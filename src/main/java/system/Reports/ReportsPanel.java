package system.Reports;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import entities.Sistema;

public class ReportsPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2923599729964175275L;
	private JComboBox<String> reportTypeComboBox;
    private JPanel reportDisplayPanel;
    private Sistema sistema;

    public ReportsPanel(Sistema sistema) {
    	this.sistema = sistema;
        setLayout(new BorderLayout());

        String[] reportTypes = {"Relatório de Vendas", "Relatório de Produtos", "Relatório de Estoque", "Relatório de Clientes", "Produtos Mais Vendidos", "Relatório de Vendas por Período"};
        reportTypeComboBox = new JComboBox<>(reportTypes);
        reportTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySelectedReport(reportTypeComboBox.getSelectedItem().toString());
            }
        });

        reportDisplayPanel = new JPanel();
        reportDisplayPanel.setLayout(new BorderLayout());

        add(reportTypeComboBox, BorderLayout.NORTH);
        add(reportDisplayPanel, BorderLayout.CENTER);
    }

    private void displaySelectedReport(String reportType) {
        reportDisplayPanel.removeAll();

        switch (reportType) {
        	case "Relatório de Vendas":
        		SalesReportPanel salesReportPanel = new SalesReportPanel(sistema);
            	reportDisplayPanel.add(salesReportPanel, BorderLayout.CENTER);
            	break;
        	case "Produtos Mais Vendidos":
                TopSellingProductsPanel topSellingProductsPanel = new TopSellingProductsPanel(sistema);
                topSellingProductsPanel.setBounds(0, 0, reportDisplayPanel.getWidth(), reportDisplayPanel.getHeight());
                reportDisplayPanel.add(topSellingProductsPanel);
                topSellingProductsPanel.setVisible(true);
                break;
        	case "Relatório de Vendas por Período":
                SalesByPeriodPanel salesByPeriodPanel = new SalesByPeriodPanel(sistema);
                salesByPeriodPanel.setBounds(0, 0, reportDisplayPanel.getWidth(), reportDisplayPanel.getHeight());
                reportDisplayPanel.add(salesByPeriodPanel);
                salesByPeriodPanel.setVisible(true);
                break;
            case "Relatório de Estoque":

                break;
            case "Relatório de Clientes":

                break;
            default:

                break;
        }

        reportDisplayPanel.revalidate();
        reportDisplayPanel.repaint();
    }

}
