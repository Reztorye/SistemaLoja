package system.CRUDProducts;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Manager.ProdutoManager;

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

	public EditProductPanel(CardLayout cardLayout, JPanel cardPanel, ProdutoManager produtoManager) {
		this.produtoManager = produtoManager;
		tableModel = new DefaultTableModel();
		setLayout(null);

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

		txtPrecoCusto = new JTextField();
		txtPrecoCusto.setBounds(100, 185, 165, 25);
		add(txtPrecoCusto);

		JLabel lblPrecoVenda = new JLabel("Preço de Venda:");
		lblPrecoVenda.setBounds(10, 185, 100, 25);
		add(lblPrecoVenda);

		txtEstoque = new JTextField();
		txtEstoque.setBounds(100, 220, 165, 25);
		add(txtEstoque);

		txtPrecoVenda = new JTextField();
		txtPrecoVenda.setBounds(110, 185, 155, 25);
		add(txtPrecoVenda);

		btnSaveChanges = new JButton("Salvar Alterações");
		btnSaveChanges.setBounds(10, 240, 150, 30);
		btnSaveChanges.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Botão Salvar pressionado");
				saveChanges();
			}
		});
		add(btnSaveChanges);

		btnBack = new JButton("Voltar");
		btnBack.setBounds(351, 271, 89, 23);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "ProductPanel");
			}
		});
		add(btnBack);

	}

	public void setCurrentProduto(Produto produto) {
		System.out.println("setCurrentProduto chamado com produto: " + produto);
		this.currentProduto = produto;
		txtSKU.setText(String.valueOf(currentProduto.getSku()));
		txtNome.setText(currentProduto.getNome());
		cbCategoria.setSelectedItem(currentProduto.getCategoria());
		cbFornecedor.setSelectedItem(currentProduto.getFornecedor());
		txtDescricao.setText(currentProduto.getDescricao());

		if (!Double.isNaN(currentProduto.getPrecoCusto())) {
			txtPrecoCusto.setText(String.valueOf(currentProduto.getPrecoCusto()));
		} else {
			txtPrecoCusto.setText("");
		}

		if (!Double.isNaN(currentProduto.getPrecoVenda())) {
			txtPrecoVenda.setText(String.valueOf(currentProduto.getPrecoVenda()));
		}

		if (currentProduto.getEstoqueDisponivel() != 0) {
			txtEstoque.setText(String.valueOf(currentProduto.getEstoqueDisponivel()));
		}

	}

	private void saveChanges() {
		System.out.println("Tentando salvar alterações para o produto: " + currentProduto);
		double precoCusto = Double.parseDouble(txtPrecoCusto.getText());
		double precoVenda = Double.parseDouble(txtPrecoVenda.getText());
		int estoqueDisponivel = Integer.parseInt(txtEstoque.getText());

		if (txtNome.getText().isEmpty() || txtDescricao.getText().isEmpty() || txtEstoque.getText().isEmpty()
				|| txtPrecoCusto.getText().isEmpty() || txtPrecoVenda.getText().isEmpty()
				|| txtSKU.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
			return;
		}

		currentProduto.setNome(txtNome.getText());
		currentProduto.setDescricao(txtDescricao.getText());
		currentProduto.setPrecoCusto(precoCusto);
		currentProduto.setPrecoVenda(precoVenda);
		currentProduto.setEstoqueDisponivel(estoqueDisponivel);

		for (int i = 0; i < tableModel.getRowCount(); i++) {
			if (tableModel.getValueAt(i, 0).equals(currentProduto.getSku())) {
				tableModel.setValueAt(currentProduto.getCategoriaNome(), i, 1);
				tableModel.setValueAt(currentProduto.getDescricao(), i, 2);
				tableModel.setValueAt(currentProduto.getFornecedor(), i, 3);
				tableModel.setValueAt(currentProduto.getCategoria(), i, 4);
				tableModel.setValueAt(currentProduto.getPreco(), i, 5);
				tableModel.setValueAt(currentProduto.getPrecoCusto(), i, 6);
				tableModel.setValueAt(currentProduto.getPrecoVenda(), i, 7);
				tableModel.setValueAt(currentProduto.getEstoqueDisponivel(), i, 8);
				break;
			}
		}

		Map<String, Object> produtoData = new HashMap<>();
		produtoData.put("nome", currentProduto.getNome());
		produtoData.put("descricao", currentProduto.getDescricao());
		produtoData.put("fornecedor", currentProduto.getFornecedor());
		produtoData.put("sku", currentProduto.getSku());
		produtoData.put("categoria", currentProduto.getCategoria());
		produtoData.put("preco", currentProduto.getPreco());
		produtoData.put("preco de custo", currentProduto.getPrecoCusto());
		produtoData.put("preco de venda", currentProduto.getPrecoVenda());

		DatabaseReference dReference = FirebaseDatabase.getInstance().getReference("produtos");
		String firebaseId = produtoManager.getFirebaseIdBySKU(currentProduto.getSku());

		if (firebaseId != null) {
			dReference.child(firebaseId).updateChildrenAsync(produtoData).addListener(() -> {
				JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!", "Produto Atualizado",
						JOptionPane.INFORMATION_MESSAGE);
			}, Executors.newSingleThreadExecutor());
		} else {
			System.out.println("Deu merda:" + currentProduto);
			JOptionPane.showMessageDialog(this, "ID do Firebase não encontrado para o produto.", "Erro",

					JOptionPane.ERROR_MESSAGE);
		}
	}

}
