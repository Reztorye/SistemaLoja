package system.Reports;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Manager.Sistema;
import entities.ItemVenda;
import entities.Produto;

public class TopSellingProductsPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 9167092819655546989L;
    private JTable table;
    private DefaultTableModel tableModel;
    private Sistema sistema;
    private JDateChooser dateChooserInicio, dateChooserFim;
    private JButton btnFiltrar;
    private JButton btnOrdenarPorValor;
    private JButton btnOrdenarPorQuantidade;

    public TopSellingProductsPanel(Sistema sistema) {
        this.sistema = sistema;
        setLayout(null);

        String[] columnNames = { "Nome do Produto", "Quantidade Vendida", "Valor Total de Vendas" };
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
        scrollPane.setBounds(0, 0, 944, 300);
        add(scrollPane);

        dateChooserInicio = new JDateChooser();
        dateChooserInicio.setBounds(0, 320, 120, 25);
        add(dateChooserInicio);

        dateChooserFim = new JDateChooser();
        dateChooserFim.setBounds(130, 320, 120, 25);
        add(dateChooserFim);

        btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBounds(270, 320, 80, 25);
        btnFiltrar.addActionListener(e -> loadData());
        add(btnFiltrar);

        btnOrdenarPorValor = new JButton("Ordenar por Valor");
        btnOrdenarPorValor.setBounds(360, 320, 150, 25);
        btnOrdenarPorValor.addActionListener(e -> ordenarPorValor());
        add(btnOrdenarPorValor);

        btnOrdenarPorQuantidade = new JButton("Ordenar por Quantidade");
        btnOrdenarPorQuantidade.setBounds(520, 320, 180, 25);
        btnOrdenarPorQuantidade.addActionListener(e -> ordenarPorQuantidade());
        add(btnOrdenarPorQuantidade);

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);

        Date inicio = dateChooserInicio.getDate();
        Date fim = dateChooserFim.getDate();

        // Usa o stream para processar as vendas e coletar a quantidade total de
        // produtos vendidos
        Map<Produto, Integer> totalVendasPorProduto = sistema.getVendas().stream()
                // Filtra as vendas que estão dentro do intervalo de datas especificado
                .filter(venda -> (inicio == null || !venda.getData().before(inicio)) &&
                        (fim == null || !venda.getData().after(fim)))
                // Converte cada venda em um stream de itens de venda
                .flatMap(venda -> venda.getItensVenda().stream())
                // Coleta os itens em um mapa, somando as quantidades para cada produto
                .collect(Collectors.toMap(
                        ItemVenda::getProduto, // Chave do mapa: Produto
                        ItemVenda::getQuantidade, // Valor do mapa: Quantidade vendida
                        Integer::sum)); // Método de combinação: Soma das quantidades

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")); // Formatador de moeda
                                                                                                // para o Brasil

        totalVendasPorProduto.entrySet().stream()
                .sorted((entry1, entry2) ->
                // Compara os valores totais de vendas de dois produtos
                Double.compare(entry2.getValue() * entry2.getKey().getPrecoVenda(),
                        entry1.getValue() * entry1.getKey().getPrecoVenda()))
                .forEach(entry -> {
                    Produto produto = entry.getKey();
                    Integer quantidade = entry.getValue();
                    double valorTotalVendas = quantidade * produto.getPrecoVenda();
                    // Formata o valor como moeda antes de adicionar à tabela
                    String valorFormatado = currencyFormat.format(valorTotalVendas);
                    tableModel.addRow(new Object[] { produto.getNome(), quantidade, valorFormatado });
                });

        // Chama a função de ordenar por quantidade ao carregar os dados
        ordenarPorQuantidade();
    }

    private void ordenarPorValor() {
        ordenarEExibir((entry1, entry2) -> Double.compare(entry2.getValue() * entry2.getKey().getPrecoVenda(),
                entry1.getValue() * entry1.getKey().getPrecoVenda()));
    }

    // Este método genérico ordena os dados com base em um comparador e exibe na
    // tabela
    private void ordenarEExibir(Comparator<Map.Entry<Produto, Integer>> comparator) {
        tableModel.setRowCount(0);

        Date inicio = dateChooserInicio.getDate();
        Date fim = dateChooserFim.getDate();

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")); // Formatador de moeda
                                                                                                // para o Brasil

        Map<Produto, Integer> totalVendasPorProduto = sistema.getVendas().stream()
                .filter(venda -> (inicio == null || !venda.getData().before(inicio)) &&
                        (fim == null || !venda.getData().after(fim)))
                .flatMap(venda -> venda.getItensVenda().stream())
                .collect(Collectors.toMap(
                        ItemVenda::getProduto,
                        ItemVenda::getQuantidade,
                        Integer::sum));

        // Ordena os produtos com base no comparador fornecido e exibe os resultados na
        // tabela
        totalVendasPorProduto.entrySet().stream()
                .sorted(comparator)
                .forEach(entry -> {
                    Produto produto = entry.getKey();
                    Integer quantidade = entry.getValue();
                    double valorTotalVendas = quantidade * produto.getPrecoVenda();
                    String valorFormatado = currencyFormat.format(valorTotalVendas); // Formata o valor para a moeda
                                                                                     // local
                    tableModel.addRow(new Object[] { produto.getNome(), quantidade, valorFormatado });
                });
    }

    private void ordenarPorQuantidade() {
        ordenarEExibir((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
    }
}
