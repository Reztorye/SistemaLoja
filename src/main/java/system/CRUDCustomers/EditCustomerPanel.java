package system.CRUDCustomers;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Manager.ClienteManager;
import entities.Cliente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EditCustomerPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1188788921631837266L;
    private JTextField txtName, txtAddress, txtPhone, txtEmail;
    private JButton btnSaveChanges;
    private ClienteManager clienteManager;
    private DefaultTableModel tableModel;
    private Cliente currentCliente; // o cliente atual sendo editado
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton btnBack;

    public EditCustomerPanel(ClienteManager clienteManager, DefaultTableModel tableModel, CardLayout cardLayout,
            JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.clienteManager = clienteManager;
        this.tableModel = tableModel;

        setLayout(null);

        JLabel lblName = new JLabel("Nome:");
        lblName.setBounds(10, 10, 80, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(100, 10, 165, 25);
        add(txtName);

        JLabel lblAddress = new JLabel("Endereço:");
        lblAddress.setBounds(10, 45, 80, 25);
        add(lblAddress);

        txtAddress = new JTextField();
        txtAddress.setBounds(100, 45, 165, 25);
        add(txtAddress);

        JLabel lblPhone = new JLabel("Telefone:");
        lblPhone.setBounds(10, 80, 80, 25);
        add(lblPhone);

        txtPhone = new JTextField();
        txtPhone.setBounds(100, 80, 165, 25);
        add(txtPhone);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(10, 115, 80, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(100, 115, 165, 25);
        add(txtEmail);

        btnSaveChanges = new JButton("Salvar Alterações");
        btnSaveChanges.setBounds(10, 150, 255, 25);
        btnSaveChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });
        add(btnSaveChanges);

        btnBack = new JButton("Voltar");
        btnBack.setBounds(351, 271, 89, 23);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "CustomersPanel");
            }
        });
        add(btnBack);
    }

    public void setCurrentCliente(Cliente cliente) {
        this.currentCliente = cliente;
        txtName.setText(cliente.getNome());
        txtAddress.setText(cliente.getEndereco());
        txtPhone.setText(cliente.getTelefone());
        txtEmail.setText(cliente.getEmail());
    }

    private void saveChanges() {
        if (txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtPhone.getText().isEmpty()
                || txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Atualizar dados localmente
        currentCliente.setNome(txtName.getText());
        currentCliente.setEndereco(txtAddress.getText());
        currentCliente.setTelefone(txtPhone.getText());
        currentCliente.setEmail(txtEmail.getText());

        // Atualizar na tabela local
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(currentCliente.getIdLocal())) {
                tableModel.setValueAt(currentCliente.getNome(), i, 1);
                tableModel.setValueAt(currentCliente.getEndereco(), i, 2);
                tableModel.setValueAt(currentCliente.getTelefone(), i, 3);
                tableModel.setValueAt(currentCliente.getEmail(), i, 4);
                break;
            }
        }

        // Preparar dados para salvar no Firebase (sem o firebaseId)
        Map<String, Object> clienteData = new HashMap<>();
        clienteData.put("nome", currentCliente.getNome());
        clienteData.put("endereco", currentCliente.getEndereco());
        clienteData.put("telefone", currentCliente.getTelefone());
        clienteData.put("email", currentCliente.getEmail());
        clienteData.put("idLocal", currentCliente.getIdLocal()); // Incluir se quiser manter o ID local no Firebase

        // Atualizar dados no Firebase
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("clientes");
        String firebaseId = clienteManager.getFirebaseId(currentCliente.getIdLocal());

        if (firebaseId != null) {
            dbRef.child(firebaseId).updateChildrenAsync(clienteData).addListener(() -> {
                JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!", "Cliente Atualizado",
                        JOptionPane.INFORMATION_MESSAGE);
            }, Executors.newSingleThreadExecutor());
        } else {
            JOptionPane.showMessageDialog(this, "ID do Firebase não encontrado para o cliente.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
