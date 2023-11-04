package pages.Pages.Fuctions;

import javax.swing.*;
import java.awt.*;

public class AddProductsPanel extends JPanel {

    public AddProductsPanel() {
        setLayout(null); // Usamos o layout nulo para um posicionamento mais preciso dos componentes

        // SKU
        JLabel labelSKU = new JLabel("SKU:");
        labelSKU.setBounds(10, 10, 130, 30);
        JTextField fieldSKU = new JTextField();
        fieldSKU.setBounds(150, 10, 200, 25);
        add(labelSKU);
        add(fieldSKU);

        // Nome
        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(10, 45, 130, 30);
        JTextField fieldNome = new JTextField();
        fieldNome.setBounds(150, 45, 200, 25);
        add(labelNome);
        add(fieldNome);

        // Categoria
        JLabel labelCategoria = new JLabel("Categoria:");
        labelCategoria.setBounds(10, 80, 130, 30);
        JComboBox<String> comboCategoria = new JComboBox<>(new String[]{"Smartphone", "Notebook", "Mouse"});
        comboCategoria.setBounds(150, 80, 200, 25);
        add(labelCategoria);
        add(comboCategoria);

        // Fornecedor
        JLabel labelFornecedor = new JLabel("Fornecedor:");
        labelFornecedor.setBounds(10, 115, 130, 30);
        JComboBox<String> comboFornecedor = new JComboBox<>(new String[]{"Xiaomi", "Samsung", "Apple"});
        comboFornecedor.setBounds(150, 115, 200, 25);
        add(labelFornecedor);
        add(comboFornecedor);

        // Descrição
        JLabel labelDescricao = new JLabel("Descrição:");
        labelDescricao.setBounds(10, 150, 130, 30);
        JTextField fieldDescricao = new JTextField();
        fieldDescricao.setBounds(150, 150, 200, 25);
        add(labelDescricao);
        add(fieldDescricao);

        // Preço de Custo
        JLabel labelPrecoCusto = new JLabel("Preço de Custo:");
        labelPrecoCusto.setBounds(10, 185, 130, 30);
        JTextField fieldPrecoCusto = new JTextField();
        fieldPrecoCusto.setBounds(150, 185, 200, 25);
        add(labelPrecoCusto);
        add(fieldPrecoCusto);

        // Preço de Venda
        JLabel labelPrecoVenda = new JLabel("Preço de Venda:");
        labelPrecoVenda.setBounds(10, 220, 130, 30);
        JTextField fieldPrecoVenda = new JTextField();
        fieldPrecoVenda.setBounds(150, 220, 200, 25);
        add(labelPrecoVenda);
        add(fieldPrecoVenda);

        // Estoque Disponível
        JLabel labelEstoque = new JLabel("Estoque Disponível:");
        labelEstoque.setBounds(10, 255, 130, 30);
        JTextField fieldEstoque = new JTextField();
        fieldEstoque.setBounds(150, 255, 200, 25);
        add(labelEstoque);
        add(fieldEstoque);

        // Botão Confirmar
        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBounds(150, 290, 200, 30);
        add(btnConfirmar);

        // Botão Voltar
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(10, 325, 200, 30);
        add(btnVoltar);
    }
}