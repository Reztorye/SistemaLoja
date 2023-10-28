package entities;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Cliente {
	private static int ultimoId = 0;
	private Integer id;
	private String nome;
	private String endereco;
	private String telefone;
	private String email;
	private List<Venda> historicoDeCompras;

	public Cliente(String nome, String endereco, String telefone, String email) {
		this.id = ++ultimoId;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.email = email;
		this.historicoDeCompras = new ArrayList<>();
	}

	public void adicionarCompra(Venda venda) {
		historicoDeCompras.add(venda);
	}

	@Override
	public String toString() {
		return "ID: " + id + " | Nome: " + nome + " \nEndereco: " + endereco + "\nTelefone: " + telefone + " | email: "
				+ email + "\nHistorico de compras: " + historicoDeCompras;
	}

}
