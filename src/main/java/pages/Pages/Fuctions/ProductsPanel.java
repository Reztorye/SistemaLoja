package pages.Pages.Fuctions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductsPanel extends JPanel {

    private CardLayout cardLayout = new CardLayout();

    public ProductsPanel() {
        setLayout(cardLayout); // Utilize o cardLayout aqui

        // Painel de visualização dos produtos
        JPanel contentProductsPanel = new JPanel(null); // Usando layout nulo para controle absoluto de posicionamento
        contentProductsPanel.setPreferredSize(new Dimension(820, 360)); // Defina o tamanho preferido do painel

        DefaultTableModel model = new DefaultTableModel(new String[]{"SKU", "Nome", "Categoria", "Fornecedor", "Descrição", "Preço de Custo", "Preço de Venda", "Estoque Disponível"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Isso faz com que nenhuma célula seja editável
            }
        };

        // Adicione alguns dados de exemplo à tabela
        model.addRow(new Object[]{1234, "Mi 8 lite", "Celular", "Xiaomi", "descricao do produto 1", 999.90, 1499.99, 50});
        model.addRow(new Object[]{1235, "Redmi note 12", "Tablet", "Xiaomi", "descricao do produto 2", 1999.90, 2499.95, 20});

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 780, 290); // Ajustar de acordo com as necessidades

        contentProductsPanel.add(scrollPane);

        // Ajustando larguras de colunas específicas
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);  // Coluna SKU
    	columnModel.getColumn(1).setPreferredWidth(110); // Coluna Nome
    	columnModel.getColumn(2).setPreferredWidth(100); // Coluna Categoria
    	columnModel.getColumn(3).setPreferredWidth(110); // Coluna Fornecedor
    	columnModel.getColumn(4).setPreferredWidth(100); // Coluna Descrição
    	columnModel.getColumn(5).setPreferredWidth(120); // Coluna Preço de Custo
    	columnModel.getColumn(6).setPreferredWidth(120); // Coluna Preço de Venda
    	columnModel.getColumn(7).setPreferredWidth(140); // Coluna Estoque Disponível
        
        // Botão para adicionar produtos
        JButton btnAddProduct = new JButton("Adicionar Produto");
        btnAddProduct.setBounds(10, 320, 150, 30);
        btnAddProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(ProductsPanel.this, "ADD_PRODUCTS");
            }
        });
        contentProductsPanel.add(btnAddProduct);

        // Adicionar painel de visualização de produtos ao cardLayout
        add(contentProductsPanel, "PRODUCTS_PANEL");

        // Painel de adição de produtos
        AddProductsPanel addProductsPanel = new AddProductsPanel();

        // Adicionar painel de adição de produtos ao cardLayout
        add(addProductsPanel, "ADD_PRODUCTS");

        // Definir painel inicial a ser mostrado
        cardLayout.show(this, "PRODUCTS_PANEL");
    }
}
