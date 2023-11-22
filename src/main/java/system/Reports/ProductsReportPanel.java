package system.Reports;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import Manager.ProdutoManager;
import Manager.Sistema;
import entities.Produto;
import entities.ProdutoVendido;

public class ProductsReportPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 2653024016896084437L;
    private JTable productsTable;
    private DefaultTableModel tableModel;
    private Sistema sistema;
    private ProdutoManager produtoManager;

    public ProductsReportPanel(Sistema sistema, ProdutoManager produtoManager) {
        this.sistema = sistema;
        this.produtoManager = produtoManager;
        setLayout(new BorderLayout());

        String[] columnNames = { "SKU", "Nome", "Descrição", "Preço", "Estoque", "Quantidade Vendida" };
        tableModel = new DefaultTableModel(columnNames, 0);
        productsTable = new JTable(tableModel);

        TableColumn stockColumn = productsTable.getColumnModel().getColumn(4);
        stockColumn.setCellRenderer(new LowStockRenderer());
        add(new JScrollPane(productsTable), BorderLayout.CENTER);

        loadData();
    }

    protected void loadData() {
        tableModel.setRowCount(0);

        List<Produto> produtos = produtoManager.getProdutos();
        List<ProdutoVendido> topProdutos = sistema.getTopMaisVendidos(3);
        // Note que estamos buscando o top 3 fora do loop de preenchimento da tabela

        for (Produto produto : produtos) {
            Object[] row = new Object[] {
                    produto.getSku(),
                    produto.getNome(),
                    produto.getDescricao(),
                    produto.getPrecoVenda(),
                    produto.getEstoqueDisponivel()
            };
            tableModel.addRow(row);
        }

        // Agora aplicamos o renderizador à coluna de Quantidade Vendida
        TableColumn quantityColumn = productsTable.getColumnModel().getColumn(5);
        quantityColumn.setCellRenderer(new TopProductsRenderer(topProdutos));
    }

    class LowStockRenderer extends DefaultTableCellRenderer {
        /**
         * 
         */
        private static final long serialVersionUID = 6193582940213662192L;
        private static final int LOW_STOCK_THRESHOLD = 5; // Limite para o estoque baixo

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 4) {
                int stock = (Integer) value;
                if (stock <= LOW_STOCK_THRESHOLD) {
                    component.setBackground(new Color(133, 0, 0, 50)); // vermelho para estoque baixo
                    component.setForeground(Color.BLACK);
                } else {
                    if (!isSelected) {
                        component.setBackground(table.getBackground());
                        component.setForeground(table.getForeground());
                    }
                }
            }
            return component;
        }
    }

    protected void loadMostSoldProductsData() {
        List<ProdutoVendido> produtosMaisVendidos = sistema.calcularProdutosMaisVendidos();
        List<ProdutoVendido> topProdutos = sistema.getTopMaisVendidos(3);
        TopProductsRenderer topRenderer = new TopProductsRenderer(topProdutos);
        // Limpando o modelo antigo
        tableModel.setRowCount(0);

        // Adicionando os produtos ao modelo da tabela
        for (ProdutoVendido produtoVendido : produtosMaisVendidos) {
            Produto produto = produtoVendido.getProduto();
            Object[] row = new Object[] {
                    produto.getSku(),
                    produto.getNome(),
                    produto.getDescricao(),
                    produto.getPrecoVenda(),
                    produto.getEstoqueDisponivel(),
                    produtoVendido.getQuantidadeVendida() // Quantidade vendida
            };
            tableModel.addRow(row);

            TableColumn quantityColumn = productsTable.getColumnModel().getColumn(5); // Índice da coluna "Quantidade
                                                                                      // Vendida"
            quantityColumn.setCellRenderer(new TopProductsRenderer(topProdutos));

            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                productsTable.getColumnModel().getColumn(i).setCellRenderer(topRenderer);
            }
        }
    }

    class TopProductsRenderer extends DefaultTableCellRenderer {
        /**
         * 
         */
        private static final long serialVersionUID = -806616656057941480L;
        List<Integer> topSKUs;

        public TopProductsRenderer(List<ProdutoVendido> topProdutos) {
            topSKUs = topProdutos.stream()
                    .map(pv -> pv.getProduto().getSku())
                    .collect(Collectors.toList());
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 5) { // Certifique-se de que esta é a coluna "Quantidade Vendida"
                int sku = (Integer) table.getValueAt(row, 0); // Supondo que a coluna 0 seja "SKU"
                if (topSKUs.contains(sku) && !isSelected) {
                    c.setBackground(Color.GREEN);
                } else {
                    c.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                    c.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
                }
            }
            return c;
        }
    }

}
