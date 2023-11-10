package system.Sales;
import java.awt.event.ActionEvent ;
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

import entities.Categoria;
import entities.Cliente;
import entities.Fornecedor;
import entities.ItemVenda;
import entities.Produto;
import entities.Sistema;
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
 // Exemplo de inicialização de Categoria
    Categoria categoriaEletronicos = new Categoria("Eletrônicos");

    // Exemplo de inicialização de Fornecedor
    Fornecedor fornecedorTech = new Fornecedor("Tech Supplier");
    

    public SalesPanel(Sistema sistema) { // Modifique o construtor para receber o Sistema
        this.sistema = sistema;			
        setLayout(null);
        // Configurar componentes e layout...	
        
        JLabel lblSKU = new JLabel("SKU:");
        lblSKU.setBounds(10, 10, 80, 25);
        add(lblSKU);

        txtSKU = new JTextField();
        txtSKU.setBounds(100, 10, 165, 25);
        add(txtSKU);

        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setBounds(10, 45, 80, 25);
        add(lblQuantity);

        txtQuantity = new JTextField();
        txtQuantity.setBounds(100, 45, 165, 25);
        add(txtQuantity);

        btnAddToCart = new JButton("Add to Cart");
        btnAddToCart.setBounds(10, 80, 120, 25);
        add(btnAddToCart);

        btnFinalizeSale = new JButton("Finalize Sale");
        btnFinalizeSale.setBounds(140, 80, 120, 25);
        add(btnFinalizeSale);

        // Setting up the JTable
        String[] columnNames = {"SKU", "Product Name", "Price", "Quantity", "Total"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableCart = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(tableCart);
        scrollPane.setBounds(10, 115, 580, 300);
        add(scrollPane);
        
        // Action Listeners for buttons
        btnAddToCart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String skuStr = txtSKU.getText();
                int quantity;
                try {
                    quantity = Integer.parseInt(txtQuantity.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SalesPanel.this, "Please enter a valid quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int sku = Integer.parseInt(skuStr);
                    Produto produto = findProductBySKU(sku);
                    if (produto != null) {
                        double price = produto.getPrecoVenda();
                        double total = price * quantity;
                        tableModel.addRow(new Object[]{sku, produto.getNome(), price, quantity, total});
                        txtSKU.setText("");
                        txtQuantity.setText("");
                    } else {
                        JOptionPane.showMessageDialog(SalesPanel.this, "Produto não encontrado com SKU: " + sku, "Erro do Produto", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SalesPanel.this, "Please enter a valid SKU.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

	        btnFinalizeSale.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                Cliente cliente = obterClientePorID();
	                if (cliente == null) {
	                    JOptionPane.showMessageDialog(SalesPanel.this, "Nenhum cliente selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	
	                List<ItemVenda> itensVenda = new ArrayList<>();
	                // Iterar sobre as linhas da tabela para coletar os produtos e quantidades
	                for (int i = 0; i < tableModel.	getRowCount(); i++) {
	                    Integer sku = (Integer) tableModel.getValueAt(i, 0); // Coluna do SKU
	                    int quantidade = (Integer) tableModel.getValueAt(i, 3); // Coluna da Quantidade
	                    Produto produto = sistema.buscarProdutoPorSku(sku);
	                    if (produto != null) {
	                        if (quantidade > produto.getEstoqueDisponivel()) {
	                            JOptionPane.showMessageDialog(SalesPanel.this, "Quantidade solicitada para o produto '" + produto.getNome() + "' excede o estoque disponível.", "Erro de Estoque", JOptionPane.ERROR_MESSAGE);
	                            return; // Interrompe o processo de finalização da venda
	                        }
	                        itensVenda.add(new ItemVenda(produto, quantidade));
	                    }
	                }
	
	                sistema.realizarVenda(cliente, itensVenda);
	
	                // Limpar o carrinho e atualizar a interface conforme necessário
	                tableModel.setRowCount(0);
	                JOptionPane.showMessageDialog(SalesPanel.this, "Venda finalizada com sucesso!", "Venda Concluída", JOptionPane.INFORMATION_MESSAGE);
	            }
	        });


    }
    
    private Cliente obterClientePorID() {
        String clienteIdStr = JOptionPane.showInputDialog(this, "Digite o ID do cliente:");
        if (clienteIdStr != null && !clienteIdStr.trim().isEmpty()) {
            try {
                int clienteId = Integer.parseInt(clienteIdStr.trim());
                return sistema.buscarClientePorId(clienteId);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
    
    private Produto findProductBySKU(Integer sku) {
        for (Produto produto : sistema.getProdutos()) {
            if (produto.getSku().equals(sku)) {
                return produto;
            }
        }
        return null;
    }


    
    protected void finalizeSale() {
        // Logic to finalize sale
    }
}