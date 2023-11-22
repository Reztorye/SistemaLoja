package Manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import entities.Categoria;
import entities.Cliente;
import entities.Fornecedor;
import entities.ItemVenda;
import entities.Produto;
import entities.ProdutoVendido;
import entities.Promocao;
import entities.Venda;

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

	public List<ProdutoVendido> getTopMaisVendidos(int topN) {
		return vendas.stream()
				.flatMap(venda -> venda.getProdutosVendidos().stream())
				.collect(Collectors.groupingBy(ItemVenda::getProduto, Collectors.summingInt(ItemVenda::getQuantidade)))
				.entrySet().stream()
				.sorted(Map.Entry.<Produto, Integer>comparingByValue().reversed())
				.limit(topN)
				.map(entry -> new ProdutoVendido(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
	}

	public List<ProdutoVendido> calcularProdutosMaisVendidos() {
		return vendas.stream()
				.flatMap(venda -> venda.getProdutosVendidos().stream())
				.collect(Collectors.groupingBy(ItemVenda::getProduto, Collectors.summingInt(ItemVenda::getQuantidade)))
				.entrySet().stream()
				.sorted(Map.Entry.<Produto, Integer>comparingByValue().reversed())
				.map(entry -> new ProdutoVendido(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
	}

	public List<ProdutoVendido> converterParaProdutosVendidos(List<Produto> produtos) {
		return produtos.stream()
				.map(produto -> new ProdutoVendido(produto, 0))
				.collect(Collectors.toList());
	}

	public double totalDeVendasPorPeriodo(Date inicio, Date fim) {
		return vendas.stream()
				.filter(venda -> !venda.getData().before(inicio) && !venda.getData().after(fim))
				.mapToDouble(Venda::calcularValorTotal)
				.sum();
	}

	public void totalDeVendasPorPeriodo() {
		// Mostrar o total de vendas em um determinado periodo.
	}

	public void produtosMaisVendidosPorPeriodo() {
		// Mostrar os produtos mais vendidos em um determinado periodo.
	}

	public void imprimirProdutos() {
		System.out.println("Lista de Produtos:");
		for (Produto produto : this.produtos) { // Substitua 'this.listaProdutos' por 'this.produtos'
			System.out.println("SKU: " + produto.getSku() +
					", Nome: " + produto.getNome() +
					", Preço: " + produto.getPrecoVenda() +
					", Estoque: " + produto.getEstoqueDisponivel()); // Ajuste esses campos conforme sua classe Produto
		}
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