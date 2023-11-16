package entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.NoSuchElementException;

public class Sistema {
	private List<Produto> produtos;
	private List<Cliente> clientes;
	private List<Venda> vendas;
	private List<Categoria> categorias;
	private List<Fornecedor> fornecedores;
	private List<Promocao> promocoes;

	public Sistema() {
		this.produtos = new ArrayList<>();
		this.clientes = new ArrayList<>();
		this.vendas = new ArrayList<>();
		this.categorias = new ArrayList<>();
		this.fornecedores = new ArrayList<>();
		promocoes = new ArrayList<>();
		categorias.add(new Categoria("Smartphone"));
	    categorias.add(new Categoria("Mouse"));
	    categorias.add(new Categoria("Notebook"));

	    fornecedores.add(new Fornecedor("Xiaomi"));
	    fornecedores.add(new Fornecedor("Samsung"));
	    fornecedores.add(new Fornecedor("Apple"));
	}
	
	public void inicializarDadosDeTeste() {
	    Categoria Celular  = adicionarCategoria("Celular");
	    Categoria Tablet  = adicionarCategoria("Tablet");
	    Fornecedor Xiaomi = adicionarFornecedor("Xiaomi");
	    Fornecedor Samsung = adicionarFornecedor("Samsung");

	    Produto prod1 = adicionarProduto(1, "Produto 1", "Descrição 1", 10.0, 15.0, 100, Celular, Xiaomi);
	    Produto prod2 = adicionarProduto(2, "Produto 2", "Descrição 2", 20.0, 25.0, 200, Celular, Xiaomi);
	    
	    Produto prod3 = adicionarProduto(1234, "Mi 8 lite", "descricao do produto 1", 999.90, 1499.99, 50, Celular, Xiaomi);
		Produto prod4 = adicionarProduto(1235, "Redmi note 12", "descricao do produto 2", 1999.90, 2499.95, 20, Tablet, Xiaomi);
		Produto prod5 = adicionarProduto(1236, "Galaxy S23", "descricao do produto 3", 2999.90, 3499.99, 4, Celular, Samsung);

		Cliente cliente1 = adicionarCliente("Rodrigo Garcia", "Rua Almir Nelson de Almeida, 290, bloco 6 apto 5 - Curitiba - PR", "(41) 99866-6332", "rodrigosrising@gmail.com");
		Cliente cliente2 = adicionarCliente("Camila Sartori", "Rua Almir Nelson de Almeida, 290, bloco 6 apto 5 - Curitiba - PR", "(41) 99633-9225", "camylla55@gmail.com");	    
	    Cliente cliente3 = adicionarCliente("Cliente 1", "Endereço 1", "Telefone 1", "cliente1@email.com");
	    Cliente cliente4 = adicionarCliente("Cliente 2", "Endereço 2", "Telefone 2", "cliente2@email.com");

	    List<ItemVenda> itensVenda1 = new ArrayList<>();
	    itensVenda1.add(new ItemVenda(prod1, 3));

	    List<ItemVenda> itensVenda2 = new ArrayList<>();
	    itensVenda2.add(new ItemVenda(prod2, 5));

	    int quantidadeTotalVenda1 = itensVenda1.stream().mapToInt(ItemVenda::getQuantidade).sum();
	    
	    Calendar calendar = new GregorianCalendar(2023, Calendar.JANUARY, 1); 
	    Date dataEspecifica = calendar.getTime();

	    Venda venda1 = new Venda(cliente1, itensVenda1, quantidadeTotalVenda1, dataEspecifica);

	    calendar.set(Calendar.DATE, 2); 
	    Date dataEspecificaVenda2 = calendar.getTime();
	    
	    int quantidadeTotalVenda2 = itensVenda2.stream().mapToInt(ItemVenda::getQuantidade).sum();
	    
	    Venda venda2 = new Venda(cliente2, itensVenda2, quantidadeTotalVenda2, dataEspecificaVenda2);
	    
	    List<ItemVenda> itensVenda3 = new ArrayList<>();
	    itensVenda3.add(new ItemVenda(prod3, 2)); // Venda de 2 unidades do "Mi 8 lite"

	    List<ItemVenda> itensVenda4 = new ArrayList<>();
	    itensVenda4.add(new ItemVenda(prod4, 1)); // Venda de 1 unidade do "Redmi note 12"

	    List<ItemVenda> itensVenda5 = new ArrayList<>();
	    itensVenda5.add(new ItemVenda(prod5, 3)); // Venda de 3 unidades do "Galaxy S23"

	    // Calcula a quantidade total vendida para as novas vendas
	    int quantidadeTotalVenda3 = itensVenda3.stream().mapToInt(ItemVenda::getQuantidade).sum();
	    int quantidadeTotalVenda4 = itensVenda4.stream().mapToInt(ItemVenda::getQuantidade).sum();
	    int quantidadeTotalVenda5 = itensVenda5.stream().mapToInt(ItemVenda::getQuantidade).sum();

	    calendar.set(Calendar.DATE, 3);
	    Date dataVenda3 = calendar.getTime(); // Data para a terceira venda

	    calendar.set(Calendar.DATE, 4);
	    Date dataVenda4 = calendar.getTime(); // Data para a quarta venda

	    calendar.set(Calendar.DATE, 5);
	    Date dataVenda5 = calendar.getTime(); // Data para a quinta venda

	    // Cria e adiciona as novas vendas à lista
	    Venda venda3 = new Venda(cliente3, itensVenda3, quantidadeTotalVenda3, dataVenda3);
	    Venda venda4 = new Venda(cliente4, itensVenda4, quantidadeTotalVenda4, dataVenda4);
	    Venda venda5 = new Venda(cliente1, itensVenda5, quantidadeTotalVenda5, dataVenda5); 

	    vendas.add(venda3);
	    vendas.add(venda4);
	    vendas.add(venda5);
	    vendas.add(venda1);
	    vendas.add(venda2);
	}

