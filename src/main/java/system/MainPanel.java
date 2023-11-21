	package system;
	
	import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import entities.Produto;
import entities.Sistema;
import entities.Venda;
import lombok.Getter;
import lombok.Setter;
	
	@Getter
	@Setter
	public class MainPanel extends JPanel {
	    private static final long serialVersionUID = -3228624175687997196L;
	    private JTable tableVendas; 
	    private DefaultTableModel tableModelVendas;
	    private JTable table; 
	    private DefaultTableModel tableModel;
	    private Sistema sistema;
	    
	    public MainPanel(Sistema sistema) {
	    	 Timer timer = new Timer(5000, e -> updateLowStockTable(sistema));
	    	    timer.start();
	    	
	    	 Timer timer1 = new Timer(1000, e ->atualizarTabelaVendas());
	    	 timer1.start(); 
	    	
	        this.sistema = sistema;
	        setLayout(null);
	        setBackground(new Color(245, 245, 245));
	        setBounds(0, 0, 800, 600);
	
	        JLabel titleLabel = new JLabel("Painel Principal", SwingConstants.CENTER);
	        titleLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 36));
	        titleLabel.setForeground(new Color(50, 50, 50));
	        titleLabel.setBounds(100, 20, 600, 50); 
	        add(titleLabel);
	
	        JTextArea welcomeMessage = new JTextArea(
	            "Bem-vindo à Eletrônica Pikachu!\n\n" +
	            "Aqui você pode gerenciar suas vendas, produtos, clientes e muito mais.\n" +
	            "Selecione uma opção no painel lateral para começar."
	        );
	        welcomeMessage.setEditable(false);
	        welcomeMessage.setFont(new Font("Yu Gothic UI", Font.PLAIN, 20));
	        welcomeMessage.setOpaque(false);
	        welcomeMessage.setFocusable(false);
	        welcomeMessage.setBounds(48, 94, 700, 143);
	        add(welcomeMessage);
	        //
	        JPanel lowStockPanel = new JPanel(null);
	        lowStockPanel.setBounds(10, 248, 384, 200);
	        lowStockPanel.setBorder(BorderFactory.createTitledBorder("Produtos com Estoque Baixo"));
	
	        String[] columnNames = {"SKU", "Nome do Produto", "Quantidade"};
	        tableModel = new DefaultTableModel(columnNames, 0);
	        table = new JTable(tableModel);
	        updateLowStockTable(sistema);
	        table.getColumnModel().getColumn(0).setPreferredWidth(10);
	        
	        JScrollPane scrollPane = new JScrollPane(table);
	        scrollPane.setBounds(10, 20, 364, 170); 
	        lowStockPanel.add(scrollPane);
	        add(lowStockPanel);
	        //
	        //
	        JPanel lastSoldPanel = new JPanel(null);
	        lastSoldPanel.setBounds(404, 248, 300, 140); 
	        lastSoldPanel.setBorder(BorderFactory.createTitledBorder("Últimos Vendidos"));

	        String[] colunasVendas = {"ID", "Cliente", "Data"};
	        tableModelVendas = new DefaultTableModel(colunasVendas, 0);
	        JTable tableVendas = new JTable(tableModelVendas);

	        tableVendas.getColumnModel().getColumn(1).setPreferredWidth(100);

	        JScrollPane scrollPaneVendas = new JScrollPane(tableVendas);
	        scrollPaneVendas.setBounds(10, 20, 280, 105);
	        lastSoldPanel.add(scrollPaneVendas);

	        add(lastSoldPanel);
	        //
	        JPanel customPanel = new JPanel(null);
	        customPanel.setBounds(203, 489, 160, 100);
	        customPanel.setBorder(BorderFactory.createTitledBorder("Seu Painel"));
	        add(customPanel);
	        
	        JPanel topSellingPanel = new JPanel(null);
	        topSellingPanel.setBounds(417, 489, 160, 100);
	        topSellingPanel.setBorder(BorderFactory.createTitledBorder("Mais Vendidos"));
	        add(topSellingPanel);    
	    }
	    
	    private void updateLowStockTable(Sistema sistema) {
	        tableModel.setRowCount(0);
	        for (Produto produto : sistema.getProdutos()) {
	            int estoque = produto.getEstoqueDisponivel();  
	            if (estoque < 5) {
	                tableModel.addRow(new Object[]{
	                    produto.getSku(),  
	                    produto.getNome(), 
	                    estoque
	                });
	            }
	        }
	    }
	    
	    public void atualizarTabelaVendas() {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        tableModelVendas.setRowCount(0); 
	        List<Venda> listaVendas = sistema.getVendas();

	        int start = Math.max(0, listaVendas.size() - 5);
	        for (int i = listaVendas.size() - 1; i >= start; i--) {
	            Venda venda = listaVendas.get(i);
	            Object[] row = new Object[3];
	            row[0] = venda.getId();
	            row[1] = venda.getCliente().getNome(); 
	            row[2] = sdf.format(venda.getData()); 
	            tableModelVendas.insertRow(0, row);
	        }
	    }
	}
