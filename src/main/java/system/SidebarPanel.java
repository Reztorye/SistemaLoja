package system;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import system.Promotion.PromotionsPanel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SidebarPanel extends JPanel {

    private static final long serialVersionUID = -1666701738391775114L;
    private CardLayout cardLayout;
    private JPanel cardPanel; // O painel que usa CardLayout
    private PromotionsPanel promotionsPanel;

    public SidebarPanel(CardLayout cardLayout, JPanel cardPanel, PromotionsPanel promotionsPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.promotionsPanel = promotionsPanel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Adicionando botões com os seus respectivos painéis
        addButton("Tela Principal", "MainPanel", null);
        addButton("Vendas", "SalesPanel", null);
        addButton("Entrada", "EntryPanel", null);
        addButton("Promoções", "PromotionsPanel", this::atualizarPromotionsPanel); // Passando uma ação para atualizar
        addButton("Produtos", "ProductsPanel", null);
        addButton("Clientes", "CustomersPanel", null);
        addButton("Relatórios", "ReportsPanel", null);

        // Define o tamanho preferido da barra lateral se desejar
        setPreferredSize(new Dimension(200, getHeight()));
    }

    private void addButton(String buttonText, String panelName, Runnable additionalAction) {
        JButton button = new JButton(buttonText);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (additionalAction != null) {
                    additionalAction.run(); // Executa a ação adicional
                }
                cardLayout.show(cardPanel, panelName);
            }
        });
        add(button);
    }

    // Método que será chamado antes de mostrar o PromotionsPanel
    private void atualizarPromotionsPanel() {
        promotionsPanel.atualizarListaProdutos(); // Chamada direta para atualizar
    }
}
