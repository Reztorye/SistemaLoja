package entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Fornecedor {
	private static int ultimoId = 0;
	private int idLocal; // ID local
	private String nome;
	private String firebaseId; // ID do Firebase

	public Fornecedor(String nome) {
		this.idLocal = ++ultimoId; // Incrementa o ID local
		this.nome = nome;
	}

	public void setId(int id) {
		this.idLocal = id;
	}

	public int getIdLocal() {
		return idLocal;
	}

	public void setFirebaseId(String firebaseId) {
		this.firebaseId = firebaseId;
	}

	public String getFirebaseId() {
		return firebaseId;
	}

	public static int gerarNovoId() {
		return ++ultimoId;
	}

	@Override
	public String toString() {
		return "ID Local: " + idLocal + " | Nome: " + nome + " | ID Firebase: " + firebaseId;
	}
}
