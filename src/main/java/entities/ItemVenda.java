package entities;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ItemVenda {
	private Produto produto;
	private int quantidade;
	private String firebaseId; // ID do Firebase para o produto

	public ItemVenda(Produto produto, int quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
		this.firebaseId = produto.getFirebaseId(); // Supondo que Produto tem um campo firebaseId
	}

	public double calcularPrecoTotal() {
		return produto.getPrecoVenda() * quantidade;
	}

	// Método para converter o item de venda em um formato adequado para Firebase
	public Map<String, Object> toFirebaseMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("produtoId", firebaseId);
		map.put("quantidade", quantidade);
		// Adicione outros campos relevantes, se necessário
		return map;
	}

	@Override
	public String toString() {
		return produto.getCategoria() + " " + produto.getFornecedor() + " " + produto.getNome()
				+ " - quantidade: " + quantidade + " - preço unitário: "
				+ String.format("R$ %.2f", produto.getPrecoVenda());
	}
}
