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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

	public EditProductPanel(CardLayout cardLayout, JPanel cardPanel, ProdutoManager produtoManager) {
		this.produtoManager = produtoManager;
		tableModel = new DefaultTableModel();
		setLayout(null);
		buscarCategorias();
		buscarFornecedores();

		JLabel lblSKU = new JLabel("SKU:");
		lblSKU.setBounds(10, 10, 80, 25);
		add(lblSKU);

		txtSKU = new JTextField();
		txtSKU.setBounds(0, 0, 0, 0);
		add(txtSKU);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 45, 80, 25);
		add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(130, 45, 165, 25);
		add(txtNome);

		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setBounds(10, 80, 80, 25);
		add(lblCategoria);

		cbCategoria = new JComboBox<>();
		cbCategoria.setBounds(130, 80, 165, 25);
		add(cbCategoria);

		JLabel lblFornecedor = new JLabel("Fornecedor:");
		lblFornecedor.setBounds(10, 115, 80, 25);
		add(lblFornecedor);

		cbFornecedor = new JComboBox<>();
		cbFornecedor.setBounds(130, 115, 165, 25);
		add(cbFornecedor);

		JLabel lblDescricao = new JLabel("Descrição:");
		lblDescricao.setBounds(10, 150, 80, 25);
		add(lblDescricao);

		txtDescricao = new JTextField();
		txtDescricao.setBounds(130, 150, 165, 25);
		add(txtDescricao);

		txtPrecoCusto = new JTextField();
		txtPrecoCusto.setBounds(0, 0, 0, 0);
		add(txtPrecoCusto);

		JLabel lblPrecoVenda = new JLabel("Preço de Venda:");
		lblPrecoVenda.setBounds(10, 185, 100, 25);
		add(lblPrecoVenda);

		txtEstoque = new JTextField();
		txtEstoque.setBounds(0, 0, 0, 0);
		add(txtEstoque);

		txtPrecoVenda = new JTextField();
		txtPrecoVenda.setBounds(130, 185, 165, 25);
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
		btnBack.setBounds(190, 240, 150, 30);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "ProductsPanel");
			}
		});
		add(btnBack);

	}

	public void setCurrentProduto(Produto produto) {
		System.out.println("setCurrentProduto chamado com produto: " + produto);
		this.currentProduto = produto;
		txtSKU.setEnabled(false);
		txtNome.setText(currentProduto.getNome().toUpperCase());
		cbCategoria.setSelectedItem(currentProduto.getCategoria().toUpperCase());
		cbFornecedor.setSelectedItem(currentProduto.getFornecedor().toUpperCase());
		txtDescricao.setText(currentProduto.getDescricao().toUpperCase());

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

		currentProduto.setNome(txtNome.getText().toUpperCase());
		currentProduto.setDescricao(txtDescricao.getText().toUpperCase());
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
		produtoData.put("nome", currentProduto.getNome().toUpperCase());
		produtoData.put("descricao", currentProduto.getDescricao().toUpperCase());
		produtoData.put("fornecedor", currentProduto.getFornecedor().toUpperCase());
		produtoData.put("sku", currentProduto.getSku());
		produtoData.put("categoria", currentProduto.getCategoria().toUpperCase());
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

	private void buscarCategorias() {
		DatabaseReference categoriasRef = FirebaseDatabase.getInstance().getReference("categorias");
		categoriasRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot categoriaSnapshot : dataSnapshot.getChildren()) {
					Categoria categoria = categoriaSnapshot.getValue(Categoria.class);
					if (categoria != null) {
						cbCategoria.addItem(categoria.getNome());
					}
				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				System.err.println("Erro ao buscar categorias: " + databaseError.getMessage());
			}
		});
	}

	private void buscarFornecedores() {
		DatabaseReference fornecedoresRef = FirebaseDatabase.getInstance().getReference("fornecedores");
		fornecedoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot fornecedorSnapshot : dataSnapshot.getChildren()) {
					Fornecedor fornecedor = fornecedorSnapshot.getValue(Fornecedor.class);
					if (fornecedor != null) {
						cbFornecedor.addItem(fornecedor.getNome());
					}
				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				System.err.println("Erro ao buscar fornecedores: " + databaseError.getMessage());
			}
		});
	}

}
