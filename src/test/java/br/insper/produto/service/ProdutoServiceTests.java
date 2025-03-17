package br.insper.produto.service;

import br.insper.produto.produto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTests {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    void test_saveProdutoSuccesfully() {

        Produto produto = new Produto();
        produto.setNome("Teste");
        produto.setPreco(23.00f);
        produto.setEstoque(22);

        CadastraProdutoDTO postDTO = new CadastraProdutoDTO("Teste", 23.00f, 22);

        Mockito.when(produtoRepository.save(produto)).thenReturn(produto);

         RetornarProdutoDTO getDTO = produtoService.cadastrarProduto(postDTO);

        Assertions.assertEquals("Teste", getDTO.nome());
        Assertions.assertEquals(23, getDTO.estoque());
        Assertions.assertEquals(22, getDTO.preco());
    }
}
