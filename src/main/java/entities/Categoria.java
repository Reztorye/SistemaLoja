package entities;

import lombok.Data;

@Data
public class Categoria {
	private static int ultimoId = 0;
	private int id;
	private String nome;

	public Categoria(String nome) {
		this.id = ++ultimoId;
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return "ID: " + id + " | Nome: " + nome;
	}
}
