package system.Reports;

import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import entities.ItemVenda;
import entities.Produto;
import entities.Sistema;

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

        String[] columnNames = {"Nome do Produto", "Quantidade Vendida", "Valor Total de Vendas"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 780, 300);
        add(scrollPane);
        
        dateChooserInicio = new JDateChooser();
        dateChooserInicio.setBounds(10, 320, 120, 25);
        add(dateChooserInicio);

        dateChooserFim = new JDateChooser();
        dateChooserFim.setBounds(140, 320, 120, 25);
        add(dateChooserFim);

        btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBounds(270, 320, 80, 25);
        btnFiltrar.addActionListener(e -> loadData());
        add(btnFiltrar);
        
        btnOrdenarPorValor = new JButton("Ordenar por Valor");
        btnOrdenarPorValor.setBounds(360, 320, 150, 25);
        btnOrdenarPorValor.addActionListener(e -> ordenarPorValor());
        add(btnOrdenarPorValor);

        // Botão para ordenar por quantidade vendida
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

        // Filtro de vendas por data
        Map<Produto, Integer> totalVendasPorProduto = sistema.getVendas().stream()
            .filter(venda -> 
                (inicio == null || !venda.getData().before(inicio)) && 
                (fim == null || !venda.getData().after(fim)))
            .flatMap(venda -> venda.getItensVenda().stream())
            .collect(Collectors.toMap(
                ItemVenda::getProduto,
                ItemVenda::getQuantidade,
                Integer::sum));

        // Ordena e adiciona os dados ao modelo da tabela
        totalVendasPorProduto.entrySet().stream()
            .sorted((entry1, entry2) -> 
                Double.compare(entry2.getValue() * entry2.getKey().getPrecoVenda(), 
                               entry1.getValue() * entry1.getKey().getPrecoVenda()))
            .forEach(entry -> {
                Produto produto = entry.getKey();
                Integer quantidade = entry.getValue();
                double valorTotalVendas = quantidade * produto.getPrecoVenda();
                tableModel.addRow(new Object[]{produto.getNome(), quantidade, valorTotalVendas});
            });
    }
    
    private void ordenarPorValor() {
        ordenarEExibir((entry1, entry2) -> 
            Double.compare(entry2.getValue() * entry2.getKey().getPrecoVenda(),
                           entry1.getValue() * entry1.getKey().getPrecoVenda()));
    }


    private void ordenarEExibir(Comparator<Map.Entry<Produto, Integer>> comparator) {
        tableModel.setRowCount(0);
        
        Date inicio = dateChooserInicio.getDate();
        Date fim = dateChooserFim.getDate();
        
        Map<Produto, Integer> totalVendasPorProduto = sistema.getVendas().stream()
                .filter(venda -> 
                    (inicio == null || !venda.getData().before(inicio)) && 
                    (fim == null || !venda.getData().after(fim)))
                .flatMap(venda -> venda.getItensVenda().stream())
                .collect(Collectors.toMap(
                    ItemVenda::getProduto,
                    ItemVenda::getQuantidade,
                    Integer::sum));

        totalVendasPorProduto.entrySet().stream()
        .sorted(comparator) 
        .forEach(entry -> {
            Produto produto = entry.getKey();
            Integer quantidade = entry.getValue();
            double valorTotalVendas = quantidade * produto.getPrecoVenda();
            tableModel.addRow(new Object[]{produto.getNome(), quantidade, valorTotalVendas});
        });
    }
    
    private void ordenarPorQuantidade() {
        ordenarEExibir((entry1, entry2) -> 
            entry2.getValue().compareTo(entry1.getValue()));  // Inverte a comparação para ordenar do maior para o menor
    }
}
