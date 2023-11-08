package system;
import java.awt.BorderLayout; 
import java.awt.CardLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entities.Sistema;
import lombok.Getter;
import lombok.Setter;
import system.CRUDCustomers.AddCustomerPanel;
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
    private Sistema sistema = new Sistema();

    public CustomersPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
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
            AddCustomerPanel addCustomerPanel = new AddCustomerPanel(sistema, tableModel);
            cardPanel.add(addCustomerPanel, "AddCustomerPanel");
            cardLayout.show(cardPanel, "AddCustomerPanel");
        });

        btnEdit.addActionListener(e -> {
            JPanel editCustomerPanel = new JPanel();
            cardPanel.add(editCustomerPanel, "EditCustomerPanel");
            cardLayout.show(cardPanel, "EditCustomerPanel");
        });

        btnDelete.addActionListener(e -> {
            JPanel deleteCustomerPanel = new JPanel();
            cardPanel.add(deleteCustomerPanel, "DeleteCustomerPanel");
            cardLayout.show(cardPanel, "DeleteCustomerPanel");
        });
    }
}

