package system.CRUDProducts;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Manager.ProdutoManager;
import entities.Categoria;
import entities.Fornecedor;
import entities.Produto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditProductPanel extends JPanel {
	private static final long serialVersionUID = 7065711491947376905L;
	private JTextField txtSKU, txtNome, txtDescricao, txtPrecoVenda, txtPrecoCusto, txtEstoque;
	private JComboBox<String> cbCategoria, cbFornecedor;
	private JButton btnSaveChanges;
	private JButton btnBack;
	private ProdutoManager produtoManager;
	private DefaultTableModel tableModel;
	private int selectedRow;
	private Produto currentProduto;
	private JTable table;
	private JButton btnEdit;

	public EditProductPanel(CardLayout cardLayout, JPanel cardPanel, ProdutoManager produtoManager, Object[] rowData) {
		this.produtoManager = produtoManager;
		setLayout(null);

		// Inicializar e configurar componentes aqui...
		initializeComponents();

		btnSaveChanges.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveChanges();
				cardLayout.show(cardPanel, "ProductsPanel");
			}
		});

		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					Object[] rowData = new Object[tableModel.getColumnCount()];
					for (int i = 0; i < tableModel.getColumnCount(); i++) {
						rowData[i] = tableModel.getValueAt(selectedRow, i);
					}

					EditProductPanel editProductPanel = new EditProductPanel(cardLayout, cardPanel, produtoManager,
							rowData);
					cardPanel.add(editProductPanel, "EditProductPanel");
					cardLayout.show(cardPanel, "EditProductPanel");
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um produto para editar.",
							"Nenhum produto selecionado", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

	}

	private void initializeComponents() {
		JLabel lblSKU = new JLabel("SKU:");
		lblSKU.setBounds(10, 10, 80, 25);
		add(lblSKU);

		txtSKU = new JTextField();
		txtSKU.setBounds(100, 10, 165, 25);
		add(txtSKU);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 45, 80, 25);
		add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(100, 45, 165, 25);
		add(txtNome);

		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setBounds(10, 80, 80, 25);
		add(lblCategoria);

		cbCategoria = new JComboBox<>(new String[] { "Smartphone", "Notebook", "Mouse" });
		cbCategoria.setBounds(100, 80, 165, 25);
		add(cbCategoria);

		JLabel lblFornecedor = new JLabel("Fornecedor:");
		lblFornecedor.setBounds(10, 115, 80, 25);
		add(lblFornecedor);

		cbFornecedor = new JComboBox<>(new String[] { "Xiaomi", "Samsung", "Apple" });
		cbFornecedor.setBounds(100, 115, 165, 25);
		add(cbFornecedor);

		JLabel lblDescricao = new JLabel("Descrição:");
		lblDescricao.setBounds(10, 150, 80, 25);
		add(lblDescricao);

		txtDescricao = new JTextField();
		txtDescricao.setBounds(100, 150, 165, 25);
		add(txtDescricao);

		JLabel lblPrecoVenda = new JLabel("Preço de Venda:");
		lblPrecoVenda.setBounds(10, 185, 100, 25);
		add(lblPrecoVenda);

		txtPrecoVenda = new JTextField();
		txtPrecoVenda.setBounds(110, 185, 155, 25);
		add(txtPrecoVenda);

		btnSaveChanges = new JButton("Salvar Alterações");
		btnSaveChanges.setBounds(10, 240, 150, 30);
		add(btnSaveChanges);
	}

	private void configureInitialData(Produto produto) {
		txtSKU.setText(String.valueOf(produto.getSku()));
		txtNome.setText(produto.getNome());
		cbCategoria.setSelectedItem(produto.getCategoria().getNome());
		cbFornecedor.setSelectedItem(produto.getFornecedor().getNome());
		txtDescricao.setText(produto.getDescricao());
		txtPrecoCusto.setText(String.valueOf(produto.getPrecoCusto()));
		txtPrecoVenda.setText(String.valueOf(produto.getPrecoVenda()));
		txtEstoque.setText(String.valueOf(produto.getEstoqueDisponivel()));
	}

	private void saveChanges() {
		try {
			Integer sku = Integer.parseInt(txtSKU.getText());
			String nome = txtNome.getText();
			String descricao = txtDescricao.getText();
			double precoVenda = Double.parseDouble(txtPrecoVenda.getText());
			double precoCusto = Double.parseDouble(txtPrecoCusto.getText());
			int estoque = Integer.parseInt(txtEstoque.getText());
			Categoria categoria = new Categoria(cbCategoria.getSelectedItem().toString());
			Fornecedor fornecedor = new Fornecedor(cbFornecedor.getSelectedItem().toString());

			Produto produtoAtualizado = new Produto(sku, nome, descricao, precoCusto, precoVenda, estoque, categoria,
					fornecedor);
			produtoManager.atualizarProdutoFirebase(produtoAtualizado);

			// Atualizar localmente
			tableModel.setValueAt(sku, selectedRow, 0);
			// ... restante da atualização local

			JOptionPane.showMessageDialog(this, "Alterações salvas com sucesso.", "Edição de Produto",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Erro ao salvar as alterações: " + e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
