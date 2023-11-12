package system.Reports;
import java.awt.BorderLayout;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import entities.ItemVenda;
import entities.Sistema;
import entities.Venda;

public class SalesReportPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4659160089766279374L;
	private JTable salesTable;
    private DefaultTableModel tableModel;
    private Sistema sistema;
    public SalesReportPanel(Sistema sistema) {
        this.sistema = sistema;
        String[] columnNames = {"Data da Venda", "Nome do Cliente", "Itens Vendidos", "Valor Total"};
        tableModel = new DefaultTableModel(columnNames, 0);
        salesTable = new JTable(tableModel);

        // Adiciona a tabela ao painel
        this.add(new JScrollPane(salesTable), BorderLayout.CENTER);
        TableColumn dateColumn = salesTable.getColumn("Data da Venda");
        dateColumn.setCellRenderer(new DateRenderer());
        
        // Ajustar larguras das colunas conforme necessário
        dateColumn.setPreferredWidth(150);
        // Carregar dados reais
        loadData();
    }

    protected void loadData() {
        // Limpa o modelo antigo
        tableModel.setRowCount(0);

        // Obter a lista de vendas do sistema
        List<Venda> vendas = sistema.getVendas();

        // Adicionar dados ao modelo da tabela
        for (Venda venda : vendas) {
            Object[] row = new Object[]{
                venda.getData(),
                venda.getCliente().getNome(),
                formatarItensVenda(venda.getItensVenda()),
                venda.calcularValorTotal()
            };
            tableModel.addRow(row);
        }
    }


    private String formatarItensVenda(List<ItemVenda> itens) {
        // Formate a lista de itens de venda para exibição
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            // Primeiro, permite que o renderizador padrão prepare o componente
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            // Se o valor for uma instância de Date, formate-o.
            if (value instanceof Date) {
                setText(formatter.format(value));
            }
            
            return component;
        }
    }
}
