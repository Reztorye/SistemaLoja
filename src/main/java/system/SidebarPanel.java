package system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SidebarPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1666701738391775114L;
	private CardLayout cardLayout;
    private JPanel cardPanel; // O painel que usa CardLayout

    public SidebarPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Adicionando botões com os seus respectivos painéis
        addButton("Tela Principal", "MainPanel");
        addButton("Vendas", "SalesPanel");
        addButton("Entrada", "EntryPanel");
        addButton("Promoções", "PromotionsPanel");
        addButton("Produtos", "ProductsPanel");
        addButton("Clientes", "ClientsPanel");
        addButton("Relatórios", "ReportsPanel");

        // Define o tamanho preferido da barra lateral se desejar
        setPreferredSize(new Dimension(200, getHeight()));
    }

    private void addButton(String buttonText, String panelName) {
        JButton button = new JButton(buttonText);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("oi");
                cardLayout.show(cardPanel, panelName);
            }
        });
        add(button);
    }
}
