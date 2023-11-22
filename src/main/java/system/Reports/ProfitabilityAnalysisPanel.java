package system.Reports;
<<<<<<< HEAD
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
=======
>>>>>>> e80df3cd23d116cfb38213981269f0699c1e44a5

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Manager.ProdutoManager;
import entities.Produto;

public class ProfitabilityAnalysisPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 7636237925973202030L;
    private JTable table;
    private DefaultTableModel tableModel;
    private ProdutoManager produtoManager;

    public ProfitabilityAnalysisPanel(ProdutoManager produtoManager) {
        this.produtoManager = produtoManager;
        setLayout(null);

        String[] columnNames = { "Nome do Produto", "Preço de Custo", "Preço de Venda", "Margem de Lucro" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            /**
             * 
             */
            private static final long serialVersionUID = -9049266189071413309L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 10, 943, 300);
        add(scrollPane);

        loadProductData();
    }

    private void loadProductData() {
<<<<<<< HEAD
        List<Produto> produtos = sistema.getProdutos();

        // Ordenando a lista de produtos com base na margem de lucro percentual
        Collections.sort(produtos, new Comparator<Produto>() {
            @Override
            public int compare(Produto p1, Produto p2) {
                double margemP1 = (p1.getPrecoVenda() - p1.getPrecoCusto()) / p1.getPrecoCusto();
                double margemP2 = (p2.getPrecoVenda() - p2.getPrecoCusto()) / p2.getPrecoCusto();
                return Double.compare(margemP2, margemP1); // Ordena em ordem decrescente
            }
        });

        // Adicionando os produtos ordenados ao modelo de tabela
        for (Produto produto : produtos) {
=======
        for (Produto produto : produtoManager.getProdutos()) {
>>>>>>> e80df3cd23d116cfb38213981269f0699c1e44a5
            double custo = produto.getPrecoCusto();
            double venda = produto.getPrecoVenda();
            double margem = venda - custo;

            tableModel.addRow(new Object[] {
                    produto.getNome(),
                    String.format("R$ %.2f", custo),
                    String.format("R$ %.2f", venda),
                    String.format("R$ %.2f (%.2f%%)", margem, margem / custo * 100)
            });
        }
    }
}
