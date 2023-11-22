package system.Sales;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Manager.ClienteManager;
import Manager.ProdutoManager;
import Manager.SalesManager;
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
    private SalesManager salesManager;
    private JTextField txtNomeProduto;

    public SalesPanel(Sistema sistema, CardLayout cardLayout, JPanel cardPanel, ProdutoManager produtoManager,
            ClienteManager clienteManager, SalesManager salesManager) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.sistema = sistema;
        this.produtoManager = produtoManager;
        this.clienteManager = clienteManager;
        this.salesManager = salesManager;
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

        JLabel lblSearchByName = new JLabel("Pesquisar por Nome:");
        lblSearchByName.setBounds(20, 49, 160, 25);
        add(lblSearchByName);

        txtNomeProduto = new JTextField();
        txtNomeProduto.setBounds(150, 49, 170, 25);
        add(txtNomeProduto);

        JButton btnSearchByName = new JButton("Pesquisar");
        btnSearchByName.setBounds(350, 49, 160, 25);
        add(btnSearchByName);

        JLabel lblSKU = new JLabel("SKU:");
        lblSKU.setBounds(20, 84, 160, 25);
        add(lblSKU);

        txtSKU = new JTextField();
        txtSKU.setBounds(60, 84, 80, 25);
        add(txtSKU);

        JLabel lblQuantity = new JLabel("Quantidade:");
        lblQuantity.setBounds(160, 84, 160, 25);
        add(lblQuantity);

        txtQuantity = new JTextField();
        txtQuantity.setBounds(240, 84, 80, 25);
        add(txtQuantity);

        btnAddToCart = new JButton("Adicionar ao Carrinho");
        btnAddToCart.setBounds(350, 84, 160, 25);
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

                salesManager.realizarVenda(cliente, itensVenda);
                addSales(cliente, itensVenda);
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

        btnSearchByName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = txtNomeProduto.getText();
                produtoManager.buscarProdutosPorNomeFirebase(nome, produtosEncontrados -> {

                    if (!produtosEncontrados.isEmpty()) {
                        // Se apenas um produto for encontrado, preenche automaticamente
                        if (produtosEncontrados.size() == 1) {
                            Produto produtoEncontrado = produtosEncontrados.get(0);
                            txtSKU.setText(String.valueOf(produtoEncontrado.getSku()));
                        } else {
                            // Se múltiplos produtos forem encontrados, permite que o usuário escolha
                            Produto produtoEscolhido = (Produto) JOptionPane.showInputDialog(
                                    SalesPanel.this,
                                    "Selecione um produto:",
                                    "Produtos Encontrados",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    produtosEncontrados.toArray(),
                                    produtosEncontrados.get(0));

                            // Se um produto foi escolhido, atualiza o campo SKU
                            if (produtoEscolhido != null) {
                                txtSKU.setText(String.valueOf(produtoEscolhido.getSku()));
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(SalesPanel.this, "Nenhum produto encontrado com o nome: " + nome,
                                "Erro do Produto", JOptionPane.ERROR_MESSAGE);
                    }
                });
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
                    produto.getCategoria(),
                    produto.getFornecedor(),
                    produto.getDescricao(),
                    produto.getPrecoCusto(),
                    produto.getPrecoVendaComDesconto(),
                    produto.getEstoqueDisponivel()
            });
        }
    }

    private void addSales(Cliente cliente, List<ItemVenda> itensVenda) {
        // Calcula o total da venda
        double totalVenda = itensVenda.stream()
                .mapToDouble(item -> item.getProduto().getPrecoVenda() *
                        item.getQuantidade())
                .sum();

        // Cria uma referência para o nó "sales" no Firebase
        DatabaseReference salesRef = FirebaseDatabase.getInstance().getReference("vendas");

        // Cria um novo nó para armazenar informações da venda
        DatabaseReference newSaleRef = salesRef.push();

        // Preparar dados da venda para enviar
        Map<String, Object> vendaData = new HashMap<>();
        vendaData.put("clienteId", cliente.getFirebaseId());
        vendaData.put("itensVenda", itensVenda.stream().map(this::convertItemVendaToMap).collect(Collectors.toList()));
        vendaData.put("totalVenda", totalVenda);

        // Envia os dados para o Firebase
        newSaleRef.setValueAsync(vendaData);
    }

    private Map<String, Object> convertItemVendaToMap(ItemVenda item) {
        Map<String, Object> itemMap = new HashMap<>();
        itemMap.put("produtoId", item.getProduto().getFirebaseId());
        itemMap.put("quantidade", item.getQuantidade());
        return itemMap;
    }

}