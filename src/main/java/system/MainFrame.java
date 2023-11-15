	package system;
	import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import entities.Sistema;
import system.CRUDCustomers.CustomersPanel;
import system.CRUDProducts.ProductsPanel;
import system.Category.AddCategoryPanel;
import system.Promotion.PromotionsPanel;
import system.Reports.ReportsPanel;
import system.Reports.SalesReportPanel;
import system.Sales.SalesPanel;
import system.Supplier.AddSupplierPanel;
	
	public class MainFrame extends JFrame {
	    /**
		 * 
		 */
		private static final long serialVersionUID = -6340235651217630471L;
		private CardLayout cardLayout;
	    private JPanel cardPanel; // Contém todos os painéis da aplicação
	    private Sistema sistema = new Sistema();   
	
		public MainFrame() {
			sistema.inicializarDadosDeTeste();
	        setTitle("Sistema de Gestão para Loja de Eletrônicos");
	        setSize(1200, 800);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null); // Centraliza a janela
	
	        cardLayout = new CardLayout();
	        cardPanel = new JPanel(cardLayout);
	
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
	        
	        ReportsPanel reportsPanel = new ReportsPanel(sistema);
	        cardPanel.add(reportsPanel, "ReportsPanel");
	        
	        AddSupplierPanel addSupplierPanel = new AddSupplierPanel(sistema, cardLayout, cardPanel);
	        cardPanel.add(addSupplierPanel, "AddSupplierPanel");
	        
	        AddCategoryPanel addCategoryPanel = new AddCategoryPanel(sistema, cardLayout, cardPanel);
	        cardPanel.add(addCategoryPanel, "AddCategoryPanel");
	        
	        SidebarPanel sidebarPanel = new SidebarPanel(cardLayout, cardPanel, promotionsPanel);
	        
	        getContentPane().add(sidebarPanel, BorderLayout.WEST);
	        add(cardPanel);
	        cardLayout.show(cardPanel, "MainPanel");
	    }
	
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
