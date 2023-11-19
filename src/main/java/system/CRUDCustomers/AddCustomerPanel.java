package system.CRUDCustomers;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class AddCustomerPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -1723482129844832445L;
    private JTextField txtName, txtAddress, txtPhone, txtEmail;
    private JButton btnAdd;
    private ClienteManager clienteManager;
    private DefaultTableModel tableModel;
    private JButton btnBack;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private CustomersPanel customersPanel;

    public AddCustomerPanel(ClienteManager clienteManager, DefaultTableModel tableModel, CardLayout cardLayout,
            JPanel cardPanel,
            CustomersPanel customersPanel) {
        this.clienteManager = clienteManager;
        this.tableModel = tableModel;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.customersPanel = customersPanel;
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

        btnAdd = new JButton("Adicionar Cliente");
        btnAdd.setBounds(10, 265, 255, 25);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });
        add(btnAdd);

        btnBack = new JButton("Voltar");
        btnBack.setBounds(351, 271, 89, 23);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "CustomersPanel");
            }
        });
        add(btnBack);
    }

    private void addCustomer() {
        // Obtém os valores dos campos de texto
        String name = txtName.getText().trim();
        String address = txtAddress.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();

        // Verifica se todos os campos foram preenchidos
        if (!name.isEmpty() && !address.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
            // Cria um novo objeto Cliente
            Cliente newClient = new Cliente(name, address, phone, email);

            // Referência para a coleção 'clientes' no Firebase
            DatabaseReference clientsRef = FirebaseDatabase.getInstance().getReference("clientes");

            // Gera um ID único no Firebase
            String firebaseId = clientsRef.push().getKey();

            // Salva o cliente no Firebase
            clientsRef.child(firebaseId).setValueAsync(newClient);

            // Adiciona uma nova linha na tabela com os dados do cliente
            tableModel.addRow(new Object[] { name, address, phone, email });

            // Limpa os campos de texto para novas entradas
            txtName.setText("");
            txtAddress.setText("");
            txtPhone.setText("");
            txtEmail.setText("");

            // Atualiza a tabela de clientes no CustomersPanel
            customersPanel.refreshCustomerTable();

            // Exibe uma mensagem de sucesso
            JOptionPane.showMessageDialog(this, "Cliente adicionado com sucesso!");
        } else {
            // Exibe uma mensagem de erro se algum campo estiver vazio
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
        }
    }

}
