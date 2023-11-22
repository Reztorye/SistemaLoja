package system.Reports;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.toedter.calendar.JDateChooser;

import Manager.Sistema;

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

        dateChooserInicio = new JDateChooser();
        dateChooserInicio.setBounds(125, 20, 120, 20);
        add(dateChooserInicio);

        dateChooserFim = new JDateChooser();
        dateChooserFim.setBounds(360, 20, 120, 20);
        add(dateChooserFim);

        btnFilter = new JButton("Filtrar");
        btnFilter.setBounds(495, 20, 80, 20);
        btnFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterSales();
            }
        });
        add(btnFilter);

        lblTotalSales = new JLabel("Total de Vendas: ");
        lblTotalSales.setBounds(0, 50, 300, 25);
        add(lblTotalSales);
        
        JLabel lblDateBegin = new JLabel("Data de início");
        lblDateBegin.setFont(new Font("Arial", Font.PLAIN, 20));
        lblDateBegin.setBounds(0, 20, 130, 20);
        add(lblDateBegin);
        
        JLabel lblDataDeFim = new JLabel("Data de fim");
        lblDataDeFim.setFont(new Font("Arial", Font.PLAIN, 20));
        lblDataDeFim.setBounds(255, 20, 109, 20);
        add(lblDataDeFim);
    }

    private void filterSales() {
        Date startDate = dateChooserInicio.getDate();
        Date endDate = dateChooserFim.getDate();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")); // Formatador de moeda
                                                                                                // para o Brasil

        if (startDate != null && endDate != null) {
            double totalSales = sistema.totalDeVendasPorPeriodo(startDate, endDate);
            lblTotalSales.setText("Total de Vendas: " + currencyFormat.format(totalSales)); // Formata o valor como
                                                                                            // moeda
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione as datas de início e término.");
        }
    }
}
