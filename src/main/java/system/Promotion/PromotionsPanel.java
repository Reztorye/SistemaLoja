package system.Promotion;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import com.toedter.calendar.JDateChooser;

import entities.Categoria;
import entities.Produto;
import entities.Sistema;
import lombok.Getter;
import lombok.Setter;
import system.CRUDProducts.ProductsPanel;

@Getter
@Setter
public class PromotionsPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3830440178314486669L;
	private Sistema sistema;
	private JComboBox<Categoria> comboCategorias;
    private JComboBox<Produto> comboProdutos;
    private JTextField txtPercentualDesconto;
    private JFormattedTextField txtDataInicio, txtDataFim;
    private JButton btnAdicionarPromocao, btnCancelar;
    private ProductsPanel productsPanel;
    private JDateChooser dateChooserInicio, dateChooserFim;
    private JButton btnVoltar;
    private CardLayout cardLayout;
    private JPanel cardPanel; 

    public PromotionsPanel(Sistema sistema, ProductsPanel productsPanel, CardLayout cardLayout, JPanel cardPanel) {
        this.sistema = sistema;
        this.productsPanel = productsPanel;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        setLayout(null); 
        initializeUI();
    }

    private void initializeUI() {
    	
    	JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(10, 10, 80, 25);
        add(lblCategoria);

        comboCategorias = new JComboBox<Categoria>(new DefaultComboBoxModel<Categoria>(
        	    sistema.getCategorias().toArray(new Categoria[0])));
        comboCategorias.setBounds(100, 10, 165, 25);
        comboCategorias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Categoria categoriaSelecionada = (Categoria) comboCategorias.getSelectedItem();
                DefaultComboBoxModel<Produto> produtoModel = new DefaultComboBoxModel<>();
                for (Produto produto : sistema.getProdutosPorCategoria(categoriaSelecionada)) {
                    produtoModel.addElement(produto);
                }
                comboProdutos.setModel(produtoModel);
            }
        });
        add(comboCategorias);
        
    	
        JLabel lblProduto = new JLabel("Produto:");
        lblProduto.setBounds(10, 54, 80, 25);
        add(lblProduto);

        comboProdutos = new JComboBox<Produto>(new DefaultComboBoxModel<Produto>(sistema.getProdutos().toArray(new Produto[0])));
        comboProdutos.setRenderer(new DefaultListCellRenderer() {
            /**
			 * 
			 */
			private static final long serialVersionUID = -774078213740267784L;

			@Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Produto) {
                    setText(((Produto) value).getNome());
                }
                return this;
            }
        });
        comboProdutos.setBounds(100, 54, 165, 25);
        add(comboProdutos);
        
        JLabel lblPercentualDesconto = new JLabel("Percentual de Desconto:");
        lblPercentualDesconto.setBounds(10, 90, 150, 25);
        add(lblPercentualDesconto);

        txtPercentualDesconto = new JTextField();
        txtPercentualDesconto.setBounds(170, 90, 95, 25);
        add(txtPercentualDesconto);

        JLabel lblDataInicio = new JLabel("Data de Início:");
        lblDataInicio.setBounds(10, 126, 80, 25);
        add(lblDataInicio);

        dateChooserInicio = new JDateChooser();
        dateChooserInicio.setBounds(100, 126, 165, 25);
        add(dateChooserInicio);

        JLabel lblDataFim = new JLabel("Data de Fim:");
        lblDataFim.setBounds(10, 164, 80, 25);
        add(lblDataFim);

        dateChooserFim = new JDateChooser();
        dateChooserFim.setBounds(100, 164, 165, 25);
        add(dateChooserFim);

        btnAdicionarPromocao = new JButton("Adicionar Promoção");
        btnAdicionarPromocao.setBounds(10, 200, 165, 25);
        btnAdicionarPromocao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarPromocao();
            }
        });
        add(btnAdicionarPromocao);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(180, 200, 85, 25);
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparFormulario();
            }
        });
        add(btnCancelar);
       
        btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(351, 271, 89, 23);
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "ProductsPanel"); 
            }
        });
        add(btnVoltar);
    }

    private void adicionarPromocao() {
        try {
        	Produto produto = (Produto) comboProdutos.getSelectedItem();
        	double percentualDesconto = Double.parseDouble(txtPercentualDesconto.getText().replace(",", "."));
            
            produto.setDescontoAtivo(true);
            produto.setValorDesconto(percentualDesconto);

            productsPanel.atualizarTabelaProdutos(); 
            
            limparFormulario();

            JOptionPane.showMessageDialog(this, "Promoção adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um valor de desconto válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar a promoção: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparFormulario() {
        comboProdutos.setSelectedIndex(-1);
        txtPercentualDesconto.setText("");
        dateChooserInicio.setCalendar(null);
        dateChooserFim.setCalendar(null);
    }
    
    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (aFlag) {
            atualizarListaProdutos();
        }
    }
    
    public void atualizarListaProdutos() {
        comboProdutos.removeAllItems();
        
        for (Produto produto : sistema.getProdutos()) {
            comboProdutos.addItem(produto);
        }
    }
}
