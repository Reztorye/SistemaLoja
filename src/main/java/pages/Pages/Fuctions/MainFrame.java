package pages.Pages.Fuctions;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    // Mova o cardLayout para fora do construtor para que possa ser acessado pelo SidebarPanel
    CardLayout cardLayout = new CardLayout();
    
    // Mova o content para fora do construtor para que possa ser acessado pelo SidebarPanel
    JPanel content = new JPanel(cardLayout);
    
    ProductsPanel productsPanel = new ProductsPanel();

    public MainFrame() {
        setTitle("Eletrônica Pikachu");
        setSize(1089, 491);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        
        // Instanciar SidebarPanel
        SidebarPanel sidebar = new SidebarPanel(cardLayout, content); 
        sidebar.setPreferredSize(new Dimension(206, 400));
        sidebar.setBackground(Color.DARK_GRAY);
        
        sidebar.setBounds(0, 0, 206, 400);
        content.setBounds(206, 0, getWidth() - 206, 400); 
        
        ProductsPanel productsPanel = new ProductsPanel();
        content.add(productsPanel, "PRODUCTS_PANEL");
        
        MainPanel mainPanel = new MainPanel();
        content.add(mainPanel, "MAIN_PANEL");  
        
        getContentPane().add(sidebar);
        getContentPane().add(content);
           
        // Mostrando o MainPanel inicialmente
        cardLayout.show(content, "MAIN_PANEL");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}