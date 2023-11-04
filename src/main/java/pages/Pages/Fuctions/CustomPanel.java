package pages.Pages.Fuctions;

import javax.swing.JPanel;
import java.awt.Graphics;

public class CustomPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Desenhe algo no painel
        g.drawString("Olá, Ruan!", 10, 20);
    }
}
