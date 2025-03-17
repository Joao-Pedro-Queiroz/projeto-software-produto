package br.insper.produto.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public List<Produto> listarProdutos(@RequestParam(required = false) String nome) {
        return produtoService.listarProdutos(nome);
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
