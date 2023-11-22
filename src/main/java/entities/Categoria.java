package entities;

import com.google.firebase.database.DataSnapshot;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Categoria {
	private static int ultimoId = 0;
	private String nome;
	private int idLocal; // ID local
	private String firebaseId; // ID do Firebase

	public Categoria(String nome) {
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

	public static Categoria fromSnapshot(DataSnapshot snapshot) {
		Categoria categoria = new Categoria();
		categoria.setFirebaseId(snapshot.child("firebaseId").getValue(String.class));
		categoria.setIdLocal(snapshot.child("idLocal").getValue(Integer.class));
		categoria.setNome(snapshot.child("nome").getValue(String.class));
		return categoria;
	}

	public static Categoria fromString(String nome) {
		Categoria categoria = new Categoria();
		categoria.setIdLocal(gerarNovoId()); // Use o método gerarNovoId ou alguma lógica similar
		categoria.setNome(nome);
		return categoria;
	}

}
