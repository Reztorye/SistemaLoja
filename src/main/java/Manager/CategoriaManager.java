package Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Categoria;

public class CategoriaManager {
    private List<Categoria> categorias;
    private Map<Integer, String> categoriaIdMap;
    private String idMapKey;

    public CategoriaManager() {
        this.categorias = new ArrayList<>();
        this.categoriaIdMap = new HashMap<>();
    }

    public void setIdMapKey(String idMapKey) {
        this.idMapKey = idMapKey;
    }

    public String getIdMapKey() {
        return this.idMapKey;
    }

    public void mapInternalIdToFirebaseId(Integer internalId, String firebaseId) {
        categoriaIdMap.put(internalId, firebaseId);
    }

    public String getFirebaseId(Integer internalId) {
        return categoriaIdMap.get(internalId);
    }

    public void removeCategoriaIdMapping(Integer internalId) {
        categoriaIdMap.remove(internalId);
    }

    public Categoria adicionarCategoria(String nome) {
        Categoria categoria = new Categoria(nome);
        categorias.add(categoria);
        return categoria;
    }
}
