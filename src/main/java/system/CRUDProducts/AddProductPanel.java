package system.CRUDProducts;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Manager.ProdutoManager;
import Manager.Sistema;
import entities.Categoria;
import entities.Fornecedor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AddProductPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 2862788884325913939L;
    private Sistema sistema;
    private JTextField fieldSKU, fieldNome, fieldDescricao, fieldPrecoCusto, fieldPrecoVenda, fieldEstoqueDisponivel;
    private JComboBox<String> comboCategoria, comboFornecedor;
    private JButton btnConfirmar;
    private DefaultTableModel tableModel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton btnVoltar;
    private ProductsPanel productsPanel;
    private ProdutoManager produtoManager;
    String categoria = "valor_da_categoria";
    String fornecedor = "valor_do_fornecedor";

    public AddProductPanel(CardLayout cardLayout, JPanel cardPanel, DefaultTableModel tableModel, Sistema sistema,
            ProdutoManager produtoManager) {
        this.sistema = sistema;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.tableModel = tableModel;
        this.produtoManager = produtoManager;
        setLayout(null);

        JLabel labelSKU = new JLabel("SKU:");
        labelSKU.setBounds(10, 10, 80, 25);
        add(labelSKU);

        fieldSKU = new JTextField();
        fieldSKU.setBounds(100, 10, 165, 25);
        add(fieldSKU);

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(10, 40, 80, 25);
        add(labelNome);

        fieldNome = new JTextField();
        fieldNome.setBounds(100, 40, 165, 25);
        add(fieldNome);

        JLabel labelDescricao = new JLabel("Descrição:");
        labelDescricao.setBounds(10, 70, 80, 25);
        add(labelDescricao);

        fieldDescricao = new JTextField();
        fieldDescricao.setBounds(100, 70, 165, 25);
        add(fieldDescricao);

        JLabel labelPrecoCusto = new JLabel("Preço de Custo:");
        labelPrecoCusto.setBounds(10, 100, 120, 25);
        add(labelPrecoCusto);

        fieldPrecoCusto = new JTextField();
        fieldPrecoCusto.setBounds(130, 100, 135, 25);
        add(fieldPrecoCusto);

        JLabel labelPrecoVenda = new JLabel("Preço de Venda:");
        labelPrecoVenda.setBounds(10, 130, 120, 25);
        add(labelPrecoVenda);

        fieldPrecoVenda = new JTextField();
        fieldPrecoVenda.setBounds(130, 130, 135, 25);
        add(fieldPrecoVenda);

        JLabel labelEstoqueDisponivel = new JLabel("Estoque:");
        labelEstoqueDisponivel.setBounds(10, 160, 120, 25);
        add(labelEstoqueDisponivel);

        fieldEstoqueDisponivel = new JTextField();
        fieldEstoqueDisponivel.setBounds(130, 160, 135, 25);
        add(fieldEstoqueDisponivel);

        JLabel labelCategoria = new JLabel("Categoria:");
        labelCategoria.setBounds(10, 190, 80, 25);
        add(labelCategoria);

        comboCategoria = new JComboBox<>();
        for (Categoria categoria : sistema.getCategorias()) {
            comboCategoria.addItem(categoria.getNome());
        }
        comboCategoria.setBounds(100, 190, 165, 25);
        add(comboCategoria);

        JLabel labelFornecedor = new JLabel("Fornecedor:");
        labelFornecedor.setBounds(10, 220, 80, 25);
        add(labelFornecedor);

        comboFornecedor = new JComboBox<>();
        for (Fornecedor fornecedor : sistema.getFornecedores()) {
            comboFornecedor.addItem(fornecedor.getNome());
        }
        comboFornecedor.setBounds(100, 220, 165, 25);
        add(comboFornecedor);

        // Buscar Categorias no Firebase
        DatabaseReference categoriasRef = FirebaseDatabase.getInstance().getReference("categorias");
        categoriasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot categoriaSnapshot : dataSnapshot.getChildren()) {
                    Categoria categoria = categoriaSnapshot.getValue(Categoria.class);
                    if (categoria != null) {
                        comboCategoria.addItem(categoria.getNome());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Tratar erros, se necessário
            }
        });

        // Buscar Fornecedores no Firebase
        DatabaseReference fornecedoresRef = FirebaseDatabase.getInstance().getReference("fornecedores");
        fornecedoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot fornecedorSnapshot : dataSnapshot.getChildren()) {
                    Fornecedor fornecedor = fornecedorSnapshot.getValue(Fornecedor.class);
                    if (fornecedor != null) {
                        comboFornecedor.addItem(fornecedor.getNome());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Tratar erros, se necessário
            }
        });

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBounds(10, 270, 255, 25);
        btnConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addProduto();
            }
        });

        add(btnConfirmar);

        btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(351, 271, 89, 23);
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "ProductsPanel");
            }
        });
        add(btnVoltar);
    }

    private void addProduto() {
        try {
            // Obtain values from text fields
            Integer sku = Integer.valueOf(fieldSKU.getText());
            String nome = fieldNome.getText();
            String descricao = fieldDescricao.getText();
            double precoCusto = Double.valueOf(fieldPrecoCusto.getText());
            double precoVenda = Double.valueOf(fieldPrecoVenda.getText());
            int estoqueDisponivel = Integer.valueOf(fieldEstoqueDisponivel.getText());
            String nomeCategoria = (String) comboCategoria.getSelectedItem();
            String nomeFornecedor = (String) comboFornecedor.getSelectedItem();

            // Check if all required fields are filled
            if (!nome.isEmpty() && !descricao.isEmpty() && !nomeCategoria.isEmpty() && !nomeFornecedor.isEmpty()) {

                this.produtoManager.adicionarProduto(sku, nome, descricao, precoCusto, precoVenda, estoqueDisponivel,
                        categoria, fornecedor, () -> {
                        });

                // Reference to the 'produtos' collection in Firebase
                DatabaseReference produtosRef = FirebaseDatabase.getInstance().getReference("produtos");

                // Generate a unique ID in Firebase
                String firebaseId = produtosRef.push().getKey();

                // Save the product in Firebase using a custom structure
                Map<String, Object> produtoData = new HashMap<>();
                produtoData.put("categoria", nomeCategoria);
                produtoData.put("descricao", descricao);
                produtoData.put("estoqueDisponivel", estoqueDisponivel);
                produtoData.put("fornecedor", nomeFornecedor);
                produtoData.put("nome", nome);
                produtoData.put("precoCusto", precoCusto);
                produtoData.put("precoVenda", precoVenda);
                produtoData.put("sku", sku);

                produtosRef.child(firebaseId).setValueAsync(produtoData);

                // Add a new row to the table with the product data
                produtoManager.mapProdutoSku(sku, firebaseId);
                Object[] rowData = { sku, nome, nomeCategoria, nomeFornecedor, descricao, precoCusto, precoVenda,
                        estoqueDisponivel };
                tableModel.addRow(rowData);

                // Clear text fields for new entries
                fieldSKU.setText("");
                fieldNome.setText("");
                fieldDescricao.setText("");
                fieldPrecoCusto.setText("");
                fieldPrecoVenda.setText("");
                fieldEstoqueDisponivel.setText("");

                // Display a success message
                JOptionPane.showMessageDialog(this, "Produto adicionado com sucesso", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Display an error message if any field is empty
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            // Display an error message if an exception occurs
            JOptionPane.showMessageDialog(this, "Erro ao adicionar o produto: " + ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}