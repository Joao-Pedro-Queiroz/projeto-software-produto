package br.insper.produto.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RetornarProdutoDTO cadastrarProduto(@RequestBody CadastraProdutoDTO produto) {
        return produtoService.cadastrarProduto(produto);
    }

    @GetMapping
    public Page<Produto> listarProdutos(@RequestParam(required = false) String nome
            , Pageable pageable) {
        return produtoService.listarProdutos(nome, pageable);
    }

    @GetMapping("/{id}")
    public Produto buscarProduto(@PathVariable String id) {
        return produtoService.buscarProduto(id);
    }

    @PutMapping("/{id}")
    public RetornarProdutoDTO diminuirEstoqueProduto(@PathVariable String id) {
        return produtoService.diminuirEstoqueProduto(id);
    }
}
