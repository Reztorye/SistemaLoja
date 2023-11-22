package system.CRUDProducts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.google.api.core.ApiFuture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Manager.ProdutoManager;

import java.util.concurrent.Executors;

public class DeleteProductPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtSKU;
	private JButton btnDelete;
	private DefaultTableModel tableModel;
	private ProdutoManager produtoManager;

	public DeleteProductPanel(DefaultTableModel tableModel, ProdutoManager produtoManager) {
		this.tableModel = tableModel;
		this.produtoManager = produtoManager;
		setLayout(null);
		initializeUI();
	}

	private void initializeUI() {
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
		String productSku = txtSKU.getText();

		if (productSku.isEmpty()) {
			return;
		}

		try {
			Integer skuId = Integer.parseInt(productSku);

			// Obtém o firebaseId associado ao SKU
			String firebaseId = produtoManager.getFirebaseIdBySKU(skuId);

			if (firebaseId != null) {
				DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("produtos");
				ApiFuture<Void> future = dbRef.child(firebaseId).removeValueAsync();

				future.addListener(() -> {
					try {
						// Remova a chamada future.get(), ela está bloqueando a execução
						if (removeFromTableModel(productSku)) {
							produtoManager.removeProdutoSkuMapping(skuId);

							// Mensagem de sucesso na EDT
							SwingUtilities.invokeLater(() -> {
								JOptionPane.showMessageDialog(this, "Produto deletado com sucesso.", "Sucesso",
										JOptionPane.INFORMATION_MESSAGE);
							});
						}
					} catch (Exception e) {
						// Mensagem de erro na EDT
						SwingUtilities.invokeLater(() -> {
							JOptionPane.showMessageDialog(this,
									"Falha ao deletar produto do Firebase: " + e.getMessage(), "Erro",
									JOptionPane.ERROR_MESSAGE);
						});
					}
				}, Executors.newSingleThreadExecutor());
			} else {
				// Mensagem de erro na EDT

				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(this, "SKU não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
				});
			}
		} catch (NumberFormatException e) {
			// Mensagem de erro na EDT

			SwingUtilities.invokeLater(() -> {
				JOptionPane.showMessageDialog(this, "Por favor, insira um SKU válido.", "Erro de Formato",
						JOptionPane.ERROR_MESSAGE);
			});
		}
	}

	private boolean removeFromTableModel(String productSku) {
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			if (String.valueOf(tableModel.getValueAt(i, 0)).equals(productSku)) {
				tableModel.removeRow(i);
				txtSKU.setText("");
				return true;
			}
		}
		return false;
	}

}
