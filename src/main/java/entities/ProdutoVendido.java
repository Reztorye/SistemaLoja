package entities;

public class ProdutoVendido {
    private Produto produto;
    private int quantidade;

    public ProdutoVendido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public void incrementarQuantidade(int quantidade) {
        this.quantidade += quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidadeVendida() {
        return quantidade;
    }
}
