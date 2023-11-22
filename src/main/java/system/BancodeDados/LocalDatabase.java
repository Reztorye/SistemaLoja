package system.BancodeDados;

import com.google.firebase.database.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LocalDatabase<T> {
    private DatabaseReference firebaseReference;
    private List<T> dataList;
    private ValueEventListener valueEventListener;
    private String localFilePath;
    private Class<T> dataClass;

    public LocalDatabase(DatabaseReference firebaseReference, String localFilePath, Class<T> dataClass) {
        this.firebaseReference = firebaseReference;
        this.dataList = new ArrayList<>();
        this.localFilePath = localFilePath;
        this.dataClass = dataClass; // Adiciona esta linha
        initializeFirebaseSync();
        loadLocalData(); // Carregar dados locais ao inicializar
    }

    private void initializeFirebaseSync() {
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Atualiza a lista local com os dados do Firebase
                dataList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    T data = snapshot.getValue(dataClass); // Usando getValue com Class<T>
                    dataList.add(data);
                }

                // Dispara algum evento de atualização local após a sincronização
                // (por exemplo, notificar observadores, atualizar UI, etc.)
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Trate os erros de sincronização (por exemplo, log, notificar o usuário, etc.)
            }
        };

        // Adiciona o ouvinte ao nó específico no Firebase (por exemplo, "produtos")
        firebaseReference.addValueEventListener(valueEventListener);
    }

    private void saveLocalData() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(localFilePath))) {
            outputStream.writeObject(dataList);
        } catch (IOException e) {
            e.printStackTrace(); // Trate exceções de IO conforme necessário
        }
    }

    @SuppressWarnings("unchecked")
    private void loadLocalData() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(localFilePath))) {
            Object loadedObject = inputStream.readObject();
            if (loadedObject instanceof List<?>) {
                dataList = (List<T>) loadedObject;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Trate exceções de IO ou de desserialização conforme necessário
        }
    }

    public void adicionarDado(T dado) {
        // Adiciona localmente
        dataList.add(dado);
        // Adiciona ao Firebase
        DatabaseReference novoDadoRef = firebaseReference.push();
        novoDadoRef.setValue(dado, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                // Lidar com erros ao adicionar ao Firebase
            } else {
                saveLocalData(); // Salva os dados localmente após uma alteração
            }
        });
    }

    public void removerDado(int indice) {
        if (indice >= 0 && indice < dataList.size()) {
            // Remove localmente
            dataList.remove(indice);
            // Remove do Firebase
            DatabaseReference dadoRemovidoRef = firebaseReference.child("id_do_dado_no_firebase");
            dadoRemovidoRef.removeValue((databaseError, databaseReference) -> {
                if (databaseError != null) {
                    // Lidar com erros ao remover do Firebase
                } else {
                    saveLocalData(); // Salva os dados localmente após uma alteração
                }
            });
        }
    }

    public List<T> recuperarTodosOsDados() {
        return new ArrayList<>(dataList);
    }

}
