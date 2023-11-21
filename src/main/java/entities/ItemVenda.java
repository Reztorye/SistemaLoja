package entities;

import lombok.Data;

@Data
public class ItemVenda {
	private Produto produto;
	private int quantidade;

	public ItemVenda(Produto produto, int quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
	}

	public double calcularPrecoTotal() {
		return produto.getPrecoVenda() * quantidade;
	}

	@Override
	public String toString() {
		return produto.getCategoria() + " " + produto.getFornecedor() + " " + produto.getNome()
				+ " - quantidade: " + quantidade + " - preço unitário: "
				+ String.format("R$ %.2f", produto.getPrecoVenda());
	}

}
