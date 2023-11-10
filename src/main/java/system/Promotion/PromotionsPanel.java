package system.Promotion;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entities.Produto;
import entities.Promocao;
import entities.Sistema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionsPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3830440178314486669L;
	private Sistema sistema;
    private JComboBox<Produto> comboProdutos;
    private JTextField txtPercentualDesconto;
    private JFormattedTextField txtDataInicio, txtDataFim;
    private JButton btnAdicionarPromocao, btnCancelar;

    public PromotionsPanel(Sistema sistema) {
        this.sistema = sistema;
        setLayout(null);
        initializeUI();
    }

    private void initializeUI() {
        // Componentes e suas posições com layout null
        JLabel lblProduto = new JLabel("Produto:");
        lblProduto.setBounds(10, 10, 80, 25);
        add(lblProduto);

        comboProdutos = new JComboBox<Produto>(new DefaultComboBoxModel<Produto>(sistema.getProdutos().toArray(new Produto[0])));
        comboProdutos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Produto) {
                    setText(((Produto) value).getNome());
                }
                return this;
            }
        });
        comboProdutos.setBounds(100, 10, 165, 25);
        add(comboProdutos);
        JLabel lblPercentualDesconto = new JLabel("Percentual de Desconto:");
        lblPercentualDesconto.setBounds(10, 45, 150, 25);
        add(lblPercentualDesconto);

        txtPercentualDesconto = new JTextField();
        txtPercentualDesconto.setBounds(170, 45, 95, 25);
        add(txtPercentualDesconto);

        JLabel lblDataInicio = new JLabel("Data de Início:");
        lblDataInicio.setBounds(10, 80, 80, 25);
        add(lblDataInicio);

        txtDataInicio = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        txtDataInicio.setBounds(100, 80, 165, 25);
        add(txtDataInicio);

        JLabel lblDataFim = new JLabel("Data de Fim:");
        lblDataFim.setBounds(10, 115, 80, 25);
        add(lblDataFim);

        txtDataFim = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        txtDataFim.setBounds(100, 115, 165, 25);
        add(txtDataFim);

        btnAdicionarPromocao = new JButton("Adicionar Promoção");
        btnAdicionarPromocao.setBounds(10, 150, 165, 25);
        btnAdicionarPromocao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarPromocao();
            }
        });
        add(btnAdicionarPromocao);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(180, 150, 85, 25);
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparFormulario();
            }
        });
        add(btnCancelar);
    }

    private void adicionarPromocao() {
        try {
            Produto produto = (Produto) comboProdutos.getSelectedItem();
            double percentual = Double.parseDouble(txtPercentualDesconto.getText().replace(",", "."));
            Date dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(txtDataInicio.getText());
            Date dataFim = new SimpleDateFormat("dd/MM/yyyy").parse(txtDataFim.getText());

            if (dataInicio.after(dataFim)) {
                JOptionPane.showMessageDialog(this, "Data de início deve ser antes da data de fim.", "Erro de Data", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Promocao promocao = new Promocao(produto, percentual, dataInicio, dataFim);
            sistema.adicionarPromocao(promocao);
            JOptionPane.showMessageDialog(this, "Promoção adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparFormulario();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar a promoção: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparFormulario() {
        comboProdutos.setSelectedIndex(0);
        txtPercentualDesconto.setText("");
        txtDataInicio.setValue(null);
        txtDataFim.setValue(null);
    }
    
    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (aFlag) {
            atualizarListaProdutos();
        }
    }

    public void atualizarListaProdutos() {
        // Limpa os itens existentes
        comboProdutos.removeAllItems();
        
        // Adiciona os produtos atualizados
        for (Produto produto : sistema.getProdutos()) {
            comboProdutos.addItem(produto);
        }
    }
}
