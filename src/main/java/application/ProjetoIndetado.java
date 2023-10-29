package application;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import entities.Produto;
import entities.Sistema;

public class ProjetoIndetado {
    // Class member declarations
    @SuppressWarnings("serial")
    // Main function of the application
    public static void main(String[] args) {
        // Initialization code
        Sistema sistema = new Sistema();
        JFrame frmEletronicaPikachu = new JFrame("Eletronica Pikachu");
        frmEletronicaPikachu.setTitle("Eletronica Pikachu");
        frmEletronicaPikachu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmEletronicaPikachu.setSize(900, 436);
        @SuppressWarnings("unused")
        Color defaultColor = Color.DARK_GRAY;
        Color highlightColor = Color.GRAY;
        JPanel sidebar = new JPanel(null);
        sidebar.setPreferredSize(new Dimension(206, 400));
        sidebar.setBackground(Color.DARK_GRAY);
        JPanel content = new JPanel();
        CardLayout cardLayout = new CardLayout();
        content.setLayout(cardLayout);
        //==========================================================================================
        //tela inicial
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        JLabel notification = new JLabel("Notificação: Estoque baixo em alguns produtos.");
        notification.setBounds(41, 46, 300, 25);
        mainPanel.add(notification);
        JPanel panel = new JPanel(null);
        panel.setBorder(BorderFactory.createTitledBorder("Painel de Resumo"));
        panel.setBounds(24, 106, 350, 100);
        mainPanel.add(panel);
        JLabel vendas = new JLabel("Vendas Hoje: R$ 2.000");
        vendas.setBounds(10, 20, 200, 25);
        panel.add(vendas);
        JLabel itens = new JLabel("Itens em Falta: 5");
        itens.setBounds(10, 50, 150, 25);
        panel.add(itens);
        content.add(mainPanel, "MAIN_PANEL");
        //=========================================================================
        //painel de vendas
        JPanel vendasPanel = new JPanel();
        vendasPanel.setLayout(null);
        content.add(vendasPanel, "VENDAS_PANEL");
        //painel de entrada
        JPanel entradaPanel = new JPanel(null);
        entradaPanel.setPreferredSize(new Dimension(600, 400));
        content.add(entradaPanel, "ENTRADA_PANEL");
        //painel de promocao
        JPanel promocaoPanel = new JPanel(null);
        promocaoPanel.setPreferredSize(new Dimension(600, 400));
        content.add(promocaoPanel, "PROMOCAO_PANEL");
        //painel de produtos
        JPanel produtosPanel = new JPanel(null);
        produtosPanel.setLayout(null);
        produtosPanel.setPreferredSize(new Dimension(600, 400));
        content.add(produtosPanel, "PRODUTOS_PANEL");
        //painel de adicionar produtos
        JPanel adicionarProdutoPanel = new JPanel(null);
        adicionarProdutoPanel.setLayout(null);
        adicionarProdutoPanel.setPreferredSize(new Dimension(600, 400));
        content.add(adicionarProdutoPanel, "ADICIONAR_PRODUTO_PANEL");
        //painel de editar produtos
        JPanel editarProdutoPanel = new JPanel(null);
        editarProdutoPanel.setLayout(null);
        editarProdutoPanel.setPreferredSize(new Dimension(600, 400));
        content.add(editarProdutoPanel, "EDITAR_PRODUTO_PANEL");
        //painel de remover produtos
        JPanel removerProdutoPanel = new JPanel(null);
        removerProdutoPanel.setPreferredSize(new Dimension(600, 400));
        content.add(removerProdutoPanel, "REMOVER_PRODUTO_PANEL");
        //painel de clientes
        JPanel clientesPanel = new JPanel(null);
        clientesPanel.setPreferredSize(new Dimension(600, 400));
        content.add(clientesPanel, "CLIENTES_PANEL");
        //======================================================================
        //componentes produtos
        // Tabela de produtos
        DefaultTableModel model = new DefaultTableModel(new String[]{"SKU", "Nome", "Categoria", "Fornecedor", "Descrição", "Preço de Custo", "Preço de Venda", "Estoque Disponível"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Isso faz com que nenhuma célula seja editável
            }
        	};
        	JTable table = new JTable(model);
        	table.setRowSelectionAllowed(false);
        	JScrollPane scrollPane = new JScrollPane(table);
        	scrollPane.setBounds(10, 10, 580, 300);
        	produtosPanel.add(scrollPane);
        	TableColumnModel columnModel = table.getColumnModel();//pre-definir tamanho das colunas pois colunas com nome grande nao coubiam
        	columnModel.getColumn(0).setPreferredWidth(50);  // Coluna SKU
        	columnModel.getColumn(1).setPreferredWidth(60); // Coluna Nome
        	columnModel.getColumn(2).setPreferredWidth(100); // Coluna Categoria
        	columnModel.getColumn(3).setPreferredWidth(110); // Coluna Fornecedor
        	columnModel.getColumn(4).setPreferredWidth(100); // Coluna Descrição
        	columnModel.getColumn(5).setPreferredWidth(150); // Coluna Preço de Custo
        	columnModel.getColumn(6).setPreferredWidth(150); // Coluna Preço de Venda
        	columnModel.getColumn(7).setPreferredWidth(100); // Coluna Estoque Disponível
        	
        	sistema.adicionarProduto(1234, "Mi 8 lite", "descricao do produto 1", 999.90, 1499.99, 50, "Celular", "Xiaomi");
        	//---------------os 3 botoes
        	JButton btnAdicionar = new JButton("Adicionar");
        	btnAdicionar.setBounds(120, 320, 100, 25);
        	btnAdicionar.setFont(new Font("Arial", Font.BOLD, 14));
        	btnAdicionar.setBackground(new Color(64, 64, 64));
        	btnAdicionar.setForeground(Color.WHITE);
        	btnAdicionar.setFocusPainted(false);
        	btnAdicionar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        	btnAdicionar.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			cardLayout.show(content, "ADICIONAR_PRODUTO_PANEL");
        		}
        	});
        	produtosPanel.add(btnAdicionar);
        	JButton btnEditar = new JButton("Editar");
        	btnEditar.setBounds(250, 320, 100, 25);
        	btnEditar.setFont(new Font("Arial", Font.BOLD, 14));
        	btnEditar.setBackground(new Color(64, 64, 64));
        	btnEditar.setForeground(Color.WHITE);
        	btnEditar.setFocusPainted(false);
        	btnEditar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        	produtosPanel.add(btnEditar);
        	JButton btnRemover = new JButton("Remover");
        	btnRemover.setBounds(380, 320, 100, 25);
        	btnRemover.setFont(new Font("Arial", Font.BOLD, 14));
        	btnRemover.setBackground(new Color(64, 64, 64));
        	btnRemover.setForeground(Color.WHITE);
        	btnRemover.setFocusPainted(false);
        	btnRemover.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        	btnRemover.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			cardLayout.show(content, "REMOVER_PRODUTO_PANEL");
        		}
        	});
            produtosPanel.add(btnRemover);
            //----------------------------------------- componentes do adicionar
            JLabel lblSku = new JLabel("SKU:");
            lblSku.setBounds(100, 50, 100, 25);
            adicionarProdutoPanel.add(lblSku);
            JLabel lblNome = new JLabel("Nome:");
            lblNome.setBounds(100, 80, 100, 25);
            adicionarProdutoPanel.add(lblNome);
            JLabel lblCategoria = new JLabel("Categoria:");
            lblCategoria.setBounds(100, 110, 100, 25);
            adicionarProdutoPanel.add(lblCategoria);
            JLabel lblFornecedor = new JLabel("Fornecedor:");
            lblFornecedor.setBounds(100, 140, 100, 25);
            adicionarProdutoPanel.add(lblFornecedor);
            JTextField txtSku = new JTextField();
            txtSku.setBounds(230, 50, 200, 25);
            adicionarProdutoPanel.add(txtSku);
            JTextField txtNome = new JTextField();
            txtNome.setBounds(230, 80, 200, 25);
            adicionarProdutoPanel.add(txtNome);
            JLabel lblDescricao = new JLabel("Descrição:");
            lblDescricao.setBounds(100, 170, 100, 25);
            adicionarProdutoPanel.add(lblDescricao);
            JTextField txtDescricao = new JTextField();
            txtDescricao.setBounds(230, 170, 200, 25);
            adicionarProdutoPanel.add(txtDescricao);
            JLabel lblPrecoCusto = new JLabel("Preço de Custo:");
            lblPrecoCusto.setBounds(100, 200, 100, 25);
            adicionarProdutoPanel.add(lblPrecoCusto);
            JTextField txtPrecoCusto = new JTextField();
            txtPrecoCusto.setBounds(230, 200, 200, 25);
            adicionarProdutoPanel.add(txtPrecoCusto);
            JLabel lblPrecoVenda = new JLabel("Preço de Venda:");
            lblPrecoVenda.setBounds(100, 230, 100, 25);
            adicionarProdutoPanel.add(lblPrecoVenda);
            JTextField txtPrecoVenda = new JTextField();
            txtPrecoVenda.setBounds(230, 230, 200, 25);
            adicionarProdutoPanel.add(txtPrecoVenda);
            JLabel lblEstoqueDisponivel = new JLabel("Estoque Disponível:");
            lblEstoqueDisponivel.setBounds(100, 260, 120, 25);
            adicionarProdutoPanel.add(lblEstoqueDisponivel);
            JTextField txtEstoqueDisponivel = new JTextField();
            txtEstoqueDisponivel.setBounds(230, 260, 200, 25);
            adicionarProdutoPanel.add(txtEstoqueDisponivel);
            String[] categorias = {"Tablet", "Smartphone", "Notebook"};
            JComboBox<String> cbCategoria = new JComboBox<>(categorias);
            cbCategoria.setBounds(230, 110, 200, 25);
            cbCategoria.setBackground(new Color(64, 64, 64));
            cbCategoria.setForeground(Color.WHITE);
            cbCategoria.setFont(new Font("Arial", Font.PLAIN, 12));
            adicionarProdutoPanel.add(cbCategoria);
            String[] fornecedores = {"Xiaomi", "Samsung", "Apple"};
            JComboBox<String> cbFornecedor = new JComboBox<>(fornecedores);
            cbFornecedor.setBounds(230, 140, 200, 25);
            cbFornecedor.setBackground(new Color(64, 64, 64));
            cbFornecedor.setForeground(Color.WHITE);
            cbFornecedor.setFont(new Font("Arial", Font.PLAIN, 12));
            adicionarProdutoPanel.add(cbFornecedor);
            btnEditar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        Object sku = table.getValueAt(selectedRow, 0);
                        Object nome = table.getValueAt(selectedRow, 1);
                        Object descricao = table.getValueAt(selectedRow, 4);
                        Object precoCusto = table.getValueAt(selectedRow, 5);
                        Object precoVenda = table.getValueAt(selectedRow, 6);
                        Object estoqueDisponivel = table.getValueAt(selectedRow, 7);
                        Object categoria = table.getValueAt(selectedRow, 2);
                        Object fornecedor = table.getValueAt(selectedRow, 3);
                        txtSku.setText(sku.toString());
                        txtNome.setText(nome.toString());
                        txtDescricao.setText(descricao.toString());
                        txtPrecoCusto.setText(precoCusto.toString());
                        txtPrecoVenda.setText(precoVenda.toString());
                        txtEstoqueDisponivel.setText(estoqueDisponivel.toString());
                        cbCategoria.setSelectedItem(categoria);
                        cbFornecedor.setSelectedItem(fornecedor);
                        cardLayout.show(content, "EDITAR_PRODUTO_PANEL");
                        } else {
                            JOptionPane.showMessageDialog(null, "Por favor, selecione um produto para editar.");
                        }
                    }
                    });
            JButton btnAdicionarConfirmar = new JButton("Adicionar");
            btnAdicionarConfirmar.setBounds(230, 296, 200, 25);
            btnAdicionarConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
            btnAdicionarConfirmar.setBackground(new Color(64, 64, 64));
            btnAdicionarConfirmar.setForeground(Color.WHITE);
            btnAdicionarConfirmar.setFocusPainted(false);
            btnAdicionarConfirmar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            btnAdicionarConfirmar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //obtendo os valores dos campos de texto e JComboBoxes
                    Integer sku = Integer.valueOf(txtSku.getText());
                    String nome = txtNome.getText();
                    String descricao = txtDescricao.getText();
                    double precoCusto = Double.valueOf(txtPrecoCusto.getText());
                    double precoVenda = Double.valueOf(txtPrecoVenda.getText());
                    int estoqueDisponivel = Integer.valueOf(txtEstoqueDisponivel.getText());
                    String NomeCategoria = (String) cbCategoria.getSelectedItem();
                    String nomeFornecedor = (String) cbFornecedor.getSelectedItem();
                    //funcao rodrigo
                    Produto novoProduto = sistema.adicionarProduto(sku, nome, descricao, precoCusto, precoVenda, estoqueDisponivel, NomeCategoria, nomeFornecedor);
                    
                    model.addRow(new Object[]{novoProduto.getSku(), novoProduto.getNome(), novoProduto.getCategoria().getNome(), novoProduto.getFornecedor().getNome(), novoProduto.getDescricao(), novoProduto.getPrecoCusto(), novoProduto.getPrecoVenda(), novoProduto.getEstoqueDisponivel()});
                    
                    txtSku.setText("");
                    txtNome.setText("");
                    txtDescricao.setText("");
                    txtPrecoCusto.setText("");
                    txtPrecoVenda.setText("");
                    txtEstoqueDisponivel.setText("");
                }
                });
                adicionarProdutoPanel.add(btnAdicionarConfirmar);
            
            JButton btnAdicionarVoltar= new JButton("Voltar");
            btnAdicionarVoltar.setBounds(390, 364, 200, 25);
            btnAdicionarVoltar.setFont(new Font("Arial", Font.BOLD, 14));
            btnAdicionarVoltar.setBackground(new Color(64, 64, 64));  // Cor de fundo
            btnAdicionarVoltar.setForeground(Color.WHITE);  // Cor do texto
            btnAdicionarVoltar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            btnAdicionarVoltar.setFocusPainted(false);
            btnAdicionarVoltar.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btnAdicionarVoltar.setBackground(new Color(80, 80, 80));  // Cor de fundo quando o mouse está sobre o botão
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btnAdicionarVoltar.setBackground(new Color(64, 64, 64));  // Cor de fundo quando o mouse não está sobre o botão
                }
            });
            btnAdicionarVoltar.addActionListener(e -> {
                cardLayout.show(content, "PRODUTOS_PANEL");
            });
            adicionarProdutoPanel.add(btnAdicionarVoltar);
                        //--------------------- componentes do editar
                        JLabel lblSkuEditar = new JLabel("SKU:");
                        lblSkuEditar.setBounds(100, 50, 100, 25);
                        editarProdutoPanel.add(lblSkuEditar);
                        JTextField txtSkuEditar = new JTextField();
                        txtSkuEditar.setBounds(200, 50, 200, 25);
                        editarProdutoPanel.add(txtSkuEditar);
                        JLabel lblNomeEditar = new JLabel("Nome:");
                        lblNomeEditar.setBounds(100, 80, 100, 25);
                        editarProdutoPanel.add(lblNomeEditar);
                        JTextField txtNomeEditar = new JTextField();
                        txtNomeEditar.setBounds(200, 80, 200, 25);
                        editarProdutoPanel.add(txtNomeEditar);
                        JLabel lblDescricaoEditar = new JLabel("Descrição:");
                        lblDescricaoEditar.setBounds(100, 170, 100, 25);
                        editarProdutoPanel.add(lblDescricaoEditar);
                        JTextField txtDescricaoEditar = new JTextField();
                        txtDescricaoEditar.setBounds(200, 170, 200, 25);
                        editarProdutoPanel.add(txtDescricaoEditar);
                        JLabel lblPrecoVendaEditar = new JLabel("Preço de Venda:");
                        lblPrecoVendaEditar.setBounds(100, 206, 100, 25);
                        editarProdutoPanel.add(lblPrecoVendaEditar);
                        JTextField txtPrecoVendaEditar = new JTextField();
                        txtPrecoVendaEditar.setBounds(200, 206, 200, 25);
                        editarProdutoPanel.add(txtPrecoVendaEditar);
                        String[] categorias1 = {"Tablet", "Smartphone", "Notebook"};
                        JComboBox<String> cbCategoriaEditar = new JComboBox<>(categorias1);
                        cbCategoriaEditar.setBounds(200, 110, 200, 25);
                        cbCategoriaEditar.setBackground(new Color(64, 64, 64));
                        cbCategoriaEditar.setForeground(Color.WHITE);
                        cbCategoriaEditar.setFont(new Font("Arial", Font.PLAIN, 12));
                        editarProdutoPanel.add(cbCategoriaEditar);
                        String[] fornecedores1 = {"Xiaomi", "Samsung", "Apple"};
                        JComboBox<String> cbFornecedorEditar = new JComboBox<>(fornecedores1);
                        cbFornecedorEditar.setBounds(200, 140, 200, 25);
                        cbFornecedorEditar.setBackground(new Color(64, 64, 64));
                        cbFornecedorEditar.setForeground(Color.WHITE);
                        cbFornecedorEditar.setFont(new Font("Arial", Font.PLAIN, 12));
                        editarProdutoPanel.add(cbFornecedorEditar);
                        JButton btnSalvarEdicoes = new JButton("Salvar Edições");
                        btnSalvarEdicoes.setBounds(200, 242, 200, 25);
                        btnSalvarEdicoes.setFont(new Font("Arial", Font.BOLD, 14));
                        btnSalvarEdicoes.setBackground(new Color(64, 64, 64));
                        btnSalvarEdicoes.setForeground(Color.WHITE);
                        btnSalvarEdicoes.setFocusPainted(false);
                        btnSalvarEdicoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                        btnSalvarEdicoes.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                // Obtém os valores atualizados dos campos
                                int selectedRow = table.getSelectedRow();
                                if (selectedRow != -1) {  // Certificando que uma linha foi selecionada
                                    Integer skuAtualizado = Integer.valueOf(txtSkuEditar.getText());
                                    String nomeAtualizado = txtNomeEditar.getText();
                                    String descricaoAtualizada = txtDescricaoEditar.getText();
                                    double precoVendaAtualizado = Double.valueOf(txtPrecoVendaEditar.getText());
                                    String categoriaAtualizada = (String) cbCategoriaEditar.getSelectedItem();
                                    String fornecedorAtualizado = (String) cbFornecedorEditar.getSelectedItem();

                                    // Atualiza o produto
                                    // Supondo que você tenha um método para atualizar o produto
                                    sistema.atualizarProduto(skuAtualizado, nomeAtualizado, descricaoAtualizada, precoVendaAtualizado, categoriaAtualizada, fornecedorAtualizado);

                                    // Atualiza a linha correspondente na tabela
                                    model.setValueAt(skuAtualizado, selectedRow, 0);
                                    model.setValueAt(nomeAtualizado, selectedRow, 1);
                                    model.setValueAt(descricaoAtualizada, selectedRow, 4);
                                    model.setValueAt(precoVendaAtualizado, selectedRow, 6);
                                    model.setValueAt(categoriaAtualizada, selectedRow, 2);
                                    model.setValueAt(fornecedorAtualizado, selectedRow, 3);

                                    txtSkuEditar.setText("");
                                    txtNomeEditar.setText("");
                                    txtDescricaoEditar.setText("");
                                    txtPrecoVendaEditar.setText("");
                                } else {
                                    // Opicional: Exibir uma mensagem de erro se nenhuma linha foi selecionada
                                    JOptionPane.showMessageDialog(editarProdutoPanel, "Nenhum produto selecionado para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        });
                            editarProdutoPanel.add(btnSalvarEdicoes);
                            JLabel lblCategoriaEditar = new JLabel("Categoria:");
                            lblCategoriaEditar.setBounds(100, 110, 100, 25);
                            editarProdutoPanel.add(lblCategoriaEditar);
                            JLabel lblFornecedorEditar = new JLabel("Fornecedor:");
                            lblFornecedorEditar.setBounds(100, 140, 100, 25);
                            editarProdutoPanel.add(lblFornecedorEditar);
                            JButton btnEditarVoltar = new JButton("Voltar");
                            btnEditarVoltar.setBounds(468, 364, 200, 25);
                            btnEditarVoltar.addActionListener(e -> {
                                cardLayout.show(content, "PRODUTOS_PANEL");
                                });
                                editarProdutoPanel.add(btnEditarVoltar);
                                
                                btnEditar.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        int selectedRow = table.getSelectedRow();
                                        if (selectedRow != -1) {  // Verificando se uma linha foi selecionada
                                            // Obtém os valores diretamente do modelo da tabela
                                            Integer sku = (Integer) model.getValueAt(selectedRow, 0);
                                            String nome = (String) model.getValueAt(selectedRow, 1);
                                            String descricao = (String) model.getValueAt(selectedRow, 4);
                                            Double precoVenda = (Double) model.getValueAt(selectedRow, 6);
                                            String categoria = (String) model.getValueAt(selectedRow, 2);
                                            String fornecedor = (String) model.getValueAt(selectedRow, 3);

                                            // Preenche os campos de texto com as informações do produto
                                            txtSkuEditar.setText(String.valueOf(sku));
                                            txtNomeEditar.setText(nome);
                                            txtDescricaoEditar.setText(descricao);
                                            txtPrecoVendaEditar.setText(String.valueOf(precoVenda));
                                            cbCategoriaEditar.setSelectedItem(categoria);
                                            cbFornecedorEditar.setSelectedItem(fornecedor);

                                            // Muda para o painel de edição
                                            cardLayout.show(content, "EDITAR_PRODUTO_PANEL");
                                        }
                                    }
                                });
                                    //------------------------------ componentes do remover
                                    JTextField txtSkuRemover = new JTextField();
                                    txtSkuRemover.setBounds(140, 100, 200, 25);
                                    removerProdutoPanel.add(txtSkuRemover);
                                    JLabel lblSkuRemover = new JLabel("SKU:");
                                    lblSkuRemover.setBounds(100, 100, 40, 25);
                                    removerProdutoPanel.add(lblSkuRemover);
                                    JButton btnRemoverConfirmar = new JButton("Remover");
                                    btnRemoverConfirmar.setBounds(350, 100, 100, 25);
                                    btnRemoverConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
                                    btnRemoverConfirmar.setBackground(new Color(64, 64, 64));
                                    btnRemoverConfirmar.setForeground(Color.WHITE);
                                    btnRemoverConfirmar.setFocusPainted(false);
                                    btnRemoverConfirmar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                                    btnRemoverConfirmar.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            int sku = Integer.valueOf(txtSkuRemover.getText());
                                            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover o produto com SKU: " + sku + "?", "Confirmação de Remoção", JOptionPane.YES_NO_OPTION);
                                            if (resposta == JOptionPane.YES_OPTION) {
                                                sistema.removerProduto(sku);
                                                for (int i = model.getRowCount() - 1; i >= 0; i--) {
                                                    if (model.getValueAt(i, 0).equals(sku)) {
                                                        model.removeRow(i);
                                                        break;
                                                    }
                                                }
                                                txtSkuRemover.setText("");
                                            }
                                        }
                                        });
                                        removerProdutoPanel.add(btnRemoverConfirmar);
                                        JButton btnVoltarRemover = new JButton("Voltar");
                                        btnVoltarRemover.setBounds(350, 100, 100, 25);
                                        btnVoltarRemover.setFont(new Font("Arial", Font.BOLD, 14));
                                        btnVoltarRemover.setBackground(new Color(64, 64, 64));
                                        btnVoltarRemover.setForeground(Color.WHITE);
                                        btnVoltarRemover.setFocusPainted(false);
                                        btnVoltarRemover.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                                        btnVoltarRemover.setBounds(450, 300, 100, 30);
                                        btnVoltarRemover.addActionListener(e -> {
                                            cardLayout.show(content, "PRODUTOS_PANEL");
                                            });
                                            removerProdutoPanel.add(btnVoltarRemover);
                                            //============================================================
                                            //componentes clientes
                                            DefaultTableModel modelClientes = new DefaultTableModel(new String[]{"ID", "Nome", "Email", "Telefone"}, 0);
                                            JTable tableClientes = new JTable(modelClientes);
                                            JScrollPane scrollPaneClientes = new JScrollPane(tableClientes);  // Adicionando a tabela a um JScrollPane
                                            scrollPaneClientes.setBounds(10, 10, 580, 300);
                                            clientesPanel.add(scrollPaneClientes);  // Adicionando o JScrollPane ao painel
                                            // Botões para adicionar, editar e remover clientes
                                            JButton btnAdicionarCliente = new JButton("Adicionar");
                                            btnAdicionarCliente.setBounds(120, 320, 100, 25);
                                            btnAdicionarCliente.setFont(new Font("Arial", Font.BOLD, 14));
                                            btnAdicionarCliente.setBackground(new Color(64, 64, 64));
                                            btnAdicionarCliente.setForeground(Color.WHITE);
                                            btnAdicionarCliente.setFocusPainted(false);
                                            btnAdicionarCliente.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                                            btnAdicionarCliente.addActionListener(new ActionListener() {
                                                public void actionPerformed(ActionEvent e) {
                                                    cardLayout.show(content, "ADICIONAR_CLIENTE_PANEL");  // Supondo que este painel será criado
                                                }
                                                });
                                                clientesPanel.add(btnAdicionarCliente);
                                                JButton btnEditarCliente = new JButton("Editar");
                                                btnEditarCliente.setBounds(250, 320, 100, 25);
                                                btnEditarCliente.setFont(new Font("Arial", Font.BOLD, 14));
                                                btnEditarCliente.setBackground(new Color(64, 64, 64));
                                                btnEditarCliente.setForeground(Color.WHITE);
                                                btnEditarCliente.setFocusPainted(false);
                                                btnEditarCliente.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                                                btnEditarCliente.addActionListener(new ActionListener() {
                                                    public void actionPerformed(ActionEvent e) {
                                                        // Ação para editar cliente
                                                    }
                                                    });
                                                    clientesPanel.add(btnEditarCliente);
                                                    JButton btnRemoverCliente = new JButton("Remover");
                                                    btnRemoverCliente.setBounds(380, 320, 100, 25);
                                                    btnRemoverCliente.setFont(new Font("Arial", Font.BOLD, 14));
                                                    btnRemoverCliente.setBackground(new Color(64, 64, 64));
                                                    btnRemoverCliente.setForeground(Color.WHITE);
                                                    btnRemoverCliente.setFocusPainted(false);
                                                    btnRemoverCliente.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                                                    btnRemoverCliente.addActionListener(new ActionListener() {
                                                        public void actionPerformed(ActionEvent e) {
                                                            // Ação para remover cliente
                                                        }
                                                          });
                                                        clientesPanel.add(btnRemoverCliente);
                                                        //==============================================================
                                                        //labels do sidebar
                                                        JLabel label_telaPrincipal = new JLabel("Tela Principal", SwingConstants.CENTER);
                                                        label_telaPrincipal.setForeground(Color.WHITE);
                                                        label_telaPrincipal.setFont(new Font("Yu Gothic UI", Font.BOLD, 30));
                                                        label_telaPrincipal.setBounds(0, 0, 206, 57);
                                                        label_telaPrincipal.setOpaque(true);
                                                        label_telaPrincipal.setBackground(new Color(30, 30, 30));
                                                        label_telaPrincipal.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                                                        // Efeito de hover
                                                        label_telaPrincipal.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            @Override
                                                            public void mouseEntered(java.awt.event.MouseEvent evt) {
                                                                label_telaPrincipal.setBackground(highlightColor);
                                                                label_telaPrincipal.setOpaque(true);
                                                            }
                                                            @Override
                                                            public void mouseExited(java.awt.event.MouseEvent evt) {
                                                                label_telaPrincipal.setBackground(new Color(30, 30, 30));
                                                                label_telaPrincipal.setOpaque(true);
                                                            }
                                                            @Override
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                cardLayout.show(content, "MAIN_PANEL");
                                                            }
                                                            });
                                                            sidebar.add(label_telaPrincipal);
                                                            JLabel label_vendas = new JLabel("Vendas", SwingConstants.CENTER);
                                                            label_vendas.setForeground(Color.WHITE);
                                                            label_vendas.setFont(new Font("Yu Gothic UI", Font.BOLD, 30));
                                                            label_vendas.setBounds(0, 57, 206, 57);
                                                            label_vendas.setOpaque(true);
                                                            label_vendas.setBackground(new Color(30, 30, 30));
                                                            label_vendas.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                                                            // Efeito de hover
                                                            label_vendas.addMouseListener(new java.awt.event.MouseAdapter() {
                                                                @Override
                                                                public void mouseEntered(java.awt.event.MouseEvent evt) {
                                                                    label_vendas.setBackground(highlightColor);
                                                                    label_vendas.setOpaque(true);
                                                                }
                                                                @Override
                                                                public void mouseExited(java.awt.event.MouseEvent evt) {
                                                                    label_vendas.setBackground(new Color(30, 30, 30));
                                                                    label_vendas.setOpaque(true);
                                                                }
                                                                @Override
                                                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                    cardLayout.show(content, "VENDAS_PANEL");
                                                                }
                                                                });
                                                                sidebar.add(label_vendas);
                                                                JLabel label_entrada = new JLabel("Entrada", SwingConstants.CENTER);
                                                                label_entrada.setForeground(Color.WHITE);
                                                                label_entrada.setFont(new Font("Yu Gothic UI", Font.BOLD, 30));
                                                                label_entrada.setBounds(0, 114, 206, 57);
                                                                label_entrada.setOpaque(true);
                                                                label_entrada.setBackground(new Color(30, 30, 30));
                                                                label_entrada.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                                                                // Efeito de hover
                                                                label_entrada.addMouseListener(new java.awt.event.MouseAdapter() {
                                                                    @Override
                                                                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                                                                        label_entrada.setBackground(highlightColor);
                                                                        label_entrada.setOpaque(true);
                                                                    }
                                                                    @Override
                                                                    public void mouseExited(java.awt.event.MouseEvent evt) {
                                                                        label_entrada.setBackground(new Color(30, 30, 30));
                                                                        label_entrada.setOpaque(true);
                                                                    }
                                                                    @Override
                                                                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                        cardLayout.show(content, "ENTRADA_PANEL");
                                                                    }
                                                                    });
                                                                    sidebar.add(label_entrada);
                                                                    JLabel label_promocoes = new JLabel("Promoções ", SwingConstants.CENTER);
                                                                    label_promocoes.setForeground(Color.WHITE);
                                                                    label_promocoes.setFont(new Font("Yu Gothic UI", Font.BOLD, 30));
                                                                    label_promocoes.setBounds(0, 171, 206, 57);
                                                                    label_promocoes.setOpaque(true);
                                                                    label_promocoes.setBackground(new Color(30, 30, 30));
                                                                    label_promocoes.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                                                                    label_promocoes.addMouseListener(new java.awt.event.MouseAdapter() {
                                                                        @Override
                                                                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                                                                            label_promocoes.setBackground(highlightColor);
                                                                            label_promocoes.setOpaque(true);
                                                                        }
                                                                        @Override
                                                                        public void mouseExited(java.awt.event.MouseEvent evt) {
                                                                            label_promocoes.setBackground(new Color(30, 30, 30));
                                                                            label_promocoes.setOpaque(true);
                                                                        }
                                                                        @Override
                                                                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                            cardLayout.show(content, "PROMOCAO_PANEL");
                                                                        }
                                                                        });
                                                                        sidebar.add(label_promocoes);
                                                                        JLabel label_produtos = new JLabel("Produtos", SwingConstants.CENTER);
                                                                        label_produtos.setForeground(Color.WHITE);
                                                                        label_produtos.setFont(new Font("Yu Gothic UI", Font.BOLD, 30));
                                                                        label_produtos.setBounds(0, 228, 206, 57);
                                                                        label_produtos.setOpaque(true);
                                                                        label_produtos.setBackground(new Color(30, 30, 30));
                                                                        label_produtos.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                                                                        label_produtos.addMouseListener(new java.awt.event.MouseAdapter() {
                                                                            @Override
                                                                            public void mouseEntered(java.awt.event.MouseEvent evt) {
                                                                                label_produtos.setBackground(highlightColor);
                                                                                label_produtos.setOpaque(true);
                                                                            }
                                                                            @Override
                                                                            public void mouseExited(java.awt.event.MouseEvent evt) {
                                                                                label_produtos.setBackground(new Color(30, 30, 30));
                                                                                label_produtos.setOpaque(true);
                                                                            }
                                                                            @Override
                                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                                cardLayout.show(content, "PRODUTOS_PANEL");
                                                                            }
                                                                            });
                                                                            sidebar.add(label_produtos);
                                                                            JLabel label_clientes = new JLabel("Clientes", SwingConstants.CENTER);
                                                                            label_clientes.setForeground(Color.WHITE);
                                                                            label_clientes.setFont(new Font("Yu Gothic UI", Font.BOLD, 30));
                                                                            label_clientes.setBounds(0, 285, 206, 57);
                                                                            label_clientes.setOpaque(true);
                                                                            label_clientes.setBackground(new Color(30, 30, 30));
                                                                            label_clientes.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                                                                            label_clientes.addMouseListener(new java.awt.event.MouseAdapter() {
                                                                                @Override
                                                                                public void mouseEntered(java.awt.event.MouseEvent evt) {
                                                                                    label_clientes.setBackground(highlightColor);
                                                                                    label_clientes.setOpaque(true);
                                                                                }
                                                                                @Override
                                                                                public void mouseExited(java.awt.event.MouseEvent evt) {
                                                                                    label_clientes.setBackground(new Color(30, 30, 30));
                                                                                    label_clientes.setOpaque(true);
                                                                                }
                                                                                @Override
                                                                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                                    cardLayout.show(content, "CLIENTES_PANEL");
                                                                                }
                                                                                });
                                                                                sidebar.add(label_clientes);
                                                                                JLabel label_relatorios = new JLabel("Relatórios", SwingConstants.CENTER);
                                                                                label_relatorios.setForeground(Color.WHITE);
                                                                                label_relatorios.setFont(new Font("Yu Gothic UI", Font.BOLD, 30));
                                                                                label_relatorios.setBounds(0, 342, 206, 57);
                                                                                label_relatorios.setOpaque(true);
                                                                                label_relatorios.setBackground(new Color(30, 30, 30));
                                                                                label_relatorios.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                                                                                label_relatorios.addMouseListener(new java.awt.event.MouseAdapter() {
                                                                                    @Override
                                                                                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                                                                                        label_relatorios.setBackground(highlightColor);
                                                                                        label_relatorios.setOpaque(true);
                                                                                    }
                                                                                    @Override
                                                                                    public void mouseExited(java.awt.event.MouseEvent evt) {
                                                                                        label_relatorios.setBackground(new Color(30, 30, 30));
                                                                                        label_relatorios.setOpaque(true);
                                                                                    }
                                                                                    @Override
                                                                                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                                        cardLayout.show(content, "RELATORIOS_PANEL");
                                                                                    }
                                                                                    });
                                                                                    sidebar.add(label_relatorios);
                                                                                    //==============================================================
                                                                                    frmEletronicaPikachu.getContentPane().add(sidebar, BorderLayout.WEST);
                                                                                    frmEletronicaPikachu.getContentPane().add(content, BorderLayout.CENTER);
                                                                                    cardLayout.show(content, "MAIN_PANEL");
                                                                                    frmEletronicaPikachu.setVisible(true);
                                                                                }
                                                                            }