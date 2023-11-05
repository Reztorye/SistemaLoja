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

import entities.Sistema;
import entities.Produto;

// ... outras importações conforme necessário

@SuppressWarnings("serial")
public class AddProductPanel extends JPanel {
    private Sistema sistema = new Sistema();
    private JTextField fieldSKU, fieldNome, fieldDescricao, fieldPrecoCusto, fieldPrecoVenda, fieldEstoqueDisponivel;
    private JComboBox<String> comboCategoria, comboFornecedor;
    private JButton btnConfirmar;
    private DefaultTableModel tableModel; // Modelo da tabela para adicionar os produtos
    private CardLayout cardLayout; // Layout dos cards
    private JPanel cardPanel; // Painel que contém os cards

    public AddProductPanel(CardLayout cardLayout, JPanel cardPanel, DefaultTableModel tableModel) {
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

        comboCategoria = new JComboBox<>(new String[]{"Smartphone", "Notebook", "Mouse"}); // Exemplo de categorias
        comboCategoria.setBounds(100, 190, 165, 25);
        add(comboCategoria);

        JLabel labelFornecedor = new JLabel("Fornecedor:");
        labelFornecedor.setBounds(10, 220, 80, 25);
        add(labelFornecedor);

        comboFornecedor = new JComboBox<>(new String[]{"Xiaomi", "Samsung", "Apple"}); // Exemplo de fornecedores
        comboFornecedor.setBounds(100, 220, 165, 25);
        add(comboFornecedor);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBounds(10, 270, 245, 25);
        btnConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Integer sku = Integer.valueOf(fieldSKU.getText());
                    String nome = fieldNome.getText();
                    String descricao = fieldDescricao.getText();
                    double precoCusto = Double.valueOf(fieldPrecoCusto.getText());
                    double precoVenda = Double.valueOf(fieldPrecoVenda.getText());
                    int estoqueDisponivel = Integer.valueOf(fieldEstoqueDisponivel.getText());
                    String NomeCategoria = (String) comboCategoria.getSelectedItem();
                    String nomeFornecedor = (String) comboFornecedor.getSelectedItem();

                    Produto novoProduto = sistema.adicionarProduto(sku, nome, descricao, precoCusto, precoVenda, estoqueDisponivel, NomeCategoria, nomeFornecedor);

                    tableModel.addRow(new Object[]{
                        novoProduto.getSku(), 
                        novoProduto.getNome(), 
                        novoProduto.getCategoria().getNome(), 
                        novoProduto.getFornecedor().getNome(), 
                        novoProduto.getDescricao(), 
                        novoProduto.getPrecoCusto(), 
                        novoProduto.getPrecoVenda(), 
                        novoProduto.getEstoqueDisponivel()
                    });

                    fieldSKU.setText("");
                    fieldNome.setText("");
                    fieldDescricao.setText("");
                    fieldPrecoCusto.setText("");
                    fieldPrecoVenda.setText("");
                    fieldEstoqueDisponivel.setText("");

                    JOptionPane.showMessageDialog(AddProductPanel.this, "Produto adicionado com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(AddProductPanel.this, "Por favor, insira números válidos nos campos de preço e estoque.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AddProductPanel.this, "Erro ao adicionar o produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btnConfirmar);
    }
}
