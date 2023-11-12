package system.Reports;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import entities.Sistema;

public class ReportsPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2923599729964175275L;
	private JComboBox<String> reportTypeComboBox;
    private JPanel reportDisplayPanel;
    private Sistema sistema;

    public ReportsPanel(Sistema sistema) {
    	this.sistema = sistema;
        setLayout(new BorderLayout());

        // Criação e configuração do combo box para seleção de tipo de relatório
        String[] reportTypes = {"Relatório de Vendas", "Relatório de Produtos", "Relatório de Estoque", "Relatório de Clientes"};
        reportTypeComboBox = new JComboBox<>(reportTypes);
        reportTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para exibir o relatório selecionado
                displaySelectedReport(reportTypeComboBox.getSelectedItem().toString());
            }
        });

        // Configurando o painel de exibição de relatório
        reportDisplayPanel = new JPanel();
        reportDisplayPanel.setLayout(new BorderLayout()); // Pode mudar dependendo de como você quer exibir o relatório

        // Adicionando componentes ao painel
        add(reportTypeComboBox, BorderLayout.NORTH);
        add(reportDisplayPanel, BorderLayout.CENTER);
    }

    private void displaySelectedReport(String reportType) {
        // Limpa o painel anterior
        reportDisplayPanel.removeAll();

        // Lógica para exibir o relatório baseado no tipo selecionado
        switch (reportType) {
        	case "Relatório de Vendas":
        		SalesReportPanel salesReportPanel = new SalesReportPanel(sistema);
            	reportDisplayPanel.add(salesReportPanel, BorderLayout.CENTER);
            	break;
            case "Relatório de Produtos":
                // Adicione a lógica para o relatório de produtos aqui
                break;
            case "Relatório de Estoque":
                // Adicione a lógica para o relatório de estoque aqui
                break;
            case "Relatório de Clientes":
                // Adicione a lógica para o relatório de clientes aqui
                break;
            default:
                // Se necessário, trate o caso padrão
                break;
        }

        // Atualiza o painel para mostrar as novas alterações
        reportDisplayPanel.revalidate();
        reportDisplayPanel.repaint();
    }

}
