package entities;

public class ProdutoVendido {
    private Produto produto;
    private int quantidadeVendida;

    // Atualize o construtor para aceitar um Produto e a quantidade vendida
    public ProdutoVendido(Produto produto, int quantidadeVendida) {
        this.produto = produto;
        this.quantidadeVendida = quantidadeVendida;
    }

    public void incrementarQuantidade(int quantidade) {
        this.quantidadeVendida += quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }

}