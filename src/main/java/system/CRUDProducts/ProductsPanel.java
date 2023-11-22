package system.CRUDProducts;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import entities.Produto;
import lombok.Getter;
import lombok.Setter;
import Manager.ProdutoManager;
import Manager.Sistema;

import javax.swing.JLabel;
import java.awt.Font;

@Getter
@Setter

public class ProductsPanel extends JPanel {
    private static final long serialVersionUID = 2448723614099966739L;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Sistema sistema;
    private int selectedRow;
    private JButton backButton;
    private JLabel lblProdutos;
    private ProdutoManager produtoManager;

    public ProductsPanel(CardLayout cardLayout, JPanel cardPanel, Sistema sistema, ProdutoManager produtoManager) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.sistema = sistema;
        this.produtoManager = produtoManager;
        setLayout(null);

        tableModel = new DefaultTableModel() {
            private static final long serialVersionUID = -3005244091815725029L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("SKU");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Categoria");
        tableModel.addColumn("Fornecedor");
        tableModel.addColumn("Descrição");
        tableModel.addColumn("Preço de Custo");
        tableModel.addColumn("Preço de Venda");
        tableModel.addColumn("Estoque Disponível");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 49, 930, 300);
        add(scrollPane);

        btnAdd = new JButton("Adicionar Produto");
        btnAdd.setBounds(214, 8, 150, 30);
        add(btnAdd);

        btnEdit = new JButton("Editar Produto");
        btnEdit.setBounds(20, 360, 150, 30);
        add(btnEdit);

        btnDelete = new JButton("Deletar Produto");
        btnDelete.setBounds(180, 360, 150, 30);
        add(btnDelete);

        backButton = new JButton("Voltar");
        backButton.setBounds(870, 8, 80, 30);
        add(backButton);

        lblProdutos = new JLabel("PRODUTOS");
        lblProdutos.setFont(new Font("Arial", Font.BOLD, 30));
        lblProdutos.setBounds(20, 8, 184, 30);
        add(lblProdutos);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddProductPanel addProductPanel = new AddProductPanel(cardLayout, cardPanel, tableModel, sistema);
                cardPanel.add(addProductPanel, "AddProductPanel");
                cardLayout.show(cardPanel, "AddProductPanel");
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
<<<<<<< HEAD
                if (selectedRow >= 0) { 
                    Object sku = tableModel.getValueAt(selectedRow, 0);
                    Object nome = tableModel.getValueAt(selectedRow, 1);
                    Object categoria = tableModel.getValueAt(selectedRow, 2);
                    Object fornecedor = tableModel.getValueAt(selectedRow, 3);
                    Object descricao = tableModel.getValueAt(selectedRow, 4);
                    Object precoVenda = tableModel.getValueAt(selectedRow, 6);
                    
                    EditProductPanel editProductPanel = new EditProductPanel(cardLayout, cardPanel);
                    editProductPanel.setSelectedRow(selectedRow);
                    editProductPanel.setTableModel(tableModel); 
                    editProductPanel.getTxtSKU().setText(sku.toString());
                    editProductPanel.getTxtNome().setText(nome.toString());
                    editProductPanel.getTxtSKU().setText(sku.toString());
                    editProductPanel.getTxtNome().setText(nome.toString());
                    editProductPanel.getCbCategoria().setSelectedItem(categoria.toString());
                    editProductPanel.getCbFornecedor().setSelectedItem(fornecedor.toString());
                    editProductPanel.getTxtDescricao().setText(descricao.toString());
                    editProductPanel.getTxtPrecoVenda().setText(precoVenda.toString());
=======
                if (selectedRow >= 0) {
                    Object[] rowData = new Object[tableModel.getColumnCount()];
                    for (int i = 0; i < tableModel.getColumnCount(); i++) {
                        rowData[i] = tableModel.getValueAt(selectedRow, i);
                    }
>>>>>>> e80df3cd23d116cfb38213981269f0699c1e44a5

                    EditProductPanel editProductPanel = new EditProductPanel(cardLayout, cardPanel, produtoManager,
                            rowData);
                    cardPanel.add(editProductPanel, "EditProductPanel");
                    cardLayout.show(cardPanel, "EditProductPanel");
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um produto para editar.",
                            "Nenhum produto selecionado", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteProductPanel deleteProductPanel = new DeleteProductPanel(tableModel, produtoManager);
                cardPanel.add(deleteProductPanel, "DeleteProductPanel");
                cardLayout.show(cardPanel, "DeleteProductPanel");
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "MainPanel");
            }
        });
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (aFlag) {
            atualizarTabelaProdutos();
        }
    }

    public void atualizarTabelaProdutos() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("caminho/para/produtos");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SwingUtilities.invokeLater(() -> {
                    tableModel.setRowCount(0);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Produto produto = snapshot.getValue(Produto.class);
                        tableModel.addRow(new Object[] {
                                produto.getSku(),
                                produto.getNome(),
                                produto.getCategoria() != null ? produto.getCategoria().getNome() : "",
                                produto.getFornecedor() != null ? produto.getFornecedor().getNome() : "",
                                produto.getDescricao(),
                                formatarMoeda(produto.getPrecoCusto()),
                                formatarMoeda(produto.getPrecoVendaComDesconto()),
                                produto.getEstoqueDisponivel()
                        });
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Falha ao ler dados: " + databaseError.getCode());
            }
        });
    }

    private String formatarMoeda(double valor) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        return format.format(valor);
    }
}
