package br.insper.produto.produto;

public class Produto {
    private String id;
    private String nome;
    private Float preco;
    private Integer estoque;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public Integer getEstoque() { return estoque; }

    public void setEstoque(Integer estoque) { this.estoque = estoque; }

    public Float getPreco() { return preco; }

    public void setPreco(Float preco) { this.preco = preco; }

    @Override
    public boolean equals (Object obj) {
        return this.nome.equals(((Produto) obj).getNome());
    }
}
