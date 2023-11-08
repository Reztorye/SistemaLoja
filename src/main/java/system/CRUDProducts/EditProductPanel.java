	package system.CRUDProducts;
	import java.awt.CardLayout;
import java.awt.GridLayout;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	
	import javax.swing.JButton;
	import javax.swing.JComboBox;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JPanel;
	import javax.swing.JTextField;
	import javax.swing.table.DefaultTableModel;
	
	import lombok.Getter;
	import lombok.Setter;
	
	
	@Getter
	@Setter
	public class EditProductPanel extends JPanel {
	    private DefaultTableModel tableModel;
	    private int selectedRow;
	    private JTextField txtSKU, txtNome, txtDescricao, txtPrecoVenda;
	    private JComboBox<String> cbCategoria, cbFornecedor;
	    private JButton btnSaveChanges;
	
	    public EditProductPanel(CardLayout cardLayout, JPanel cardPanel) {
	        setLayout(null); // Define o layout do painel como nulo
	
	        // SKU
	        JLabel lblSKU = new JLabel("SKU:");
	        lblSKU.setBounds(10, 10, 80, 25);
	        add(lblSKU);
	
	        txtSKU = new JTextField();
	        txtSKU.setBounds(100, 10, 165, 25);
	        add(txtSKU);
	
	        // Nome
	        JLabel lblNome = new JLabel("Nome:");
	        lblNome.setBounds(10, 45, 80, 25);
	        add(lblNome);
	
	        txtNome = new JTextField();
	        txtNome.setBounds(100, 45, 165, 25);
	        add(txtNome);
	
	        // Categoria
	        JLabel lblCategoria = new JLabel("Categoria:");
	        lblCategoria.setBounds(10, 80, 80, 25);
	        add(lblCategoria);
	
	        cbCategoria = new JComboBox<>(new String[]{"Smartphone", "Notebook", "Mouse"});
	        cbCategoria.setBounds(100, 80, 165, 25);
	        add(cbCategoria);
	
	        // Fornecedor
	        JLabel lblFornecedor = new JLabel("Fornecedor:");
	        lblFornecedor.setBounds(10, 115, 80, 25);
	        add(lblFornecedor);
	
	        cbFornecedor = new JComboBox<>(new String[]{"Xiaomi", "Samsung", "Apple"});
	        cbFornecedor.setBounds(100, 115, 165, 25);
	        add(cbFornecedor);
	
	        // Descrição
	        JLabel lblDescricao = new JLabel("Descrição:");
	        lblDescricao.setBounds(10, 150, 80, 25);
	        add(lblDescricao);
	
	        txtDescricao = new JTextField();
	        txtDescricao.setBounds(100, 150, 165, 25);
	        add(txtDescricao);
	
	        // Preço de Venda
	        JLabel lblPrecoVenda = new JLabel("Preço de Venda:");
	        lblPrecoVenda.setBounds(10, 185, 100, 25);
	        add(lblPrecoVenda);
	
	        txtPrecoVenda = new JTextField();
	        txtPrecoVenda.setBounds(110, 185, 155, 25);
	        add(txtPrecoVenda);
	
	        // Botão para salvar alterações
	        btnSaveChanges = new JButton("Salvar Alterações");
	        btnSaveChanges.setBounds(10, 220, 255, 25);
	        btnSaveChanges.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                saveChanges(); // Chama o método para salvar as alterações feitas pelo usuário
	                cardLayout.show(cardPanel, "ProductsPanel");
	            }
	        });
	        add(btnSaveChanges);
	    }
	    private void saveChanges() {
	        // Validação e lógica para salvar as alterações
	    	tableModel.setValueAt(txtSKU.getText(), selectedRow, 0);
	        tableModel.setValueAt(txtNome.getText(), selectedRow, 1);
	        tableModel.setValueAt(cbCategoria.getSelectedItem(), selectedRow, 2);
	        tableModel.setValueAt(cbFornecedor.getSelectedItem(), selectedRow, 3);
	        tableModel.setValueAt(txtDescricao.getText(), selectedRow, 4);
	        tableModel.setValueAt(txtPrecoVenda.getText(), selectedRow, 6);

	        // Aqui você pode adicionar a lógica para atualizar o banco de dados ou a fonte de dados

	        JOptionPane.showMessageDialog(this, "Alterações salvas com sucesso.", "Edição de Produto", JOptionPane.INFORMATION_MESSAGE);
	    }
	}	