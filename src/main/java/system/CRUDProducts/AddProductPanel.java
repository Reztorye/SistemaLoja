package system.CRUDProducts;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import entities.Categoria;
import entities.Fornecedor;
import entities.Produto;
import entities.Sistema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter


public class AddProductPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2862788884325913939L;
	private Sistema sistema;
    private JTextField fieldSKU, fieldNome, fieldDescricao, fieldPrecoCusto, fieldPrecoVenda, fieldEstoqueDisponivel;
    private JComboBox<String> comboCategoria, comboFornecedor;
    private JButton btnConfirmar;
    private DefaultTableModel tableModel; // Modelo da tabela para adicionar os produtos
    private CardLayout cardLayout; // Layout dos cards
    private JPanel cardPanel; // Painel que contém os cards
    private JButton btnVoltar;

    public AddProductPanel(CardLayout cardLayout, JPanel cardPanel, DefaultTableModel tableModel, Sistema sistema) {
    	this.sistema = sistema;
    	this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.tableModel = tableModel; // Atribui o modelo da tabela passado pelo construtor
        setLayout(null); // Sem gerenciador de layout
        
        // Inicialização e posicionamento das labels e campos de texto
        JLabel labelSKU = new JLabel("SKU:");
        labelSKU.setBounds(10, 10, 80, 25);
        add(labelSKU);

        fieldSKU = new JTextField();
        fieldSKU.setBounds(100, 10, 165, 25);
        add(fieldSKU);

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(10, 40, 80, 25);
        add(labelNome);

        fieldNome = new JTextField();
        fieldNome.setBounds(100, 40, 165, 25);
        add(fieldNome);

        JLabel labelDescricao = new JLabel("Descrição:");
        labelDescricao.setBounds(10, 70, 80, 25);
        add(labelDescricao);

        fieldDescricao = new JTextField();
        fieldDescricao.setBounds(100, 70, 165, 25);
        add(fieldDescricao);

        JLabel labelPrecoCusto = new JLabel("Preço de Custo:");
        labelPrecoCusto.setBounds(10, 100, 120, 25);
        add(labelPrecoCusto);

        fieldPrecoCusto = new JTextField();
        fieldPrecoCusto.setBounds(130, 100, 135, 25);
        add(fieldPrecoCusto);

        JLabel labelPrecoVenda = new JLabel("Preço de Venda:");
        labelPrecoVenda.setBounds(10, 130, 120, 25);
        add(labelPrecoVenda);

        fieldPrecoVenda = new JTextField();
        fieldPrecoVenda.setBounds(130, 130, 135, 25);
        add(fieldPrecoVenda);

        JLabel labelEstoqueDisponivel = new JLabel("Estoque:");
        labelEstoqueDisponivel.setBounds(10, 160, 120, 25);
        add(labelEstoqueDisponivel);

        fieldEstoqueDisponivel = new JTextField();
        fieldEstoqueDisponivel.setBounds(130, 160, 135, 25);
        add(fieldEstoqueDisponivel);

        JLabel labelCategoria = new JLabel("Categoria:");
        labelCategoria.setBounds(10, 190, 80, 25);
        add(labelCategoria);

        comboCategoria = new JComboBox<>(); // Inicializa o JComboBox sem itens
        for (Categoria categoria : sistema.getCategorias()) {
            comboCategoria.addItem(categoria.getNome()); // Adiciona cada categoria ao JComboBox
        }
        comboCategoria.setBounds(100, 190, 165, 25);
        add(comboCategoria);

        JLabel labelFornecedor = new JLabel("Fornecedor:");
        labelFornecedor.setBounds(10, 220, 80, 25);
        add(labelFornecedor);

        comboFornecedor = new JComboBox<>(); // Exemplo de fornecedores
        for (Fornecedor fornecedor : sistema.getFornecedores()) {
            comboFornecedor.addItem(fornecedor.getNome()); // Adiciona cada categoria ao JComboBox
        }
        comboFornecedor.setBounds(100, 220, 165, 25);
        add(comboFornecedor);

	        btnConfirmar = new JButton("Confirmar");
	        btnConfirmar.setBounds(10, 270, 255, 25);
	        btnConfirmar.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                try {
	                    Integer sku = Integer.valueOf(fieldSKU.getText());
	                    String nome = fieldNome.getText();
	                    String descricao = fieldDescricao.getText();
	                    double precoCusto = Double.valueOf(fieldPrecoCusto.getText());
	                    double precoVenda = Double.valueOf(fieldPrecoVenda.getText());
	                    int estoqueDisponivel = Integer.valueOf(fieldEstoqueDisponivel.getText());
	                    String nomeCategoria = (String) comboCategoria.getSelectedItem();
	                    String nomeFornecedor = (String) comboFornecedor.getSelectedItem();
	
	                    // Aqui você deve criar a Categoria e o Fornecedor com base nos nomes selecionados.
	                    // Isso pressupõe que você tenha maneiras de buscar a Categoria e o Fornecedor pelo nome.
	                    Categoria categoria = sistema.buscarCategoriaPorNome(nomeCategoria);
	                    Fornecedor fornecedor = sistema.buscarFornecedorPorNome(nomeFornecedor);
	                    
	                    
	                    // Verifique se a categoria e o fornecedor existem antes de adicionar o produto
	                    if (categoria == null || fornecedor == null) {
	                        JOptionPane.showMessageDialog(AddProductPanel.this, "Categoria ou Fornecedor não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
	                        return;
	                    }
	
	                    Produto novoProduto = sistema.adicionarProduto(sku, nome, descricao, precoCusto, precoVenda, estoqueDisponivel, categoria, fornecedor);
	
	                    Object[] rowData = {sku, nome, categoria, fornecedor, descricao, precoCusto, precoVenda, estoqueDisponivel};
	                    tableModel.addRow(rowData);
	                    
	                    if (novoProduto != null) {
	                        JOptionPane.showMessageDialog(AddProductPanel.this, "Produto adicionado com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	                        sistema.imprimirProdutos(); // Mova a chamada do método para aqui
	                    } else {
	                        JOptionPane.showMessageDialog(AddProductPanel.this, "Não foi possível adicionar o produto", "Erro", JOptionPane.ERROR_MESSAGE);
	                    }
	                } catch (NumberFormatException nfe) {
	                    JOptionPane.showMessageDialog(AddProductPanel.this, "Por favor, insira números válidos nos campos de preço e estoque.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
	                } catch (Exception ex) {
	                    JOptionPane.showMessageDialog(AddProductPanel.this, "Erro ao adicionar o produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        });
	        add(btnConfirmar);
                   
        btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(351, 271, 89, 23);
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "ProductsPanel"); 
            }
        });
        add(btnVoltar);
    }
}
