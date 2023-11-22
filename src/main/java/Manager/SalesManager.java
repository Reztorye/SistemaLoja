package Manager;

import com.google.firebase.database.*;
import java.util.*;
import java.util.function.Consumer;

import entities.Cliente;
import entities.ItemVenda;
import entities.Produto;
import entities.Venda;

public class SalesManager {
    private List<Venda> vendas;

    public SalesManager() {
        this.vendas = new ArrayList<>();
    }

    public List<Venda> getVendas() {
        return this.vendas;
    }

    public Venda adicionarVenda(/* parâmetros para criar uma Venda */) {
        // Cria uma nova Venda com os parâmetros fornecidos e adiciona à lista
        Venda venda = new Venda(/* parâmetros */);
        vendas.add(venda);
        return venda;
    }

    public void atualizarVenda(Venda venda) {
        // Atualiza uma Venda existente
        for (int i = 0; i < vendas.size(); i++) {
            if (vendas.get(i).getIdLocal() == venda.getIdLocal()) { // Supondo que Venda tem um identificador único
                vendas.set(i, venda);
                return;
            }
        }
    }

    public void removerVenda(int vendaId) {
        // Remove uma Venda com base no ID
        vendas.removeIf(v -> v.getIdLocal() == vendaId);
    }

    public Venda buscarVendaPorId(int vendaId) {
        // Busca uma Venda por ID
        return vendas.stream()
                .filter(venda -> venda.getIdLocal() == vendaId)
                .findFirst()
                .orElse(null);
    }

    public void fetchVendasFromFirebase(Consumer<List<Venda>> callback) {
        // Busca vendas do Firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("vendas");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Venda> fetchedVendas = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Venda venda = snapshot.getValue(Venda.class);
                    if (venda != null) {
                        venda.setFirebaseId(snapshot.getKey());
                        fetchedVendas.add(venda);
                    }
                }
                callback.accept(fetchedVendas);
                vendas.addAll(fetchedVendas);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Erro ao buscar vendas: " + databaseError.getMessage());
                callback.accept(null);
            }
        });
    }

    public void mostrarVendasRealizadas() {
        System.out.println("Vendas Realizadas:");
        for (Venda venda : vendas) {
            System.out.println("ID da Venda: " + venda.getIdLocal());
            System.out.println("Cliente: " + venda.getCliente().getNome());
            System.out.println("Data: " + venda.getData());
            System.out.println("Produtos:");

            for (ItemVenda itemVenda : venda.getProdutosVendidos()) {
                Produto produto = itemVenda.getProduto();
                int quantidade = itemVenda.getQuantidade();
                System.out.println("  - " + produto.getNome() + ", Quantidade: " + quantidade);
            }

            System.out.println("-----------------------");
        }
    }

    public void listarVendas() {
        for (Venda venda : vendas) {
            System.out.println(venda);
        }
    }

    public void realizarVenda(Cliente cliente, List<ItemVenda> itensVenda) {
        boolean estoqueSuficiente = true;

        for (ItemVenda itemVenda : itensVenda) {
            Produto produto = itemVenda.getProduto();
            int quantidade = itemVenda.getQuantidade();

            if (produto.getEstoqueDisponivel() < quantidade) {
                estoqueSuficiente = false;
                break;
            }

        }

        if (estoqueSuficiente) {
            for (ItemVenda itemVenda : itensVenda) {
                Produto produto = itemVenda.getProduto();
                int quantidade = itemVenda.getQuantidade();
                produto.setEstoqueDisponivel(produto.getEstoqueDisponivel() - quantidade);
            }

            Venda venda = new Venda(cliente, itensVenda, new Date());

            vendas.add(venda);

            System.out.println("Venda realizada com sucesso!");
        } else {
            System.out.println("Quantidade insuficiente em estoque para um ou mais produtos.");
        }
    }
}
