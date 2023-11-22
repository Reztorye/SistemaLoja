package system.CRUDProducts;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Manager.ProdutoManager;
import Manager.Sistema;
import entities.Produto;
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
    private ProdutoManager produtoManager;
    private int selectedRow;
    private JButton backButton;
    private JLabel lblProdutos;
    private Sistema sistema;

    public ProductsPanel(CardLayout cardLayout, JPanel cardPanel, ProdutoManager produtoManager, Sistema sistema) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.produtoManager = produtoManager;
        this.sistema = sistema;
        setLayout(null);

        tableModel = new DefaultTableModel() {
            /**
             * 
             */

            private static final long serialVersionUID = -3005244091815725029L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("SKU");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Categoria");
        tableModel.addColumn("Fornecedor");
        tableModel.addColumn("Descrição");
        tableModel.addColumn("Preço de Custo");
        tableModel.addColumn("Preço de Venda");
        tableModel.addColumn("Estoque Disponível");

        loadProdutoIntoTable();

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 49, 930, 300);
        add(scrollPane);

        btnAdd = new JButton("Adicionar Produto");
        btnAdd.setBounds(214, 8, 150, 30);
        add(btnAdd);

        btnEdit = new JButton("Editar Produto");
        btnEdit.setBounds(20, 360, 150, 30);
        add(btnEdit);

        btnDelete = new JButton("Deletar Produto");
        btnDelete.setBounds(180, 360, 150, 30);
        add(btnDelete);

        backButton = new JButton("Voltar");
        backButton.setBounds(870, 8, 80, 30);
        add(backButton);

        lblProdutos = new JLabel("PRODUTOS");
        lblProdutos.setFont(new Font("Arial", Font.BOLD, 30));
        lblProdutos.setBounds(20, 8, 184, 30);
        add(lblProdutos);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddProductPanel addProductPanel = new AddProductPanel(cardLayout, cardPanel, tableModel, sistema,
                        produtoManager);
                cardPanel.add(addProductPanel, "AddProductPanel");
                cardLayout.show(cardPanel, "AddProductPanel");
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int produtoSku = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                    Produto produtoParaEditar = produtoManager.buscarProdutoPorSku(produtoSku);

                    if (produtoParaEditar != null) {
                        EditProductPanel editProductPanel = new EditProductPanel(cardLayout, cardPanel, produtoManager);
                        editProductPanel.setCurrentProduto(produtoParaEditar);
                        cardPanel.add(editProductPanel, "EditProductPanel");
                        cardLayout.show(cardPanel, "EditProductPanel");
                    } else {
                        JOptionPane.showMessageDialog(null, "Produto não encontrado.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {

                    JOptionPane.showMessageDialog(null, "Selecione um produto para editar.",
                            "Nenhum produto selecionado", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteProductPanel deleteProductPanel = new DeleteProductPanel(tableModel, produtoManager);
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

    public void refreshProdutoTable() {
        loadProdutoIntoTable();
    }

    private void loadProdutoIntoTable() {
        tableModel.setRowCount(0); // Limpar a tabela existente

        produtoManager.fetchProdutosFromFirebase(produtos -> {
            if (produtos != null) {
                SwingUtilities.invokeLater(() -> {
                    for (Produto produto : produtos) {
                        tableModel.addRow(new Object[] {
                                produto.getSku(),
                                produto.getNome(),
                                produto.getCategoria() != null ? produto.getCategoria() : "",
                                produto.getFornecedor() != null ? produto.getFornecedor() : "",
                                produto.getDescricao(),
                                produto.getPrecoCusto(),
                                produto.getPrecoVenda(),
                                produto.getEstoqueDisponivel()
                        });
                    }
                });
            } else {
                String errorMessage = "Erro ao carregar dados do Firebase. Lista de produtos nula.";
                System.err.println(errorMessage);
                JOptionPane.showMessageDialog(this, errorMessage, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
