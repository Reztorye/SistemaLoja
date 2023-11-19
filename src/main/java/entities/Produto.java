package entities;

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
	private Categoria categoria;
	private Fornecedor fornecedor;
	private boolean descontoAtivo;
	private double valorDesconto;

	// auxiliares
	private int quantidadeEstoque = 0;
	private int vendidos = 0;
	private double lucro = 0.0;
	private double valorLucro = 0.0;
	private double custoTemporario = 0.0;
	private double receita = 0.0;

	private String firebaseId;

	public Produto(Integer sku, String nome, String descricao, double precoCusto, double precoVenda,
			int estoqueDisponivel, Categoria categoria, Fornecedor fornecedor) {
		super();
		this.sku = sku;
		this.nome = nome;
		this.descricao = descricao;
		this.precoCusto = precoCusto;
		this.precoVenda = precoVenda;
		this.estoqueDisponivel = estoqueDisponivel;
		this.categoria = categoria;
		this.fornecedor = fornecedor;
	}

	public void setFirebaseId(String firebaseId) {
		this.firebaseId = firebaseId;
	}

	public String getFirebaseId() {
		return firebaseId;
	}

	@Override
	public String toString() {
		return ("SKU:" + String.format(" %-5d", this.getSku()) + "| " + String.format("%-20s", this.getNome())
				+ " | Cat: "
				+ String.format("%-10s", this.getCategoria().getNome()) + " | Fornecedor: "
				+ String.format("%-10s", this.getFornecedor().getNome()) + " | "
				+ String.format("R$ %10.2f", this.getPrecoVenda()) + " | Estoque: "
				+ String.format("%5s", this.getEstoqueDisponivel()) + " unidades.");
	}

	public double getPreco() {
		return precoVenda;
	}

	public double getPrecoVendaComDesconto() {
		if (descontoAtivo) {
			// Assumindo que o valorDesconto é um valor percentual
			return getPrecoVenda() * (1 - (valorDesconto / 100));
		} else {
			return getPrecoVenda();
		}
	}

}