	public double totalDeVendasPorPeriodo(Date inicio, Date fim) {
        return vendas.stream()
                     .filter(venda -> !venda.getData().before(inicio) && !venda.getData().after(fim))
                     .mapToDouble(Venda::calcularValorTotal)
                     .sum();
    }
	
	public Produto adicionarProduto(Integer sku, String nome, String descricao, double precoCusto, double precoVenda, int estoqueDisponivel, Categoria categoria, Fornecedor fornecedor) {
	    Produto produto = new Produto(sku, nome, descricao, precoCusto, precoVenda, estoqueDisponivel, categoria, fornecedor);
	    produtos.add(produto);
	    return produto;
	}

	public void atualizarProduto(Integer sku, String nome, String descricao, double precoVenda, String NomeCategoria, String nomeFornecedor) {
        for (Produto produto : produtos) {
            if (produto.getSku().equals(sku)) {
                produto.setNome(nome);
                produto.setDescricao(descricao);
                produto.setPrecoVenda(precoVenda);
                produto.getCategoria().setNome(NomeCategoria);
                produto.getFornecedor().setNome(nomeFornecedor);
                System.out.println("Produto atualizado: " + produto.getNome());
                return;
            }
        }
        System.out.println("Produto não encontrado: " + sku);
    }
	
	public void removerProduto(int sku) {
		for (int i = 0; i < produtos.size(); i++) {
			Produto p = (Produto) produtos.get(i);
			if (p.getSku().equals(sku)) {
				produtos.remove(i);
				System.out.println("Produto removido: " + p.getNome());
				return; 
			}
		}
		System.out.println("Produto não encontrado: " + sku);
	}

	public void listarProdutos() {
		for (Produto produto : produtos) {
			System.out.println(produto);
		}
	}

	public void listarProdutosEmFalta() {
		// Mostrar todos os produtos com estoque abaixo do minimo ou zerados.
		// estoqueDisponivel
		for (Produto produto : produtos) {
			if (produto.getEstoqueDisponivel() <= 5) {
				System.out.println(produto);
			}
		}
	}

	public Produto buscarProdutoPorSku(int skuProduto) {
		for (Produto produto : produtos) {
			if (produto.getSku() == skuProduto) {
				return produto; // Retorna o produto se o SKU for encontrado
			}
		}
		return null; // Retorna null se o SKU não for encontrado
	}

	public void totalDeVendasPorPeriodo() {
		// Mostrar o total de vendas em um determinado periodo.
	}

	public void produtosMaisVendidosPorPeriodo() {
		// Mostrar os produtos mais vendidos em um determinado periodo.
	}
	public void imprimirProdutos() {
	    System.out.println("Lista de Produtos:");
	    for (Produto produto : this.produtos) {  // Substitua 'this.listaProdutos' por 'this.produtos'
	        System.out.println("SKU: " + produto.getSku() +
	                           ", Nome: " + produto.getNome() +
	                           ", Preço: " + produto.getPrecoVenda() +
	                           ", Estoque: " + produto.getEstoqueDisponivel()); // Ajuste esses campos conforme sua classe Produto
	    }
	}

	// =============================================================================

	// Clientes
	public Cliente adicionarCliente(String nome, String endereco, String telefone, String email) {
	    Cliente cliente = new Cliente(nome, endereco, telefone, email);
	    clientes.add(cliente);
	    return cliente;
	}

	public void atualizarCliente(Cliente cliente) {
	    // atualizar o cliente na lista de clientes
	    // isso substitui a necessidade de passar todos os campos individualmente
	    for (int i = 0; i < clientes.size(); i++) {
	        if (clientes.get(i).getId().equals(cliente.getId())) {
	            clientes.set(i, cliente);
	            System.out.println("Cliente atualizado: " + cliente.getNome());
	            return;
	        }
	    }
	    System.out.println("Cliente não encontrado: " + cliente.getId());
	}
	
	public void listarClientes() {
		for (Cliente cliente : clientes) {
			System.out.println(cliente);
		}
	}

