package Manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
	private CategoriaManager categoriaManager;
	private FornecedorManager fornecedorManager;

	public Sistema() {

		this.produtos = new ArrayList<>();
		this.clientes = new ArrayList<>();
		this.vendas = new ArrayList<>();
		this.categorias = new ArrayList<>();
		this.fornecedores = new ArrayList<>();
		this.categoriaManager = new CategoriaManager();
		this.fornecedorManager = new FornecedorManager();

		promocoes = new ArrayList<>();

	}

	public List<Venda> getVendas() {
		return this.vendas;
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

	public Categoria adicionarCategoriaFirebase(String nome) {
		Categoria categoria = categoriaManager.adicionarCategoria(nome);
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categorias");
		String firebaseId = ref.push().getKey();
		categoria.setFirebaseId(firebaseId);
		ref.child(firebaseId).setValueAsync(categoria);
		return categoria;
	}

	public Fornecedor adicionarFornecedorFirebase(String nome) {
		Fornecedor fornecedor = fornecedorManager.adicionarFornecedor(nome);
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("fornecedores");
		String firebaseId = ref.push().getKey();
		fornecedor.setFirebaseId(firebaseId);
		ref.child(firebaseId).setValueAsync(fornecedor);
		return fornecedor;
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

	public List<Cliente> getClientes() {
		return this.clientes;
	}

}
