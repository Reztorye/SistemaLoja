package system.Reports;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entities.Cliente;
import entities.Sistema;
import entities.Venda;

public class TopCustomersPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1790760558483656958L;
	private JTable table;
    private DefaultTableModel tableModel;
    private Sistema sistema;

    public TopCustomersPanel(Sistema sistema) {
        this.sistema = sistema;
        setLayout(null);
        
        tableModel = new DefaultTableModel(){
            /**
			 * 
			 */
			private static final long serialVersionUID = -9049266189071413309L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("Nome do Cliente");
        tableModel.addColumn("Total de Compras");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 480, 280);
        add(scrollPane);
        
        loadTopCustomers();
    }
    
    private void loadTopCustomers() {
        Map<Cliente, Double> totalPorCliente = new HashMap<>();

        // Calcula o total de compras por cliente
        for (Venda venda : sistema.getVendas()) {
            Cliente cliente = venda.getCliente();
            double total = venda.calcularValorTotal();
            totalPorCliente.merge(cliente, total, Double::sum);
        }

        // Ordena e adiciona ao modelo da tabela
        totalPorCliente.entrySet().stream()
            .sorted(Map.Entry.<Cliente, Double>comparingByValue().reversed())
            .forEach(entry -> {
                tableModel.addRow(new Object[]{entry.getKey().getNome(), entry.getValue()});
            });
    }
}
