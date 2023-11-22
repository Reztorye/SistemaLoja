package system.Reports;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Manager.ProdutoManager;
import entities.Produto;

public class LowStockReportPanel extends JPanel {

    private static final long serialVersionUID = 212123L;
    private JTable table;
    private DefaultTableModel tableModel;
    private ProdutoManager produtoManager;

    public LowStockReportPanel(ProdutoManager produtoManager) {
        this.produtoManager = produtoManager;
        initializePanel();
        initializeTable();
        loadData();
    }

    private void initializePanel() {
        setLayout(null);
    }

    private void initializeTable() {
        String[] columnNames = { "SKU", "Nome do Produto", "Quantidade em Estoque" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = -7806541731456304290L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Desativa a edição das células
            }
        };

        table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new StockCellRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 480, 300); // Ajuste esses valores conforme necessário
        add(scrollPane);
    }

    private void loadData() {
        tableModel.setRowCount(0);

        for (Produto produto : produtoManager.getProdutos()) {
            int estoque = produto.getEstoqueDisponivel();
            if (estoque < 5) {
                tableModel.addRow(new Object[]{
                    produto.getSku(),
                    produto.getNome(),
                    estoque
                });
            }
        }
    }

    class StockCellRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 235563L;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (column == 2) {
                int stock = (Integer) value;
                if (stock < 5) {
                    c.setBackground(new Color(255, 200, 200)); // Cor de fundo para estoque baixo
                } else {
                    c.setBackground(Color.WHITE); // Cor de fundo normal
                }
            } else {
                c.setBackground(Color.WHITE); // Cor de fundo normal
            }

            return c;
        }
    }
}
