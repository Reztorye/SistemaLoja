package entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class Venda {
	private static int ultimoId = 0;
	private int idLocal; // ID local
	private String firebaseId; // ID do Firebase
	private Cliente cliente;
	private Date data;
	private List<ItemVenda> itensVenda;

	public Venda(Cliente cliente, List<ItemVenda> itensVenda, Date data) {
		this.idLocal = ++ultimoId;
		this.cliente = cliente;
		this.itensVenda = new ArrayList<>(itensVenda);
		this.data = data;
		cliente.adicionarCompra(this); // Adiciona esta venda ao histórico do cliente
	}

	public void adicionarItemVenda(ItemVenda itemVenda) {
		// Método para adicionar um item à venda
		itensVenda.add(itemVenda);
	}

	public void removerItemVenda(int sku) {
		// Método para remover um item da venda
		itensVenda.removeIf(item -> item.getProduto().getSku().equals(sku));
	}

	public double calcularValorTotal() {
		// Método para calcular o valor total da venda
		return itensVenda.stream()
				.mapToDouble(item -> item.getProduto().getPrecoVenda() * item.getQuantidade())
				.sum();
	}

	public static int gerarNovoId() {
		return ++ultimoId;
	}

	public void setFirebaseId(String firebaseId) {
		this.firebaseId = firebaseId;
	}

	public String getFirebaseId() {
		return firebaseId;
	}

	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Venda - ID: ").append(idLocal)
				.append(", cliente: ").append(cliente.getNome())
				.append(", data: ").append(formato.format(data))
				.append(", itens: ").append(itensVenda)
				.append(" Total: ").append(String.format("R$ %.2f", calcularValorTotal()));

		return sb.toString();
	}

	public List<ItemVenda> getProdutosVendidos() {
		return this.itensVenda;
	}

}
