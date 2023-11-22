package Manager;

import com.google.api.core.ApiFuture;
import com.google.firebase.database.*;

import java.awt.Component;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import entities.Produto;

public class ProdutoManager {
    private List<Produto> produtos;
    private Map<Integer, String> produtoSkuMap;
    private String skuMapKey;

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

    public Map<Integer, String> getProdutoSkuMap() {
        return produtoSkuMap;
    }

    public List<Produto> getProdutos() {
        return this.produtos;
    }

    public void removeProdutoSkuMapping(Integer sku) {
        produtoSkuMap.remove(sku);
    }

    public void mapProdutoSku(Integer sku, String firebaseId) {
        produtoSkuMap.put(sku, firebaseId);
    }

    public String getFirebaseIdBySKU(Integer sku) {
        return produtoSkuMap.get(sku);
    }

    public Produto adicionarProduto(Integer sku, String nome, String descricao, double precoCusto, double precoVenda,
            int estoqueDisponivel, String categoria, String fornecedor, Runnable onSuccess) {
        Produto produto = new Produto(sku, nome, descricao, precoCusto, precoVenda, estoqueDisponivel, categoria,
                fornecedor);
        produtos.add(produto);

        if (onSuccess != null) {
            onSuccess.run();
        }

        return produto;
    }

    public void atualizarProduto(Produto produto) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getSku() == produto.getSku()) {
                produtos.set(i, produto);
                deleteProductAfterAddition(produto.getSku(), null);
                return;
            }
        }
    }

    public void listarProdutos() {
        for (Produto produto : produtos) {
            System.out.println(produto);
        }
    }

    public void removerProduto(Integer sku) {
        boolean removido = produtos.removeIf(c -> c.getSku() == sku);
        if (removido) {
            System.out.println("Produto removido: SKU " + sku);

        } else {
            System.out.println("Produto não encontrado: SKU " + sku);
        }

    }

    public Produto buscarProdutoPorSku(int sku) {
        return produtos.stream()
                .filter(produto -> produto.getSku() == sku)
                .findFirst()
                .orElse(null);
    }

    public void buscarProdutosPorNomeFirebase(String nome, Consumer<List<Produto>> callback) {
        String nomeBusca = nome.toUpperCase();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("produtos");
        ref.orderByChild("nome").startAt(nomeBusca).endAt(nome + "\uf8ff")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Produto> produtosEncontrados = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Produto produto = snapshot.getValue(Produto.class);
                            produtosEncontrados.add(produto);
                        }
                        callback.accept(produtosEncontrados);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.err.println("Erro ao buscar produtos: " + databaseError.getMessage());
                        callback.accept(Collections.emptyList());
                    }
                });
    }

    public void atualizarProduto(Integer sku, String nome, String descricao, double precoCusto, double precoVenda,
            int estoqueDisponivel, String categoria, String fornecedor) {
        for (Produto produto : produtos) {
            if (produto.getSku() == sku) {
                produto.setNome(nome);
                produto.setDescricao(descricao);
                produto.setPrecoCusto(precoCusto);
                produto.setPrecoVenda(precoVenda);
                produto.setEstoqueDisponivel(estoqueDisponivel);
                produto.setCategoria(categoria);
                produto.setFornecedor(fornecedor);
                return;
            }
        }
    }

    public Produto obterProdutoDaLinha(int linha) {
        if (linha >= 0 && linha < produtos.size()) {
            return produtos.get(linha);
        }
        return null; // ou lançar uma exceção adequada
    }

    public Produto buscarProdutoPorFirebaseId(Integer produtoSku) {
        for (Produto produto : produtos) {
            if (produtoSku.toString().equals(getFirebaseIdBySKU(produto.getSku()))) {
                return produto;
            }
        }
        return null;
    }

    public void deleteProductAfterAddition(Integer sku, Component parentComponent) {
        String firebaseId = getFirebaseIdBySKU(sku);
        System.out.println("Deletando produto com SKU: " + sku);

        if (firebaseId != null) {
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("produtos");
            ApiFuture<Void> future = dbRef.child(firebaseId).removeValueAsync();

            CompletableFuture<Void> completableFuture = new CompletableFuture<>();

            future.addListener(() -> {
                try {
                    future.get(); // Aguarda a operação ser concluída

                    // Remova o SKU do mapa
                    removeProdutoSkuMapping(sku);

                    // Mensagem de sucesso na EDT
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(parentComponent, "Produto deletado com sucesso.", "Sucesso",
                                JOptionPane.INFORMATION_MESSAGE);
                    });

                    completableFuture.complete(null);
                } catch (Exception e) {
                    // Mensagem de erro na EDT
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(parentComponent,
                                "Falha ao deletar produto do Firebase: " + e.getMessage(), "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    });

                    e.printStackTrace(); // Adiciona a stack trace ao log de erro
                    completableFuture.completeExceptionally(e);
                }
            }, Executors.newSingleThreadExecutor());

            // Aguarda a conclusão da operação assíncrona
            try {
                completableFuture.join();
            } catch (Exception e) {
                // Log para identificar exceções durante o join
                e.printStackTrace();
            }
        } else {
            // Mensagem de erro na EDT
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(parentComponent, "SKU não encontrado.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            });
        }
    }

    public void fetchProdutosFromFirebase(Consumer<List<Produto>> callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("produtos");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Produto> fetchedProdutos = new ArrayList<>();

                produtoSkuMap.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Produto produto = snapshot.getValue(Produto.class);
                    if (produto != null && produto.getSku() != 0) {
                        produto.setFirebaseId(snapshot.getKey());
                        fetchedProdutos.add(produto);

                        produtoSkuMap.put(produto.getSku(), snapshot.getKey());
                        System.out.println("SKU: " + produto.getSku() + ", Firebase ID: " + snapshot.getKey());
                    }
                }
                callback.accept(fetchedProdutos);
                produtos.addAll(fetchedProdutos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Erro ao buscar produtos: " + databaseError.getMessage());
                callback.accept(null);
            }
        });
    }
}
