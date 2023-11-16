package system.CRUDCustomers;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entities.Cliente;
import entities.Sistema;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter	

public class CustomersPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4843807817212241104L;
	private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Sistema sistema;
    private JButton backButton;
    private JLabel lblClientes;

    public CustomersPanel(CardLayout cardLayout, JPanel cardPanel, Sistema sistema) {
        this.sistema = sistema;
        this.cardPanel = cardPanel;
        setLayout(null);
        
       
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Endereço");
        tableModel.addColumn("Telefone");
        tableModel.addColumn("Email");
        loadCustomersIntoTable();
        lblClientes = new JLabel("CLIENTES");
        lblClientes.setFont(new Font("Arial", Font.BOLD, 30));
        lblClientes.setBounds(20, 10, 150, 30);
        add(lblClientes);
        
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 50, 930, 300);
        add(scrollPane);

        btnAdd = new JButton("Adicionar Cliente");
        btnAdd.setBounds(180, 10, 150, 30); 
        add(btnAdd);
        
        btnEdit = new JButton("Editar Cliente");
        btnEdit.setBounds(20, 360, 150, 30);
        add(btnEdit);
        
        btnDelete = new JButton("Deletar Cliente");
        btnDelete.setBounds(180, 360, 150, 30);
        add(btnDelete);

        backButton = new JButton("Voltar");
        backButton.setBounds(870, 10, 80, 30);
        add(backButton);   
        
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "MainPanel"));

        btnAdd.addActionListener(e -> {
            AddCustomerPanel addCustomerPanel = new AddCustomerPanel(sistema, tableModel, cardLayout, cardPanel);
            cardPanel.add(addCustomerPanel, "AddCustomerPanel");
            cardLayout.show(cardPanel, "AddCustomerPanel");
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    Integer clienteId = (Integer) tableModel.getValueAt(selectedRow, 0);
                    Cliente clienteParaEditar = sistema.buscarClientePorId(clienteId);
                    if (clienteParaEditar != null) {
                        EditCustomerPanel editCustomerPanel = new EditCustomerPanel(sistema, tableModel, cardLayout, cardPanel);
                        editCustomerPanel.setCurrentCliente(clienteParaEditar);
                        cardPanel.add(editCustomerPanel, "EditCustomerPanel");
                        cardLayout.show(cardPanel, "EditCustomerPanel");
                    } else {
                        JOptionPane.showMessageDialog(null, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um cliente para editar.", "Nenhuma seleção", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnDelete.addActionListener(e -> {
            DeleteCustomerPanel deleteCustomerPanel = new DeleteCustomerPanel(sistema, tableModel, cardLayout, cardPanel);
            cardPanel.add(deleteCustomerPanel, "DeleteCustomerPanel");
            cardLayout.show(cardPanel, "DeleteCustomerPanel");
        });
    }
    
    public void loadCustomersIntoTable() {
        tableModel.setRowCount(0); 

        for (Cliente cliente : sistema.getClientes()) {
            tableModel.addRow(new Object[]{
                cliente.getId(),
                cliente.getNome(),
                cliente.getEndereco(),
                cliente.getTelefone(),
                cliente.getEmail()
            });
        }
    }

}

