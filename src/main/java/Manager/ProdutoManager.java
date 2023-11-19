package Manager;

import com.google.firebase.database.*;
import entities.Categoria;
import entities.Fornecedor;
import entities.Produto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class ProdutoManager {
    private List<Produto> produtos;
    private Map<Integer, String> produtoSkuMap;
    private String skuMapKey;
    private List<FirebaseDataChangeListener> listeners = new ArrayList<>();

    public ProdutoManager() {
        this.produtos = new ArrayList<>();
        this.produtoSkuMap = new HashMap<>();
    }

    public void setSkuMapKey(String skuMapKey) {
        this.skuMapKey = skuMapKey;
    }

    public String getSkuMapKey() {
        return this.skuMapKey;
    }

    public void mapProdutoSku(Integer sku, String firebaseId) {
        produtoSkuMap.put(sku, firebaseId);
    }

    public String getFirebaseId(Integer sku) {
        return produtoSkuMap.get(sku);
    }

    public void removeProdutoSkuMapping(Integer sku) {
        produtoSkuMap.remove(sku);
    }

    public Produto adicionarProduto(Integer sku, String nome, String descricao, double precoCusto, double precoVenda,
            int estoqueDisponivel, Categoria categoria, Fornecedor fornecedor) {
        Produto produto = new Produto(sku, nome, descricao, precoCusto, precoVenda, estoqueDisponivel, categoria,
                fornecedor);
        produtos.add(produto);

        // Adicionar ao Firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("produtos");
        ref.child(String.valueOf(sku)).setValueAsync(produto);

        // Mapear SKU para Firebase ID
        mapProdutoSku(sku, String.valueOf(sku));

        return produto;
    }

    public void atualizarProduto(Integer sku, String nome, String descricao, double precoVenda, String nomeCategoria,
            String nomeFornecedor) {
        Produto produto = buscarProdutoPorSku(sku);
        if (produto != null) {
            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setPrecoVenda(precoVenda);
            produto.getCategoria().setNome(nomeCategoria);
            produto.getFornecedor().setNome(nomeFornecedor);

            // Atualizar no Firebase
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("produtos");
            ref.child(String.valueOf(sku)).setValueAsync(produto);

            // Atualizar mapeamento
            if (produtoSkuMap.containsKey(sku)) {
                removeProdutoSkuMapping(sku);
            }
            mapProdutoSku(sku, String.valueOf(sku));

            System.out.println("Produto atualizado: " + produto.getNome());
        } else {
            System.out.println("Produto não encontrado: " + sku);
        }
    }

    public void removerProduto(int sku) {
        produtos.removeIf(produto -> produto.getSku().equals(sku));

        // Remover do Firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("produtos");
        ref.child(String.valueOf(sku)).removeValueAsync();

        // Remover mapeamento
        if (produtoSkuMap.containsKey(sku)) {
            removeProdutoSkuMapping(sku);
        }

        System.out.println("Produto removido: SKU " + sku);
    }

    public Produto buscarProdutoPorSku(int skuProduto) {
        return produtos.stream()
                .filter(produto -> produto.getSku() == skuProduto)
                .findFirst()
                .orElse(null);
    }

    public List<Produto> getProdutos() {
        return new ArrayList<>(produtos);
    }

    public void listarProdutos() {
        for (Produto produto : produtos) {
            System.out.println(produto);
        }
    }

    public void listarProdutosEmFalta() {
        for (Produto produto : produtos) {
            if (produto.getEstoqueDisponivel() <= 5) {
                System.out.println(produto);
            }
        }
    }

    public void atualizarProdutoFirebase(Produto produto) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("produtos");
        DatabaseReference produtoRef = ref.child(produto.getSku().toString());

        // Executar a operação de atualização em uma thread separada
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                produtoRef.setValueAsync(produto).get(); // Aguarda a conclusão da operação
                System.out.println("Produto atualizado com sucesso no Firebase.");
            } catch (Exception e) {
                System.err.println("Erro ao atualizar produto no Firebase.");
                e.printStackTrace();
            }
        });
    }

    public void addFirebaseDataChangeListener(FirebaseDataChangeListener listener) {
        listeners.add(listener);
    }

    private void notifyDataChange(List<Produto> produtos) {
        for (FirebaseDataChangeListener listener : listeners) {
            listener.onDataChange(produtos);
        }
    }

    public void fetchProdutosFromFirebase() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("produtos");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Produto> fetchedProdutos = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Produto produto = child.getValue(Produto.class);
                    if (produto != null) {
                        fetchedProdutos.add(produto);
                        mapProdutoSku(produto.getSku(), child.getKey());
                    }
                }
                produtos = fetchedProdutos;
                notifyDataChange(produtos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Erro ao buscar produtos: " + databaseError.getMessage());
            }
        };

        ref.addValueEventListener(listener);
    }

    public interface FirebaseDataChangeListener {
        void onDataChange(List<Produto> produtos);
    }
}
