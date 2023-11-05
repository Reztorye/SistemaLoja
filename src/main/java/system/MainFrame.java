package system;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.List;

// MainFrame.java
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import entities.Produto;
import system.CRUDProducts.AddProductPanel;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel; // Contém todos os painéis da aplicação
    private List<Produto> produtos = new ArrayList<>();

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

        // TODO: adicionar outros painéis aqui conforme forem criados

        // Cria a barra lateral
        SidebarPanel sidebarPanel = new SidebarPanel(cardLayout, cardPanel);
        
        ProductsPanel productsPanel = new ProductsPanel(cardLayout, cardPanel);
        cardPanel.add(productsPanel, "ProductsPanel");
        
        AddProductPanel addProductPanel = new AddProductPanel(cardLayout, cardPanel, null); // produtos é a lista de produtos
        cardPanel.add(addProductPanel, "AddProductPanel"); // Adicionando ao CardLayout
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
