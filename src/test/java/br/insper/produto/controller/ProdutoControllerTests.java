package br.insper.produto.controller;

import br.insper.produto.produto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProdutoControllerTests {

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(produtoController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void test_PostUsuario() throws Exception {

        CadastraProdutoDTO postDTO = new CadastraProdutoDTO("Teste", 23.00f, 22);

        RetornarProdutoDTO getDTO = new RetornarProdutoDTO("123","Teste", 23.00f, 22);

        ObjectMapper objectMapper = new ObjectMapper();

        Mockito.when(produtoService.cadastrarProduto(postDTO)).thenReturn(getDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/produto")
                        .content(objectMapper.writeValueAsString(postDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(getDTO)));
    }

    @Test
    void test_GetProdutosSemFiltro() throws Exception {

        Produto produto = new Produto();
        produto.setId("123");
        produto.setNome("Teste");
        produto.setPreco(23.00f);
        produto.setEstoque(22);

        List<Produto> produtos = Arrays.asList(produto);

        ObjectMapper objectMapper = new ObjectMapper();

        Mockito.when(produtoService.listarProdutos(null))
                .thenReturn(produtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(produtos)));
    }

    @Test
    void test_GetProduto() throws Exception {

        Produto produto = new Produto();
        produto.setId("123");
        produto.setNome("Teste");
        produto.setPreco(23.00f);
        produto.setEstoque(22);

        ObjectMapper objectMapper = new ObjectMapper();

        Mockito.when(produtoService.buscarProduto(produto.getId()))
                .thenReturn(produto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/"+produto.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(produto)));
    }

    @Test
    void test_PutProdutoDiminuiEstoque() throws Exception {

        String idProduto = "123";

        RetornarProdutoDTO getDTO = new RetornarProdutoDTO(idProduto,"Teste", 23.00f, 22);

        ObjectMapper objectMapper = new ObjectMapper();

        Mockito.when(produtoService.diminuirEstoqueProduto(idProduto))
                .thenReturn(getDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/produto/"+idProduto))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(getDTO)));
    }
}
