package system.BancodeDados;

import com.google.firebase.database.*;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import entities.Cliente;

public class FirebaseSync {
    private DatabaseReference clientsRef;
    private DefaultTableModel tableModel;

    public FirebaseSync(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
        this.clientsRef = FirebaseDatabase.getInstance().getReference("clientes");
    }

    public void syncTableWithFirebase() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela local

        clientsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    updateTable(childSnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                showError("Erro ao buscar clientes: " + databaseError.getMessage());
            }
        });
    }

    private void updateTable(DataSnapshot dataSnapshot) {
        SwingUtilities.invokeLater(() -> {
            Cliente cliente = dataSnapshot.getValue(Cliente.class);
            if (cliente != null) {
                // Adiciona a nova linha
                tableModel.addRow(new Object[] {
                        dataSnapshot.getKey(), // ID do Cliente
                        cliente.getNome(),
                        cliente.getEndereco(),
                        cliente.getTelefone(),
                        cliente.getEmail()
                });
            }
        });
    }

    private void showError(String errorMessage) {
        // Implemente conforme necessário
    }
}
