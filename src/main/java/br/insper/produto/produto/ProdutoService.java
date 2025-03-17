package br.insper.produto.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public RetornarProdutoDTO cadastrarProduto(CadastraProdutoDTO dto) {

        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setPreco(dto.preco());
        produto.setEstoque(dto.estoque());

        produto = produtoRepository.save(produto);
        return new RetornarProdutoDTO(produto.getId(), produto.getNome(), produto.getPreco(), produto.getEstoque());
    }

    public List<Produto> listarProdutos(String nome) {
        if (nome != null) {
            return produtoRepository.findByNome(nome);
        }
        return produtoRepository.findAll();
    }

    public Produto buscarProduto(String id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public RetornarProdutoDTO diminuirEstoqueProduto(String id) {
        Produto produto = buscarProduto(id);
        produto.setEstoque(produto.getEstoque() - 1);

        produto = produtoRepository.save(produto);
        return new RetornarProdutoDTO(produto.getId(), produto.getNome(), produto.getPreco(), produto.getEstoque());
    }

}
