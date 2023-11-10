package system.CRUDCustomers;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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

    public CustomersPanel(CardLayout cardLayout, JPanel cardPanel, Sistema sistema) {
        this.sistema = sistema;
        this.cardPanel = cardPanel;
        setLayout(new BorderLayout());

        // Modelo de tabela
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Endereço");
        tableModel.addColumn("Telefone");
        tableModel.addColumn("Email");

        // Tabela para mostrar os clientes
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Painel para os botões
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAdd = new JButton("Adicionar Cliente");	
        btnEdit = new JButton("Editar Cliente");
        btnDelete = new JButton("Deletar Cliente");

        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnEdit);
        buttonsPanel.add(btnDelete);

        add(buttonsPanel, BorderLayout.SOUTH);

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
}

