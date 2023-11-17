package entities;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

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
		StringBuilder sb = new StringBuilder();
		sb.append("ID: ").append(id).append(" | Nome: ").append(nome)
				.append("\nEndereco: ").append(endereco).append("\nTelefone: ").append(telefone)
				.append(" | email: ").append(email).append("\nHistorico de compras:\n");

		for (Venda venda : historicoDeCompras) {
			sb.append(" - ").append(venda.getId()).append(": ").append(venda.toString()).append("\n");
			// Utilize o método toString() da classe Venda, ou adapte conforme necessário
		}

		return sb.toString();
	}

}
