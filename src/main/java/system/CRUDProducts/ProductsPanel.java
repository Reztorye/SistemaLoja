package system.CRUDProducts;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
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

    public ProductsPanel(CardLayout cardLayout, JPanel cardPanel, Sistema sistema) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.sistema = sistema;
        setLayout(new BorderLayout()); // Utilizar BorderLayout

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
        add(scrollPane, BorderLayout.CENTER);

        // Painel para os botões
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));	
        btnAdd = new JButton("Adicionar Produto");
        btnEdit = new JButton("Editar Produto");   
        btnDelete = new JButton("Deletar Produto");

        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnEdit);
        buttonsPanel.add(btnDelete);		

        add(buttonsPanel, BorderLayout.SOUTH);
        
        // Adicionar action listeners para os botões
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
                    // Aqui você pega os valores da linha selecionada para preencher nos campos de texto do painel de edição
                    Object sku = tableModel.getValueAt(selectedRow, 0);
                    Object nome = tableModel.getValueAt(selectedRow, 1);
                    Object categoria = tableModel.getValueAt(selectedRow, 2);
                    Object fornecedor = tableModel.getValueAt(selectedRow, 3);
                    Object descricao = tableModel.getValueAt(selectedRow, 4);
                    Object precoVenda = tableModel.getValueAt(selectedRow, 6);

                    // Agora você cria o painel de edição e preenche os campos de texto
                    EditProductPanel editProductPanel = new EditProductPanel(cardLayout, cardPanel);
                    editProductPanel.setSelectedRow(selectedRow); // Você precisa de um método para definir a linha selecionada
                    editProductPanel.setTableModel(tableModel); // E um método para definir o modelo da tabela
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
    
    }
    
 // Dentro da classe ProductsPanel

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