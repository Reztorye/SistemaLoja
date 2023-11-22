package Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import entities.Cliente;

public class ClienteManager {
    private List<Cliente> clientes;
    private Map<Integer, String> clienteIdMap;
    private String idMapKey;

    public ClienteManager() {
        this.clientes = new ArrayList<>();
        this.clienteIdMap = new HashMap<>();
    }

    public void setIdMapKey(String idMapKey) {
        this.idMapKey = idMapKey;
    }

    // Método para obter a chave do Firebase
    public String getIdMapKey() {
        return this.idMapKey;
    }

    public void mapClienteId(Integer internalId, String firebaseId) {
        clienteIdMap.put(internalId, firebaseId);
    }

    public String getFirebaseId(Integer internalId) {
        return clienteIdMap.get(internalId);
    }

    public void removeClienteIdMapping(Integer internalId) {
        clienteIdMap.remove(internalId);
    }

    public Cliente adicionarCliente(String nome, String endereco, String telefone, String email) {
        Cliente cliente = new Cliente(nome, endereco, telefone, email);
        clientes.add(cliente);
        return cliente;
    }

    public void fetchClientesFromFirebase(Consumer<List<Cliente>> callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clientes");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Cliente> fetchedClientes = new ArrayList<>();

                // Limpa o mapeamento existente
                clienteIdMap.clear();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Cliente cliente = child.getValue(Cliente.class);
                    if (cliente != null && cliente.getIdLocal() != 0) {
                        cliente.setFirebaseId(child.getKey());
                        fetchedClientes.add(cliente);
                        // Adiciona ao mapeamento
                        clienteIdMap.put(cliente.getIdLocal(), child.getKey());
                    }
                }

                // Remove clientes do mapeamento se não estiverem presentes no Firebase
                for (Integer idLocal : new ArrayList<>(clienteIdMap.keySet())) {
                    boolean foundInFirebase = false;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (!foundInFirebase && clienteIdMap.containsKey(idLocal)) {
                            foundInFirebase = true;
                            break;
                        }
                    }
                    if (!foundInFirebase) {
                        clienteIdMap.remove(idLocal);
                    }
                }

                callback.accept(fetchedClientes);
                clientes.addAll(fetchedClientes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Erro ao buscar clientes: " + databaseError.getMessage());
                callback.accept(null);
            }
        });
    }

    public void atualizarCliente(Cliente cliente) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getIdLocal() == cliente.getIdLocal()) {
                clientes.set(i, cliente);
                System.out.println("Cliente atualizado: " + cliente.getNome());
                return;
            }
        }
        System.out.println("Cliente não encontrado: " + cliente.getIdLocal());
    }

    public void listarClientes() {
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    public void removerCliente(int id) {
        boolean removido = clientes.removeIf(c -> c.getIdLocal() == id);
        if (removido) {
            System.out.println("Cliente removido: ID " + id);
        } else {
            System.out.println("Cliente não encontrado: " + id);
        }
    }

    public Cliente buscarClientePorId(int id) {
        return clientes.stream()
                .filter(cliente -> cliente.getIdLocal() == id)
                .findFirst()
                .orElse(null);
    }

    public void atualizarCliente(String email, String nome, String endereco, String telefone) {
        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equals(email)) {
                cliente.setNome(nome);
                cliente.setEndereco(endereco);
                cliente.setTelefone(telefone);
                return;
            }
        }
    }

}