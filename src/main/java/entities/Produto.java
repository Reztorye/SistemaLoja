package entities;

import com.google.firebase.database.DataSnapshot;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Produto {
	private Integer sku;
	private String nome;
	private String descricao;
	private double precoCusto;
	private double precoVenda;
	private int estoqueDisponivel;
	private String categoria;
	private String fornecedor;
	private boolean descontoAtivo;
	private double valorDesconto;
	private String firebaseId;

	// auxiliares
	private int quantidadeEstoque = 0;
	private int vendidos = 0;
	private double lucro = 0.0;
	private double valorLucro = 0.0;
	private double custoTemporario = 0.0;
	private double receita = 0.0;
	private String categoriaNome;

	public Produto(Integer sku, String nome, String descricao, double precoCusto, double precoVenda,
			int estoqueDisponivel, String categoria, String fornecedor) {
		this.sku = sku;
		this.nome = nome;
		this.descricao = descricao;
		this.precoCusto = precoCusto;
		this.precoVenda = precoVenda;
		this.estoqueDisponivel = estoqueDisponivel;
		this.categoria = categoria;
		this.fornecedor = fornecedor;

	}

	@Override
	public String toString() {
		return ("SKU:" + String.format(" %-5d", this.getSku()) + "| " + String.format("%-20s", this.getNome())
				+ " | Cat: "
				+ String.format("%-10s", this.getCategoria()) + " | Fornecedor: "
				+ String.format("%-10s", this.getFornecedor()) + " | "
				+ String.format("R$ %10.2f", this.getPrecoVenda()) + " | Estoque: "
				+ String.format("%5s", this.getEstoqueDisponivel()) + " unidades.");
	}

	public double getPreco() {
		return precoVenda;
	}

	public String getCategoriaNome() {
		return categoriaNome;
	}

	public void setCategoriaNome(String categoriaNome) {
		this.categoriaNome = categoriaNome;
	}

	public String getFirebaseId() {
		return firebaseId;
	}

	public void setFirebaseId(String firebaseId) {
		this.firebaseId = firebaseId;
	}

	public double getPrecoVendaComDesconto() {
		if (descontoAtivo) {
			return getPrecoVenda() * (1 - (valorDesconto / 100));
		} else {
			return getPrecoVenda();
		}
	}

	public static Produto fromSnapshot(DataSnapshot snapshot) {
		Produto produto = new Produto();
		produto.setSku(snapshot.child("sku").getValue(Integer.class));
		// Adicione mensagens de log
		System.out.println("SKU: " + produto.getSku());

		// Restante do mapeamento...

		return produto;
	}

}
