package system.Sales;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Manager.ClienteManager;
import Manager.ProdutoManager;
import Manager.Sistema;
import entities.Cliente;
import entities.ItemVenda;
import entities.Produto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SalesPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -391912408247070772L;
    private JTextField txtSKU;
    private JTextField txtQuantity;
    private JTable tableCart;
    private DefaultTableModel tableModel;
    private JButton btnAddToCart;
    private JButton btnFinalizeSale;
    private Sistema sistema;
    private JLabel lblVendas;
    private JButton btnVoltar;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ProdutoManager produtoManager;
    private ClienteManager clienteManager;

    public SalesPanel(Sistema sistema, CardLayout cardLayout, JPanel cardPanel, ProdutoManager produtoManager,
            ClienteManager clienteManager) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.sistema = sistema;
        this.produtoManager = produtoManager;
        this.clienteManager = clienteManager;
        setLayout(null);

        btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(881, 18, 89, 23);
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "ProductsPanel");
            }
        });
        add(btnVoltar);

        lblVendas = new JLabel("VENDAS");
        lblVendas.setFont(new Font("Arial", Font.BOLD, 30));
        lblVendas.setBounds(20, 8, 184, 30);
        add(lblVendas);

        JLabel lblSKU = new JLabel("SKU:");
        lblSKU.setBounds(20, 49, 80, 25);
        add(lblSKU);

        txtSKU = new JTextField();
        txtSKU.setBounds(110, 49, 165, 25);
        add(txtSKU);

        JLabel lblQuantity = new JLabel("Quantidade:");
        lblQuantity.setBounds(20, 78, 80, 25);
        add(lblQuantity);

        txtQuantity = new JTextField();
        txtQuantity.setBounds(110, 78, 165, 25);
        add(txtQuantity);

        btnAddToCart = new JButton("Adicionar ao Carrinho");
        btnAddToCart.setBounds(300, 78, 160, 25);
        add(btnAddToCart);

        btnFinalizeSale = new JButton("Finalizar Venda");
        btnFinalizeSale.setBounds(190, 425, 160, 25);
        add(btnFinalizeSale);

        JButton btnRemoveFromCart = new JButton("Remover do Carrinho");
        btnRemoveFromCart.setBounds(20, 425, 160, 25);
        add(btnRemoveFromCart);

        String[] columnNames = { "SKU", "Produto", "Preco", "Quantidade", "Total" };
        tableModel = new DefaultTableModel(columnNames, 0);
        tableCart = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(tableCart);
        scrollPane.setBounds(20, 114, 950, 300);
        add(scrollPane);

        btnAddToCart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String skuStr = txtSKU.getText();
                int quantity;
                try {
                    quantity = Integer.parseInt(txtQuantity.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SalesPanel.this, "Por favor entre com uma quantidade valida.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int sku = Integer.parseInt(skuStr);
                    Produto produto = findProductBySKU(sku);
                    if (produto != null) {
                        double price = produto.getPrecoVendaComDesconto();
                        double total = price * quantity;
                        tableModel.addRow(new Object[] { sku, produto.getNome(), price, quantity, total });
                        txtSKU.setText("");
                        txtQuantity.setText("");
                    } else {
                        JOptionPane.showMessageDialog(SalesPanel.this, "Produto não encontrado com SKU: " + sku,
                                "Erro do Produto", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SalesPanel.this, "Please entre com um SKU valido.", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnFinalizeSale.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Cliente cliente = obterClientePorID();
                if (cliente == null) {
                    JOptionPane.showMessageDialog(SalesPanel.this, "Nenhum cliente selecionado.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                List<ItemVenda> itensVenda = new ArrayList<>();

                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    Integer sku = (Integer) tableModel.getValueAt(i, 0);
                    int quantidade = (Integer) tableModel.getValueAt(i, 3);
                    Produto produto = produtoManager.buscarProdutoPorSku(sku);
                    if (produto != null) {
                        if (quantidade > produto.getEstoqueDisponivel()) {
                            JOptionPane.showMessageDialog(SalesPanel.this,
                                    "Quantidade solicitada para o produto '" + produto.getNome()
                                            + "' excede o estoque disponível.",
                                    "Erro de Estoque", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        itensVenda.add(new ItemVenda(produto, quantidade));
                    }
                }

                sistema.realizarVenda(cliente, itensVenda);

                tableModel.setRowCount(0);
                JOptionPane.showMessageDialog(SalesPanel.this, "Venda finalizada com sucesso!", "Venda Concluída",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnRemoveFromCart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableCart.getSelectedRow();
                if (selectedRow >= 0) {
                    int confirm = JOptionPane.showConfirmDialog(
                            SalesPanel.this,
                            "Tem certeza que deseja remover o item selecionado?",
                            "Remover Item",
                            JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        tableModel.removeRow(selectedRow);
                    }
                } else {
                    JOptionPane.showMessageDialog(SalesPanel.this, "Por favor, selecione um item para remover.",
                            "Nenhum item selecionado", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    private Cliente obterClientePorID() {
        String clienteIdStr = JOptionPane.showInputDialog(this, "Digite o ID do cliente:");
        if (clienteIdStr != null && !clienteIdStr.trim().isEmpty()) {
            try {
                int clienteId = Integer.parseInt(clienteIdStr.trim());
                return clienteManager.buscarClientePorId(clienteId);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro de Formato",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    private Produto findProductBySKU(Integer sku) {
        for (Produto produto : produtoManager.getProdutos()) {
            if (produto.getSku().equals(sku)) {
                return produto;
            }
        }
        return null;
    }

    public void atualizarTabelaProdutos() {
        tableModel.setRowCount(0);

        List<Produto> produtos = produtoManager.getProdutos();
        for (Produto produto : produtos) {
            tableModel.addRow(new Object[] {
                    produto.getSku(),
                    produto.getNome(),
                    produto.getCategoria().getNome(),
                    produto.getFornecedor().getNome(),
                    produto.getDescricao(),
                    produto.getPrecoCusto(),
                    produto.getPrecoVendaComDesconto(),
                    produto.getEstoqueDisponivel()
            });
        }
    }

}