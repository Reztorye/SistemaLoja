package Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Fornecedor;

public class FornecedorManager {
    private List<Fornecedor> fornecedores;
    private Map<Integer, String> fornecedorIdMap;
    private String idMapKey;

    public FornecedorManager() {
        this.fornecedores = new ArrayList<>();
        this.fornecedorIdMap = new HashMap<>();
    }

    public void setIdMapKey(String idMapKey) {
        this.idMapKey = idMapKey;
    }

    public String getIdMapKey() {
        return this.idMapKey;
    }

    public void mapInternalIdToFirebaseId(Integer internalId, String firebaseId) {
        fornecedorIdMap.put(internalId, firebaseId);
    }

    public String getFirebaseId(Integer internalId) {
        return fornecedorIdMap.get(internalId);
    }

    public void removeFornecedorIdMapping(Integer internalId) {
        fornecedorIdMap.remove(internalId);
    }

    public Fornecedor adicionarFornecedor(String nome) {
        Fornecedor fornecedor = new Fornecedor(nome);
        fornecedores.add(fornecedor);
        return fornecedor;
    }
}