	public void removerCliente(int id) {
		for (int i = 0; i < clientes.size(); i++) {
			Cliente c = (Cliente) clientes.get(i);
			if (c.getId().equals(id)) {
				clientes.remove(i);
				System.out.println("Cliente removido: " + c.getNome());
				return; // Para de procurar após encontrar e remover o produto
			}
		}
		System.out.println("Cliente não encontrado: " + id);
	}

	public Cliente buscarClientePorId(int id) {
		for (Cliente cliente : clientes) {
			if (cliente.getId() == id) {
				return cliente;
			}
		}
		return null; 
	}

	public void realizarVenda(Cliente cliente, List<ItemVenda> itensVenda) {
		boolean estoqueSuficiente = true;
		int quantidadeTotal = 0;

		for (ItemVenda itemVenda : itensVenda) {
			Produto produto = itemVenda.getProduto();
			int quantidade = itemVenda.getQuantidade();

			if (produto.getEstoqueDisponivel() < quantidade) {
				estoqueSuficiente = false;
				break;
			}

			quantidadeTotal += quantidade;
		}

		if (estoqueSuficiente) {
			for (ItemVenda itemVenda : itensVenda) {
				Produto produto = itemVenda.getProduto();
				int quantidade = itemVenda.getQuantidade();
				produto.setEstoqueDisponivel(produto.getEstoqueDisponivel() - quantidade);
			}

			Venda venda = new Venda(cliente, itensVenda, quantidadeTotal, new Date());

			vendas.add(venda);

			System.out.println("Venda realizada com sucesso!");
		} else {
			System.out.println("Quantidade insuficiente em estoque para um ou mais produtos.");
		}
	}

	public void listarVendas() {
		for (Venda venda : vendas) {
			System.out.println(venda);
		}
	}

	public void mostrarVendasRealizadas() {
		System.out.println("Vendas Realizadas:");
		for (Venda venda : vendas) {
			System.out.println("ID da Venda: " + venda.getId());
			System.out.println("Cliente: " + venda.getCliente().getNome());
			System.out.println("Data: " + venda.getData());
			System.out.println("Produtos:");

			for (ItemVenda itemVenda : venda.getProdutosVendidos()) {
				Produto produto = itemVenda.getProduto();
				int quantidade = itemVenda.getQuantidade();
				System.out.println("  - " + produto.getNome() + ", Quantidade: " + quantidade);
			}

			System.out.println("-----------------------");
		}
	}

	public Categoria adicionarCategoria(String nome) {
		for (Categoria categoria : categorias) {
			if (categoria.getNome().equalsIgnoreCase(nome)) {
				return categoria; 
			}
		}
		Categoria novaCategoria = new Categoria(nome);
		categorias.add(novaCategoria);
		return novaCategoria;
	}

	public void listarCategorias() {
		for (Categoria categoria : categorias) {
			System.out.println(categoria);
		}
	}
	public Fornecedor adicionarFornecedor(String nome) {
		for (Fornecedor fornecedor : fornecedores) {
			if (fornecedor.getNome().equalsIgnoreCase(nome)) {
				return fornecedor; 
			}
		}
		Fornecedor novoFornecedor = new Fornecedor(nome);
		fornecedores.add(novoFornecedor);
		return novoFornecedor;
	}

	public void listarFornecedores() {
		for (Fornecedor fornecedor : fornecedores) {
			System.out.println(fornecedor);
		}
	}

	public void atualizarCliente(String email, String nome, String endereco, String telefone) {
	    for (Cliente cliente : clientes) {
	        if (cliente.getEmail().equals(email)) {
	            cliente.setNome(nome);
	            cliente.setEndereco(endereco);
	            cliente.setTelefone(telefone);
	            return;
	        }
	    }
	}

	public List<Produto> getProdutos() {
        return produtos; // retorna a lista real
	}
	
	public Categoria buscarCategoriaPorNome(String nome) {
	    for (Categoria categoria : categorias) {
	        if (categoria.getNome().equals(nome)) {
	            return categoria;
	        }
	    }
	    throw new NoSuchElementException("Categoria com o nome '" + nome + "' não encontrado.");
	}

	public Fornecedor buscarFornecedorPorNome(String nome) {
	    for (Fornecedor fornecedor : fornecedores) {
	        if (fornecedor.getNome().equals(nome)) {
	            return fornecedor;
	        }
	    }
	    throw new NoSuchElementException("Fornecedor com o nome '" + nome + "' não encontrado.");
	}
	
	public List<Categoria> getCategorias() {
	    return categorias;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}
	
	public void adicionarPromocao(Promocao promocao) {
        promocoes.add(promocao); 
    }

    public List<Promocao> getPromocoes() {
        return promocoes;
    }
    
    public List<Venda> getVendas() {
        return new ArrayList<>(vendas); 
    }
    
    public List<Cliente> getClientes() {
        return this.clientes;
    }
   
}


