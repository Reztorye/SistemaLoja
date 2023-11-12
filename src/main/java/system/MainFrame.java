	package system;
	import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import entities.Sistema;
import system.CRUDCustomers.CustomersPanel;
import system.CRUDProducts.AddProductPanel;
import system.CRUDProducts.ProductsPanel;
import system.Promotion.PromotionsPanel;
import system.Reports.ReportsPanel;
import system.Reports.SalesReportPanel;
import system.Sales.SalesPanel;
	
	public class MainFrame extends JFrame {
	    /**
		 * 
		 */
		private static final long serialVersionUID = -6340235651217630471L;
		private CardLayout cardLayout;
	    private JPanel cardPanel; // Contém todos os painéis da aplicação
	    private DefaultTableModel tableModel;
	    private Sistema sistema = new Sistema();
	   
	
	    @SuppressWarnings("unused")
		public MainFrame() {
	        // Configurações iniciais do JFrame
	        setTitle("Sistema de Gestão para Loja de Eletrônicos");
	        setSize(1200, 800);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null); // Centraliza a janela
	
	        // Inicializa o cardLayout e o painel que contém os cards
	        cardLayout = new CardLayout();
	        cardPanel = new JPanel(cardLayout);
	
	        // Cria o painel principal e o adiciona ao cardPanel
	        MainPanel mainPanel = new MainPanel();
	        cardPanel.add(mainPanel, "MainPanel");
	
	        SalesReportPanel salesReportPanel = new SalesReportPanel(sistema);
	        cardPanel.add(salesReportPanel, "SalesReportPanel");
	        
	        ProductsPanel productsPanel = new ProductsPanel(cardLayout, cardPanel, sistema);
	        cardPanel.add(productsPanel, "ProductsPanel");
	        
	        CustomersPanel customersPanel = new CustomersPanel(cardLayout, cardPanel, sistema);
	        cardPanel.add(customersPanel, "CustomersPanel");
	        
	        SalesPanel salesPanel = new SalesPanel(sistema);
	        cardPanel.add(salesPanel, "SalesPanel");
	        
	        PromotionsPanel promotionsPanel = new PromotionsPanel(sistema, productsPanel);
	        cardPanel.add(promotionsPanel, "PromotionsPanel");
	        
	        AddProductPanel addProductPanel = new AddProductPanel(cardLayout, cardPanel, tableModel, sistema);
	        
	     // No MainFrame ou onde você instanciar ReportsPanel:
	        ReportsPanel reportsPanel = new ReportsPanel(sistema);
	        cardPanel.add(reportsPanel, "ReportsPanel");
	        
	        SidebarPanel sidebarPanel = new SidebarPanel(cardLayout, cardPanel, promotionsPanel);
	        
	       
	        // Adiciona a barra lateral ao frame
	        getContentPane().add(sidebarPanel, BorderLayout.WEST);
	        add(cardPanel);
	
	        // Define o painel principal para ser exibido inicialmente
	        cardLayout.show(cardPanel, "MainPanel");
	    }
	
	    // Método para alternar entre os painéis
	    public void showPanel(String panelName) {
	        cardLayout.show(cardPanel, panelName);
	    }
	
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            MainFrame frame = new MainFrame();
	            frame.setVisible(true);
	        });
	    }
	}
