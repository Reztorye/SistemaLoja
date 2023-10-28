package entities;

import lombok.Data;

@Data
public class Fornecedor {
	private static int ultimoId = 0;
	private int id;
	private String nome;

	public Fornecedor(String nome) {
		this.id = ++ultimoId;
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return "ID: " + id + " | Nome: " + nome;
	}
}
