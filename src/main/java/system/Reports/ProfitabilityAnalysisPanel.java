package system.Reports;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entities.Produto;
import entities.Sistema;

public class ProfitabilityAnalysisPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7636237925973202030L;
	private JTable table;
    private DefaultTableModel tableModel;
    private Sistema sistema;

    public ProfitabilityAnalysisPanel(Sistema sistema) {
        this.sistema = sistema;
        setLayout(null);
        
        String[] columnNames = {"Nome do Produto", "Preço de Custo", "Preço de Venda", "Margem de Lucro"};
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
        scrollPane.setBounds(0, 10, 943, 300); 
        add(scrollPane);

        loadProductData();
    }


    private void loadProductData() {
        for (Produto produto : sistema.getProdutos()) {
            double custo = produto.getPrecoCusto();
            double venda = produto.getPrecoVenda();
            double margem = venda - custo;

            tableModel.addRow(new Object[]{
                produto.getNome(),
                String.format("R$ %.2f", custo),
                String.format("R$ %.2f", venda),
                String.format("R$ %.2f (%.2f%%)", margem, margem / custo * 100)
            });
        }
    }
}
