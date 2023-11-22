package system.CRUDProducts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Manager.ProdutoManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteProductPanel extends JPanel {
	private static final long serialVersionUID = -2368454640878192629L;
	private JTextField txtSKU;
	private JButton btnDelete;
	private DefaultTableModel tableModel;
	private ProdutoManager produtoManager;

	public DeleteProductPanel(DefaultTableModel tableModel, ProdutoManager produtoManager) {
		this.tableModel = tableModel;
		this.produtoManager = produtoManager;
		setLayout(null);

		JLabel lblSKU = new JLabel("SKU para deletar:");
		lblSKU.setBounds(10, 10, 150, 25);
		add(lblSKU);

		txtSKU = new JTextField();
		txtSKU.setBounds(160, 10, 165, 25);
		add(txtSKU);

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
			JOptionPane.showMessageDialog(this, "Por favor, insira o SKU do produto a ser deletado.", "SKU Vazio",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			int sku = Integer.parseInt(skuToDelete);

			// Remove do gerenciador
			produtoManager.removerProduto(sku);

			// Remove do Firebase
			DatabaseReference ref = FirebaseDatabase.getInstance().getReference("produtos");
			String firebaseId = produtoManager.getFirebaseId(sku);
			if (firebaseId != null) {
				ref.child(firebaseId).removeValueAsync();
			}

			// Remove da tabela
			for (int i = 0; i < tableModel.getRowCount(); i++) {
				if (tableModel.getValueAt(i, 0).equals(sku)) {
					tableModel.removeRow(i);
					break;
				}
			}

			JOptionPane.showMessageDialog(this, "Produto deletado com sucesso.", "Produto Deletado",
					JOptionPane.INFORMATION_MESSAGE);
			txtSKU.setText("");

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Por favor, insira um SKU válido (apenas números).", "Erro de Formato",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
