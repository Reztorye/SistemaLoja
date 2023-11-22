package system.Category;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Manager.Sistema;
import entities.Categoria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCategoryPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8207909782982191996L;
	private JTextField txtNomeCategoria;
    private JButton btnAddCategoria, btnCancelCategoria;
    private Sistema sistema;
    private JButton backButton;

    public AddCategoryPanel(Sistema sistema, CardLayout cardLayout, JPanel cardPanel) {
        this.sistema = sistema;
        setLayout(null);

        JLabel lblNomeCategoria = new JLabel("Nome da Categoria:");
        lblNomeCategoria.setBounds(50, 50, 150, 25);
        add(lblNomeCategoria);

        txtNomeCategoria = new JTextField(20);
        txtNomeCategoria.setBounds(210, 50, 200, 25);
        add(txtNomeCategoria);

        btnAddCategoria = new JButton("Adicionar Categoria");
        btnAddCategoria.setBounds(50, 100, 180, 30);
        btnAddCategoria.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomeCategoria = txtNomeCategoria.getText().trim();
                if (!nomeCategoria.isEmpty()) {
                    Categoria categoria = sistema.adicionarCategoriaFirebase(nomeCategoria);
                    if (categoria != null) {
                        JOptionPane.showMessageDialog(AddCategoryPanel.this,
                                "Categoria adicionada com sucesso: " + categoria.getNome(),
                                "Operação Concluída",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(AddCategoryPanel.this,
                                "Erro ao adicionar categoria.",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    txtNomeCategoria.setText("");
                } else {
                    JOptionPane.showMessageDialog(AddCategoryPanel.this,
                            "O nome da categoria não pode ser vazio.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(btnAddCategoria);

        backButton = new JButton("Voltar");
        backButton.setBounds(360, 9, 80, 30);
        add(backButton);

        backButton.addActionListener(e -> cardLayout.show(cardPanel, "MainPanel"));

        btnCancelCategoria = new JButton("Cancelar");
        btnCancelCategoria.setBounds(240, 100, 170, 30);
        btnCancelCategoria.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtNomeCategoria.setText("");
            }
        });
        add(btnCancelCategoria);
    }
}
