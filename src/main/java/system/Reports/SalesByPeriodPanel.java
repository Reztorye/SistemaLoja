package system.Reports;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.toedter.calendar.JDateChooser;

import entities.Sistema;

public class SalesByPeriodPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5591050954014030538L;
	private Sistema sistema;
    private JDateChooser dateChooserInicio, dateChooserFim;
    private JButton btnFilter;
    private JLabel lblTotalSales;

    public SalesByPeriodPanel(Sistema sistema) {
        this.sistema = sistema;
        setLayout(null);
        initializeUI();
    }

    private void initializeUI() {
        // Data de início
        dateChooserInicio = new JDateChooser();
        dateChooserInicio.setBounds(10, 20, 120, 25);
        add(dateChooserInicio);

        // Data de término
        dateChooserFim = new JDateChooser();
        dateChooserFim.setBounds(140, 20, 120, 25);
        add(dateChooserFim);

        // Botão para filtrar
        btnFilter = new JButton("Filtrar");
        btnFilter.setBounds(270, 20, 80, 25);
        btnFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterSales();
            }
        });
        add(btnFilter);

        // Label para exibir o total de vendas
        lblTotalSales = new JLabel("Total de Vendas: ");
        lblTotalSales.setBounds(10, 60, 300, 25);
        add(lblTotalSales);
    }

    private void filterSales() {
        Date startDate = dateChooserInicio.getDate();
        Date endDate = dateChooserFim.getDate();

        if (startDate != null && endDate != null) {
            double totalSales = sistema.totalDeVendasPorPeriodo(startDate, endDate);
            lblTotalSales.setText("Total de Vendas: " + totalSales);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione as datas de início e término.");
        }
    }
}
