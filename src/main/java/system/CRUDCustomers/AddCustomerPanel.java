package system.CRUDCustomers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import lombok.Getter;
import lombok.Setter;	
import entities.Cliente;
import entities.Sistema;
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

    public AddCustomerPanel(Sistema sistema, DefaultTableModel tableModel) {
        this.sistema = sistema;
        this.tableModel = tableModel;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Campo Nome
        txtName = new JTextField(20);
        add(new JLabel("Nome:"));
        add(txtName);

        // Campo Endereço
        txtAddress = new JTextField(20);
        add(new JLabel("Endereço:"));
        add(txtAddress);

        // Campo Telefone
        txtPhone = new JTextField(20);
        add(new JLabel("Telefone:"));
        add(txtPhone);

        // Campo Email
        txtEmail = new JTextField(20);
        add(new JLabel("Email:"));
        add(txtEmail);

        // Botão para adicionar cliente
        btnAdd = new JButton("Adicionar Cliente");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });
        add(btnAdd);
    }

    private void addCustomer() {
    	
        String name = txtName.getText().trim(); //trim apaga espaços adicionados na esquerda e direita da string
        String address = txtAddress.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();

        if (!name.isEmpty() && !address.isEmpty() && !phone.isEmpty() && !email.isEmpty()) //Validacao para preencher todos os campos 
        {
            Cliente newClient = new Cliente(name, address, phone, email);
            sistema.adicionarCliente(newClient);
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
