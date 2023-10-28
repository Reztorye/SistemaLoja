package entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Venda {
	private static int ultimoId = 0;
	private int id;
	private Cliente cliente;
	private Date data;
	private List<Produto> produtos;
	private List<ItemVenda> itensVenda;

	public Venda(Cliente cliente, List<ItemVenda> itensVenda, int quantidade, Date date) {
		this.id = ++ultimoId;
		this.cliente = cliente;
		this.itensVenda = new ArrayList<>(itensVenda);
		this.data = new Date(); // Data atual
	}

	public void adicionarVenda(int sku) {
		for (Produto produto : produtos) {
			if (produto.getSku().equals(sku)) {
				produtos.add(produto);
			}
		}
	}

	public void removerVenda(int sku) {
		for (Produto produto : produtos) {
			if (produto.getSku().equals(sku)) {
				produtos.remove(produto);
			}
		}
	}

	public List<ItemVenda> getProdutosVendidos() {
		return itensVenda;
	}

	public double calcularValorTotal() {
		double valorTotal = 0;
		for (ItemVenda itemVenda : itensVenda) {
			valorTotal += itemVenda.getProduto().getPrecoVenda() * itemVenda.getQuantidade();
		}
		return valorTotal;
	}

	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	@Override
	public String toString() {
		return "Venda - ID: " + id + ", cliente: " + cliente.getNome() + ", data: " + formato.format(data) + ", itens: "
				+ itensVenda + " Total: " + String.format("R$ %.2f", calcularValorTotal());
	}

}
