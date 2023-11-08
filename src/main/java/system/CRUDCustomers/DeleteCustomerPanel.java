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

import entities.Sistema;

public class DeleteCustomerPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8884562050178127774L;
	private JTextField txtCustomerId;
    private JButton btnDeleteCustomer;
    private Sistema sistema;
    private DefaultTableModel tableModel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
	private JButton btnBack;

    public DeleteCustomerPanel(Sistema sistema, DefaultTableModel tableModel, CardLayout cardLayout, JPanel cardPanel) {
    	this.cardLayout = cardLayout;
    	this.cardPanel = cardPanel;
    	this.sistema = sistema;
        this.tableModel = tableModel;
        
        initializeUI();
    }

    private void initializeUI() {
        setLayout(null);

        // Label ID
        JLabel lblCustomerId = new JLabel("ID do Cliente:");
        lblCustomerId.setBounds(10, 10, 150, 25);
        add(lblCustomerId);

        // Campo de texto para o ID
        txtCustomerId = new JTextField();
        txtCustomerId.setBounds(160, 10, 165, 25);
        add(txtCustomerId);

        // Botão de deletar
        btnDeleteCustomer = new JButton("Deletar Cliente");
        btnDeleteCustomer.setBounds(10, 45, 315, 25);
        btnDeleteCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCustomer();
            }
        });
        add(btnDeleteCustomer);
        
        btnBack = new JButton("Voltar");
        btnBack.setBounds(351, 271, 89, 23);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "CustomersPanel");
            }
        });
        add(btnBack);
    }

    private void deleteCustomer() {
        String customerIdStr = txtCustomerId.getText();
        if (customerIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira o ID do cliente a ser deletado.", "ID Vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int customerId = Integer.parseInt(customerIdStr);
            sistema.removerCliente(customerId);

            // Remover da tabela do GUI
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(customerId)) {
                    tableModel.removeRow(i);
                    JOptionPane.showMessageDialog(this, "Cliente deletado com sucesso.", "Cliente Deletado", JOptionPane.INFORMATION_MESSAGE);
                    txtCustomerId.setText("");
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Cliente deletado do sistema, mas não encontrado na tabela.", "Atenção", JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ID válido (apenas números).", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}
