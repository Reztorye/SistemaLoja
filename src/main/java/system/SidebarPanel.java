package system;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import lombok.Getter;
import lombok.Setter;
import system.Promotion.PromotionsPanel;

@Getter
@Setter

public class SidebarPanel extends JPanel {

    private static final long serialVersionUID = -1666701738391775114L;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private PromotionsPanel promotionsPanel;
    
    public SidebarPanel(CardLayout cardLayout, JPanel cardPanel, PromotionsPanel promotionsPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.promotionsPanel = promotionsPanel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        addButton("Tela Principal", "MainPanel", null);
        addButton("Vendas", "SalesPanel", null);
        addButton("Promoções", "PromotionsPanel", () -> {
            promotionsPanel.atualizarListaProdutos(); 
            cardLayout.show(cardPanel, "PromotionsPanel");
        });
        addButton("Produtos", "ProductsPanel", null);
        addButton("Clientes", "CustomersPanel", null);
        addButton("Fornecedor", "AddSupplierPanel", null);
        addButton("Categoria", "AddCategoryPanel", null);
        addButton("Relatórios", "ReportsPanel", null);
        
        setPreferredSize(new Dimension(200, getHeight()));
    }

    private void addButton(String buttonText, String panelName, Runnable additionalAction) {
        JButton button = new JButton(buttonText);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (additionalAction != null) {
                    additionalAction.run();
                }
                cardLayout.show(cardPanel, panelName);
            }
        });
        add(button);
    }
}
