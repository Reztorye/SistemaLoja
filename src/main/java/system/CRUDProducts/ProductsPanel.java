package system.CRUDProducts;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entities.Produto;
import entities.Sistema;
import lombok.Getter;
import lombok.Setter;
import javax.swing.JLabel;
import java.awt.Font;
@Getter
@Setter

public class ProductsPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2448723614099966739L;
	private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Sistema sistema;
    private int selectedRow;
    private JButton backButton;
    private JLabel lblProdutos;

    public ProductsPanel(CardLayout cardLayout, JPanel cardPanel, Sistema sistema) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.sistema = sistema;
        setLayout(null);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("SKU");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Categoria");
        tableModel.addColumn("Fornecedor");
        tableModel.addColumn("Descrição");
        tableModel.addColumn("Preço de Custo");
        tableModel.addColumn("Preço de Venda");
        tableModel.addColumn("Estoque Disponível");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 49, 930, 300); // Substitua pelos valores reais
        add(scrollPane);

        btnAdd = new JButton("Adicionar Produto");
        btnAdd.setBounds(214, 8, 150, 30); // Substitua pelos valores reais
        add(btnAdd);
         
        btnEdit = new JButton("Editar Produto");
        btnEdit.setBounds(20, 360, 150, 30); // Substitua pelos valores reais
        add(btnEdit);
        
        btnDelete = new JButton("Deletar Produto");
        btnDelete.setBounds(180, 360, 150, 30); // Substitua pelos valores reais
        add(btnDelete);
        
        backButton = new JButton("Voltar");
        backButton.setBounds(870, 8, 80, 30);
        add(backButton);
        
        lblProdutos = new JLabel("PRODUTOS");
        lblProdutos.setFont(new Font("Arial", Font.BOLD, 30));
        lblProdutos.setBounds(20, 8, 184, 30);
        add(lblProdutos);
        
        //Actions Listeners (eventos ao clicar no botão)
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddProductPanel addProductPanel = new AddProductPanel(cardLayout, cardPanel, tableModel, sistema);
                cardPanel.add(addProductPanel, "AddProductPanel");
                cardLayout.show(cardPanel, "AddProductPanel");
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) { // Verifica se uma linha está selecionada
                    Object sku = tableModel.getValueAt(selectedRow, 0);
                    Object nome = tableModel.getValueAt(selectedRow, 1);
                    Object categoria = tableModel.getValueAt(selectedRow, 2);
                    Object fornecedor = tableModel.getValueAt(selectedRow, 3);
                    Object descricao = tableModel.getValueAt(selectedRow, 4);
                    Object precoVenda = tableModel.getValueAt(selectedRow, 6);

                    EditProductPanel editProductPanel = new EditProductPanel(cardLayout, cardPanel);
                    editProductPanel.setSelectedRow(selectedRow);
                    editProductPanel.setTableModel(tableModel); 
                    editProductPanel.getTxtSKU().setText(sku.toString());
                    editProductPanel.getTxtNome().setText(nome.toString());
                    editProductPanel.getTxtSKU().setText(sku.toString());
                    editProductPanel.getTxtNome().setText(nome.toString());
                    editProductPanel.getCbCategoria().setSelectedItem(categoria.toString());
                    editProductPanel.getCbFornecedor().setSelectedItem(fornecedor.toString());
                    editProductPanel.getTxtDescricao().setText(descricao.toString());
                    editProductPanel.getTxtPrecoVenda().setText(precoVenda.toString());

                    // Aqui você adiciona o painel de edição ao cardPanel e mostra ele
                    cardPanel.add(editProductPanel, "EditProductPanel");
                    cardLayout.show(cardPanel, "EditProductPanel");
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um produto para editar.", "Nenhum produto selecionado", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // A suposição aqui é que você tem uma instância válida do 'Sistema' chamada 'sistema'.
                DeleteProductPanel deleteProductPanel = new DeleteProductPanel(tableModel, sistema);
                cardPanel.add(deleteProductPanel, "DeleteProductPanel");
                cardLayout.show(cardPanel, "DeleteProductPanel");
            }
        });
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "MainPanel");
            }
        });
    }
 
    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (aFlag) {
            atualizarTabelaProdutos();
        }
    }
    
    public void atualizarTabelaProdutos() {
        tableModel.setRowCount(0); //limpa a tabela anterior

        List<Produto> produtos = sistema.getProdutos(); //atualiza tabela com o desconto
        for (Produto produto : produtos) {
            tableModel.addRow(new Object[]{
                produto.getSku(),
                produto.getNome(),
                produto.getCategoria().getNome(),
                produto.getFornecedor().getNome(),
                produto.getDescricao(),
                produto.getPrecoCusto(),
                produto.getPrecoVendaComDesconto(), 
                produto.getEstoqueDisponivel()
            });
        }
    }

}	