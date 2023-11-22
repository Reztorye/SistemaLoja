package system.Supplier;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Manager.FornecedorManager;
import Manager.Sistema;
import entities.Fornecedor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddSupplierPanel extends JPanel {
    private static final long serialVersionUID = 2902349697031729515L;
    private JTextField txtNomeFornecedor;
    private JButton btnAddFornecedor, btnCancelFornecedor;
    private Sistema sistema;
    private JButton backButton;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private FornecedorManager fornecedorManager; // Corrigido para o tipo adequado

    public AddSupplierPanel(Sistema sistema, CardLayout cardLayout, JPanel cardPanel,
            FornecedorManager fornecedorManager) {
        this.sistema = sistema;
        this.fornecedorManager = fornecedorManager; // Corrigido para o tipo adequado
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
                String nomeFornecedor = txtNomeFornecedor.getText().trim().toUpperCase();
                if (!nomeFornecedor.isEmpty()) {
                    Fornecedor fornecedor = sistema.adicionarFornecedorFirebase(nomeFornecedor);
                    if (fornecedor != null) {
                        JOptionPane.showMessageDialog(AddSupplierPanel.this,
                                "Fornecedor adicionado com sucesso: " + fornecedor.getNome(),
                                "Fornecedor Adicionado",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(AddSupplierPanel.this,
                                "Erro ao adicionar fornecedor.",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    txtNomeFornecedor.setText("");
                } else {
                    JOptionPane.showMessageDialog(AddSupplierPanel.this,
                            "O nome do fornecedor não pode ser vazio.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
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
