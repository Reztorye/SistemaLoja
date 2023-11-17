package system.Reports;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entities.Sistema;

public class ReportsPanel extends JPanel {

    private static final long serialVersionUID = -2923599729964175275L;
    private JComboBox<String> reportTypeComboBox;
    private JPanel reportDisplayPanel;
    private Sistema sistema;
    private JLabel lblSelectReport; 
    private JLabel lblRelatorio;
    
    
    public ReportsPanel(Sistema sistema) {
        this.sistema = sistema;
        setLayout(null); 
        
        lblRelatorio = new JLabel("RELATORIOS");
        lblRelatorio.setFont(new Font("Arial", Font.BOLD, 30));
        lblRelatorio.setBounds(20, 11, 214, 30);
        add(lblRelatorio);
        
        lblSelectReport = new JLabel("SELECIONE UM RELATÓRIO");
        lblSelectReport.setFont(new Font("Arial", Font.BOLD, 14));
        lblSelectReport.setBounds(20, 52, 200, 30); 
        add(lblSelectReport);

        String[] reportTypes = {"Relatorio de Estoque Baixo", "Relatório de Vendas", "Produtos Mais Vendidos", "Relatório de Vendas por Período",
                "Clientes mais Valiosos", "Análise de Lucratividade", "Vendas por Categoria"};
        reportTypeComboBox = new JComboBox<>(reportTypes);
        reportTypeComboBox.setBounds(20, 81, 300, 20); 
        reportTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySelectedReport(reportTypeComboBox.getSelectedItem().toString());
            }
        });
        add(reportTypeComboBox);

        reportDisplayPanel = new JPanel();
        reportDisplayPanel.setLayout(new BorderLayout());
        reportDisplayPanel.setBounds(20, 112, 1106, 402); 
        add(reportDisplayPanel);
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
        	case "Clientes mais Valiosos":
        		TopCustomersPanel topCustomersPanel = new TopCustomersPanel(sistema);
        		topCustomersPanel.setBounds(0, 0, reportDisplayPanel.getWidth(), reportDisplayPanel.getHeight());
                reportDisplayPanel.add(topCustomersPanel);
                topCustomersPanel.setVisible(true);
                break;
        	case "Análise de Lucratividade":
        		ProfitabilityAnalysisPanel profitabilityAnalysisPanel = new ProfitabilityAnalysisPanel(sistema);
        		profitabilityAnalysisPanel.setBounds(0, 0, reportDisplayPanel.getWidth(), reportDisplayPanel.getHeight());
                reportDisplayPanel.add(profitabilityAnalysisPanel);
                profitabilityAnalysisPanel.setVisible(true);
                break;
        	case "Vendas por Categoria":
        		SalesByCategoryPanel salesByCategoryPanel = new SalesByCategoryPanel(sistema);
        		salesByCategoryPanel.setBounds(0, 0, reportDisplayPanel.getWidth(), reportDisplayPanel.getHeight());
                reportDisplayPanel.add(salesByCategoryPanel);
                salesByCategoryPanel.setVisible(true);
                break;
        	case "Relatorio de Estoque Baixo":
        		LowStockReportPanel lowStockReportPanel = new LowStockReportPanel(sistema);
        		lowStockReportPanel.setBounds(0, 0, reportDisplayPanel.getWidth(), reportDisplayPanel.getHeight());
                reportDisplayPanel.add(lowStockReportPanel);
                lowStockReportPanel.setVisible(true);
                break;    
            default:

                break;
        }

        reportDisplayPanel.revalidate();
        reportDisplayPanel.repaint();
    }

}
