package pages.Pages.Fuctions;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
public class ProductsPanel extends JPanel {

    public ProductsPanel() {
        setLayout(null);  // Definir layout nulo para posicionamento absoluto

        // Seu código para criar a tabela começa aqui
        DefaultTableModel model = new DefaultTableModel(new String[]{"SKU", "Nome", "Categoria", "Fornecedor", "Descrição", "Preço de Custo", "Preço de Venda", "Estoque Disponível"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Isso faz com que nenhuma célula seja editável
            }
        };
        JTable table = new JTable(model);
        table.setRowSelectionAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 800, 299);
        add(scrollPane);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);  
        columnModel.getColumn(1).setPreferredWidth(110);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(110);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(120);
        columnModel.getColumn(6).setPreferredWidth(120);
        columnModel.getColumn(7).setPreferredWidth(140);
        model.addRow(new Object[]{1234, "Mi 8 lite", "Celular", "Xiaomi", "descricao do produto 1", 999.90, 1499.99, 50});
        model.addRow(new Object[]{1235, "Redmi note 12", "Tablet", "Xiaomi", "descricao do produto 2", 1999.90, 2499.95, 20});
        // Seu código para criar a tabela termina aqui
    }
}

