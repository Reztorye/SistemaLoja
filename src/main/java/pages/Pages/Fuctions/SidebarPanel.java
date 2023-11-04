package pages.Pages.Fuctions;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SidebarPanel extends JPanel {
    
    // Defina cada JLabel aqui para que possa ser acessado e modificado facilmente
    JLabel labelTelaPrincipal;
    JLabel labelVendas;
    JLabel labelEntrada;
    JLabel labelPromocoes;
    JLabel labelProdutos;
    JLabel labelClientes;
    JLabel labelRelatorios;
    private CardLayout cardLayout;
    private JPanel contentPanel;

    public SidebarPanel(CardLayout cardLayout, JPanel contentPanel) {
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;

        // Tela Principal
        labelTelaPrincipal = new JLabel("Tela Principal", SwingConstants.CENTER);
        labelTelaPrincipal.setForeground(Color.WHITE);
        labelTelaPrincipal.setFont(new Font("Yu Gothic UI", Font.BOLD, 30));
        labelTelaPrincipal.setOpaque(true);
        labelTelaPrincipal.setBackground(new Color(30, 30, 30));
        labelTelaPrincipal.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        labelTelaPrincipal.setBounds(0, 0, 206, 57);
        add(labelTelaPrincipal);

        // Vendas
        labelVendas = new JLabel("Vendas", SwingConstants.CENTER);
        labelVendas.setForeground(Color.WHITE);
        labelVendas.setFont(new Font("Yu Gothic UI", Font.BOLD, 30));
        labelVendas.setOpaque(true);
        labelVendas.setBackground(new Color(30, 30, 30));
        labelVendas.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        labelVendas.setBounds(0, 57, 206, 57);
        add(labelVendas);

        // Entrada
        labelEntrada = new JLabel("Entrada", SwingConstants.CENTER);
        labelEntrada.setForeground(Color.WHITE);
        labelEntrada.setFont(new Font("Yu Gothic UI", Font.BOLD, 30));
        labelEntrada.setOpaque(true);
        labelEntrada.setBackground(new Color(30, 30, 30));
        labelEntrada.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        labelEntrada.setBounds(0, 114, 206, 57);
        add(labelEntrada);

        // Promoções
        labelPromocoes = new JLabel("Promoções", SwingConstants.CENTER);
        labelPromocoes.setForeground(Color.WHITE);
        labelPromocoes.setFont(new Font("Yu Gothic UI", Font.BOLD, 30));
        labelPromocoes.setOpaque(true);
        labelPromocoes.setBackground(new Color(30, 30, 30));
        labelPromocoes.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        labelPromocoes.setBounds(0, 171, 206, 57);
        add(labelPromocoes);

        // Produtos
        labelProdutos = new JLabel("Produtos", SwingConstants.CENTER);
        labelProdutos.setForeground(Color.WHITE);
        labelProdutos.setFont(new Font("Yu Gothic UI", Font.BOLD, 30));
        labelProdutos.setOpaque(true);
        labelProdutos.setBackground(new Color(30, 30, 30));
        labelProdutos.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        labelProdutos.setBounds(0, 228, 206, 57);
        add(labelProdutos);

        // Clientes
        labelClientes = new JLabel("Clientes", SwingConstants.CENTER);
        labelClientes.setForeground(Color.WHITE);
        labelClientes.setFont(new Font("Yu Gothic UI", Font.BOLD, 30));
        labelClientes.setOpaque(true);
        labelClientes.setBackground(new Color(30, 30, 30));
        labelClientes.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        labelClientes.setBounds(0, 285, 206, 57);
        add(labelClientes);

        // Relatórios
        labelRelatorios = new JLabel("Relatórios", SwingConstants.CENTER);
        labelRelatorios.setForeground(Color.WHITE);
        labelRelatorios.setFont(new Font("Yu Gothic UI", Font.BOLD, 30));
        labelRelatorios.setOpaque(true);
        labelRelatorios.setBackground(new Color(30, 30, 30));
        labelRelatorios.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        labelRelatorios.setBounds(0, 342, 206, 57);
        add(labelRelatorios);

        // Adicione os eventos aqui para cada JLabel	
        labelProdutos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "PRODUCTS_PANEL");
            }
        });
        
        labelTelaPrincipal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "MAIN_PANEL");
            }
        });
    
    }
}