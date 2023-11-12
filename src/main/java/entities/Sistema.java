package entities;

import java.util.ArrayList;
import java.util.Date;
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
	    // ... mais categorias ...

	    fornecedores.add(new Fornecedor("Xiaomi"));
	    fornecedores.add(new Fornecedor("Samsung"));
	    fornecedores.add(new Fornecedor("Apple"));
	}

	public Produto adicionarProduto(Integer sku, String nome, String descricao, double precoCusto, double precoVenda, int estoqueDisponivel, Categoria categoria, Fornecedor fornecedor) {
	    // Você não precisa recriar as instâncias de Categoria e Fornecedor aqui,
	    // pois elas já são passadas como argumentos para o método.
	    
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
				return; // Para de procurar após encontrar e remover o produto
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
	public void adicionarCliente(Cliente cliente) {
		clientes.add(cliente);
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
		return null; // Retorna null se o cliente com o ID especificado não for encontrado
	}

	// Vendas
	public void realizarVenda(Cliente cliente, List<ItemVenda> itensVenda) {
		boolean estoqueSuficiente = true;
		int quantidadeTotal = 0;

		// Verificar se há quantidade suficiente em estoque para todos os produtos na
		// lista
		for (ItemVenda itemVenda : itensVenda) {
			Produto produto = itemVenda.getProduto();
			int quantidade = itemVenda.getQuantidade();

			if (produto.getEstoqueDisponivel() < quantidade) {
				estoqueSuficiente = false;
				break;
			}

			quantidadeTotal += quantidade;
		}

		// Se houver quantidade suficiente em estoque para todos os produtos
		if (estoqueSuficiente) {
			// Atualizar o estoque dos produtos
			for (ItemVenda itemVenda : itensVenda) {
				Produto produto = itemVenda.getProduto();
				int quantidade = itemVenda.getQuantidade();
				produto.setEstoqueDisponivel(produto.getEstoqueDisponivel() - quantidade);
			}

			// Criar a venda
			Venda venda = new Venda(cliente, itensVenda, quantidadeTotal, new Date());

			// Adicionar a venda à lista de vendas
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

	// Categorias
	public Categoria adicionarCategoria(String nome) {
		for (Categoria categoria : categorias) {
			if (categoria.getNome().equalsIgnoreCase(nome)) {
				return categoria; // Retorna a categoria existente se encontrada
			}
		}
		// Se a categoria não existe, cria uma nova
		Categoria novaCategoria = new Categoria(nome);
		categorias.add(novaCategoria);
		return novaCategoria;
	}

	public void listarCategorias() {
		for (Categoria categoria : categorias) {
			System.out.println(categoria);
		}
	}

	// Fornecedores
	public Fornecedor adicionarFornecedor(String nome) {
		for (Fornecedor fornecedor : fornecedores) {
			if (fornecedor.getNome().equalsIgnoreCase(nome)) {
				return fornecedor; // Retorna o fornecedor existente se encontrado
			}
		}
		// Se o fornecedor não existe, cria um novo
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
	            System.out.println("Cliente encontrado: " + cliente.getNome());  // Debug: Verifique se o cliente foi encontrado
	            cliente.setNome(nome);
	            cliente.setEndereco(endereco);
	            cliente.setTelefone(telefone);
	            System.out.println("Cliente atualizado: " + cliente.getNome());  // Debug: Verifique a atualização
	            return;
	        }
	    }
	    System.out.println("Cliente não encontrado: " + email);  // Debug: Verifique se o cliente não foi encontrado
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
        promocoes.add(promocao); // Adiciona a nova promoção à lista
    }

    public List<Promocao> getPromocoes() {
        return promocoes;
    }
    
    public List<Venda> getVendas() {
        return new ArrayList<>(vendas); // Retorna uma cópia da lista para evitar modificações externas
    }

}


