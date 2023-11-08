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

import entities.Cliente;
import entities.Sistema;
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
    private Sistema sistema = new Sistema(); // A instância do sistema para adicionar clientes
    private DefaultTableModel tableModel; // Modelo da tabela de clientes
    private JButton btnBack;
    private CardLayout cardLayout; // Layout dos cards
    private JPanel cardPanel; // Painel que contém os cards

    public AddCustomerPanel(Sistema sistema, DefaultTableModel tableModel, CardLayout cardLayout, JPanel cardPanel) {
        this.sistema = sistema;
        this.tableModel = tableModel;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        setLayout(null);
        initializeUI();
    }

    private void initializeUI() {
    	// Nome
        JLabel lblName = new JLabel("Nome:");
        lblName.setBounds(10, 10, 80, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(100, 10, 165, 25);
        add(txtName);

        // Endereço
        JLabel lblAddress = new JLabel("Endereço:");
        lblAddress.setBounds(10, 45, 80, 25);
        add(lblAddress);

        txtAddress = new JTextField();
        txtAddress.setBounds(100, 45, 165, 25);
        add(txtAddress);

        // Telefone
        JLabel lblPhone = new JLabel("Telefone:");
        lblPhone.setBounds(10, 80, 80, 25);
        add(lblPhone);

        txtPhone = new JTextField();
        txtPhone.setBounds(100, 80, 165, 25);
        add(txtPhone);

        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(10, 115, 80, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(100, 115, 165, 25);
        add(txtEmail);
        
        // Botão para adicionar cliente
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
    	
        String name = txtName.getText().trim(); //trim apaga espaços adicionados na esquerda e direita da string
        String address = txtAddress.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();

        if (!name.isEmpty() && !address.isEmpty() && !phone.isEmpty() && !email.isEmpty()) //Validacao para preencher todos os campos 
        {
            Cliente newClient = new Cliente(name, address, phone, email);
            sistema.adicionarCliente(newClient); //rodrigo methods
            JOptionPane.showMessageDialog(this, "Cliente adicionado com sucesso!");

            tableModel.addRow(new Object[]{
                newClient.getId(), // ID gerado automaticamente
                name,
                address,
                phone,
                email
            });

            txtName.setText("");
            txtAddress.setText("");
            txtPhone.setText("");
            txtEmail.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
        }
    }
}
