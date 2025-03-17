package br.insper.produto.service;

import br.insper.produto.produto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTests {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    void test_saveProdutoSuccesfully() {

        Produto produto = new Produto();
        produto.setId("123");
        produto.setNome("Teste");
        produto.setPreco(23.00f);
        produto.setEstoque(22);

        CadastraProdutoDTO postDTO = new CadastraProdutoDTO("Teste", 23.00f, 22);

        Mockito.when(produtoRepository.save(produto)).thenReturn(produto);

        RetornarProdutoDTO getDTO = produtoService.cadastrarProduto(postDTO);

        Assertions.assertEquals("123", getDTO.id());
        Assertions.assertEquals("Teste", getDTO.nome());
        Assertions.assertEquals(23, getDTO.preco());
        Assertions.assertEquals(22, getDTO.estoque());
    }

    @Test
    void test_findAllProdutosWhenProdutosIsEmpty() {

        Mockito.when(produtoRepository.findAll()).thenReturn(new ArrayList<>());

        List<Produto> produtos = produtoService.listarProdutos(null);

        Assertions.assertEquals(0, produtos.size());
    }

    @Test
    void test_findByNomeProdutosWhenProdutosIsEmpty() {
        String nome = "Teste";

        Mockito.when(produtoRepository.findByNome(nome)).thenReturn(new ArrayList<>());

        List<Produto> produtos = produtoService.listarProdutos(nome);

        Assertions.assertEquals(0, produtos.size());
    }

    @Test
    void test_findProdutoByIdSuccesfully() {

        Produto produto = new Produto();
        produto.setId("123");
        produto.setNome("Teste");
        produto.setPreco(23.00f);
        produto.setEstoque(22);

        Mockito.when(produtoRepository.findById(produto.getId())).thenReturn(Optional.of(produto));

        Produto produtoRetornado = produtoService.buscarProduto(produto.getId());

        Assertions.assertEquals("123", produtoRetornado.getId());
        Assertions.assertEquals("Teste", produtoRetornado.getNome());
        Assertions.assertEquals(23, produtoRetornado.getPreco());
        Assertions.assertEquals(22, produtoRetornado.getEstoque());
    }

    @Test
    void test_findProdutoByIdErrorNotFound() {

        String produtoId = "123";

        Mockito.when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> produtoService.buscarProduto(produtoId));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void test_diminuiEstoqueProdutoSuccesfully() {

        Produto produto = new Produto();
        produto.setId("123");
        produto.setNome("Teste");
        produto.setPreco(23.00f);
        produto.setEstoque(22);

        Mockito.when(produtoRepository.findById(produto.getId())).thenReturn(Optional.of(produto));

        Produto produtoComEstoqueAtualizado = new Produto();
        produtoComEstoqueAtualizado.setId("123");
        produtoComEstoqueAtualizado.setNome("Teste");
        produtoComEstoqueAtualizado.setPreco(23.00f);
        produtoComEstoqueAtualizado.setEstoque(21);

        Mockito.when(produtoRepository.save(produtoComEstoqueAtualizado)).thenReturn(produtoComEstoqueAtualizado);

        RetornarProdutoDTO getDTO = produtoService.diminuirEstoqueProduto(produto.getId());

        Assertions.assertEquals("123", getDTO.id());
        Assertions.assertEquals("Teste", getDTO.nome());
        Assertions.assertEquals(23, getDTO.preco());
        Assertions.assertEquals(21, getDTO.estoque());

        Mockito.verify(produtoRepository).save(Mockito.argThat(p -> p.getEstoque() == 21));
    }
}
