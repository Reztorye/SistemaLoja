package system.Reports;

import java.awt.Component;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import Manager.Sistema;
import entities.ItemVenda;
import entities.Venda;

public class SalesReportPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 4659160089766279374L;
    private JTable table;
    private DefaultTableModel tableModel;
    private Sistema sistema;

    public SalesReportPanel(Sistema sistema) {
        this.sistema = sistema;
        setLayout(null);

        String[] columnNames = { "Data da Venda", "Nome do Cliente", "Itens Vendidos", "Valor Total" };

        tableModel = new DefaultTableModel(columnNames, 0) {
            /**
             * 
             */
            private static final long serialVersionUID = -9049266189071413309L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setBounds(10, 10, 480, 300);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 11, 952, 261);
        add(scrollPane);

        TableColumn dateColumn = table.getColumn("Data da Venda");
        dateColumn.setCellRenderer(new DateRenderer());
        dateColumn.setPreferredWidth(150);

        loadData();
    }

    protected void loadData() {
        tableModel.setRowCount(0);

        List<Venda> vendas = sistema.getVendas();

        for (Venda venda : vendas) {
            Object[] row = new Object[] {
                    venda.getData(),
                    venda.getCliente().getNome(),
                    formatarItensVenda(venda.getItensVenda()),
                    venda.calcularValorTotal()
            };
            tableModel.addRow(row);
        }

        TableColumn totalColumn = table.getColumn("Valor Total");
        totalColumn.setCellRenderer(new CurrencyRenderer());
        totalColumn.setPreferredWidth(150);
    }

    private String formatarItensVenda(List<ItemVenda> itens) {
        // Exemplo: "Produto A (3), Produto B (2)"
        StringBuilder builder = new StringBuilder();
        for (ItemVenda item : itens) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(item.getProduto().getNome()).append(" (").append(item.getQuantidade()).append(")");
        }
        return builder.toString();
    }

    class DateRenderer extends DefaultTableCellRenderer {
        /**
         * 
         */
        private static final long serialVersionUID = -4565714607647023646L;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (value instanceof Date) {
                setText(formatter.format(value));
            }

            return component;
        }
    }

    class CurrencyRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {
            if (value instanceof Number) {
                value = currencyFormatter.format(value);
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}
