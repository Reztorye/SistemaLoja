package system.Reports;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entities.Categoria;
import entities.ItemVenda;
import entities.Sistema;
import entities.Venda;

public class SalesByCategoryPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6508291280027593846L;
	private JTable table;
    private DefaultTableModel tableModel;
    private Sistema sistema;

    public SalesByCategoryPanel(Sistema sistema) {
        this.sistema = sistema;
        setLayout(null);
        String[] columnNames = {"Categoria", "Total de Vendas"};
        tableModel = new DefaultTableModel(columnNames, 0){
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

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 10, 943, 240); 
        add(scrollPane);

        loadSalesData();
    }

    private void loadSalesData() {
        Map<Categoria, Double> salesByCategory = new HashMap<>();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")); // Formatador de moeda para o Brasil

        // Calcula o total de vendas por categoria
        for (Venda venda : sistema.getVendas()) {
            for (ItemVenda itemVenda : venda.getItensVenda()) {
                Categoria categoria = itemVenda.getProduto().getCategoria();
                double valorTotalItem = itemVenda.getQuantidade() * itemVenda.getProduto().getPrecoVenda();
                salesByCategory.merge(categoria, valorTotalItem, Double::sum);
            }
        }

        // Adiciona os dados calculados ao modelo da tabela
        salesByCategory.forEach((categoria, totalVendas) -> {
            tableModel.addRow(new Object[]{
                categoria.getNome(),
                currencyFormat.format(totalVendas) 
            });
        });
    }
}
