package system.BancodeDados;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.IOException;
import java.io.InputStream;

public class DataBaseFirebase {
        private static boolean isInitialized = false;

        public static void initialize() {
                if (!isInitialized) {
                        try {
                                InputStream serviceAccount = DataBaseFirebase.class.getClassLoader()
                                                .getResourceAsStream(
                                                                "sistema-loja-bf8c2-firebase-adminsdk-1suld-d84f0b66e7.json");

                                if (serviceAccount == null) {
                                        throw new IOException(
                                                        "Não foi possível encontrar o arquivo de credenciais do Firebase.");
                                }

                                FirebaseOptions options = FirebaseOptions.builder()
                                                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                                                .setDatabaseUrl("https://sistema-loja-bf8c2-default-rtdb.firebaseio.com")
                                                .build();

                                FirebaseApp.initializeApp(options);
                                isInitialized = true;
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
        }

        public static void main(String[] args) {
                // Exemplo de utilização do banco de dados Firebase
                DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference("exemplo");

                ChildEventListener childEventListener = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                                // Implemente a lógica aqui
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                                // Implemente a lógica aqui
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                                // Implemente a lógica aqui
                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                                // Implemente a lógica aqui
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                                // Implemente a lógica aqui
                        }
                };

                firebaseRef.addChildEventListener(childEventListener);

                // Exemplo de como adicionar dados
                DatabaseReference newItemRef = firebaseRef.push();
                newItemRef.setValue("Novo Item no Firebase", new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError != null) {
                                        System.err.println("Erro ao adicionar dados: " + databaseError.getMessage());
                                } else {
                                        System.out.println("Dados adicionados com sucesso!");
                                }
                        }
                });
        }
}
