package br.insper.produto.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Page<Produto> listarProdutos(String nome, Pageable pageable) {
        if (nome != null) {
            return produtoRepository.findByNome(nome, pageable);
        }
        return produtoRepository.findAll(pageable);
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
