package system.Supplier;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import entities.Fornecedor;
import entities.Sistema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddSupplierPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2902349697031729515L;
	private JTextField txtNomeFornecedor;
    private JButton btnAddFornecedor, btnCancelFornecedor;
    private Sistema sistema;
    private JButton backButton;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public AddSupplierPanel(Sistema sistema, CardLayout cardLayout, JPanel cardPanel) {
        this.sistema = sistema;
        setLayout(null);

        JLabel lblNomeFornecedor = new JLabel("Nome do Fornecedor:");
        lblNomeFornecedor.setBounds(50, 50, 150, 25);
        add(lblNomeFornecedor);

        txtNomeFornecedor = new JTextField(20);
        txtNomeFornecedor.setBounds(210, 50, 230, 25);
        add(txtNomeFornecedor);

        btnAddFornecedor = new JButton("Adicionar Fornecedor");
        btnAddFornecedor.setBounds(50, 100, 170, 30);
        btnAddFornecedor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomeFornecedor = txtNomeFornecedor.getText().trim();
                Fornecedor fornecedor = sistema.adicionarFornecedor(nomeFornecedor);
                if (fornecedor != null) {
                    JOptionPane.showMessageDialog(AddSupplierPanel.this,
                            "Fornecedor adicionado: " + fornecedor.getNome(),
                            "Fornecedor Adicionado",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                txtNomeFornecedor.setText("");
            }
        });
        add(btnAddFornecedor);

        btnCancelFornecedor = new JButton("Cancelar");
        btnCancelFornecedor.setBounds(250, 100, 150, 30);
        btnCancelFornecedor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtNomeFornecedor.setText(""); 
                
            }
        });
        add(btnCancelFornecedor);
        
        backButton = new JButton("Voltar");
        backButton.setBounds(360, 9, 80, 30);
        add(backButton);
        
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "MainPanel"));
    }
}
