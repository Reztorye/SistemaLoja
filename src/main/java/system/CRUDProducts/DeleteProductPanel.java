package system.CRUDProducts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import entities.Sistema;
import lombok.Getter;
import lombok.Setter;

	
@Getter
@Setter


public class DeleteProductPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2368454640878192629L;
	private JTextField txtSKU;
    private JButton btnDelete;
    private DefaultTableModel tableModel;
	private Sistema sistema;

	public DeleteProductPanel(DefaultTableModel tableModel, Sistema sistema) {
	    // Guarda a instância do sistema
	    this.sistema = sistema;
    	this.tableModel = tableModel;
        setLayout(null);

        // Label SKU
        JLabel lblSKU = new JLabel("SKU para deletar:");
        lblSKU.setBounds(10, 10, 150, 25);
        add(lblSKU);

        // Campo de texto para SKU
        txtSKU = new JTextField();
        txtSKU.setBounds(160, 10, 165, 25);
        add(txtSKU);

        // Botão de deletar
        btnDelete = new JButton("Deletar");
        btnDelete.setBounds(10, 45, 315, 25);
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });
        add(btnDelete);
    }


	private void deleteProduct() {
	    String skuToDelete = txtSKU.getText();
	    if (skuToDelete.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Por favor, insira o SKU do produto a ser deletado.", "SKU Vazio", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    try {
	        int sku = Integer.parseInt(skuToDelete);
	        sistema.removerProduto(sku); // Chama o método do sistema.rodrigo para remover o produto

	        for (int i = 0; i < tableModel.getRowCount(); i++) {
	            String tableSKU = tableModel.getValueAt(i, 0).toString();
	            if (tableSKU.equals(skuToDelete)) {
	                tableModel.removeRow(i);
	                JOptionPane.showMessageDialog(this, "Produto deletado com sucesso.", "Produto Deletado", JOptionPane.INFORMATION_MESSAGE);
	                txtSKU.setText("");
	                return;
	            }
	        }
	        JOptionPane.showMessageDialog(this, "Produto deletado do sistema, mas não encontrado na tabela.", "Atenção", JOptionPane.WARNING_MESSAGE);
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "Por favor, insira um SKU válido (apenas números).", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
	    }
	}
}
