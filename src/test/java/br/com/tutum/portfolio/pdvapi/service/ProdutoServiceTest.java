package br.com.tutum.portfolio.pdvapi.service;

import br.com.tutum.portfolio.pdvapi.model.Produto;
import br.com.tutum.portfolio.pdvapi.repository.ProdutoRepository;
import br.com.tutum.portfolio.pdvapi.service.exception.RegraDeNegocioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest { // Habilitar o Mockito

    @Mock // 1 Passo: Criar uma espécie de Duble do ProdutoRepository
    private ProdutoRepository produtoRepository;

    @InjectMocks // 2 Passo: Injetar o Duble dentro do Service que será testado
    private ProdutoService produtoService;

    @Test
    @DisplayName("Deve criar um produto com sucesso quando o código de barras não existe")
    void criarProduto_CenarioSucesso() {
        // Arrange (preparação)
        Produto novoProduto = new Produto();
        novoProduto.setCodigoDeBarras("12345");
        novoProduto.setValorUnitario(10.0);

        // Ensinar o Mockito que quando alguém procurar '12345' ele deve retornar um
        // Optional vazio
        when(produtoRepository.findByCodigoDeBarras("12345")).thenReturn(Optional.empty());

        // Ensinar o Mockito que quando mandar salvar, retornar o próprio produto
        when(produtoRepository.save(any(Produto.class))).thenReturn(novoProduto);

        // Act (ação)
        Produto produtoSalvo = produtoService.criarProduto(novoProduto);

        // Assert (verificação)
        assertEquals(novoProduto, produtoSalvo); // Verificar se não é nulo
        assertEquals("12345", produtoSalvo.getCodigoDeBarras()); // Verificar se o código de barras é o mesmo

        // Verificar se o repositório foi chamado exatamente uma vez
        verify(produtoRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar um produto duplicado")
    void criarProduto_CenarioDuplicado() {
        // Arrange (preparação)
        Produto produtoDuplicado = new Produto();
        produtoDuplicado.setCodigoDeBarras("789");

        // Ensinar o Mockito quando buscar "789", dizer que já existe um produto
        when(produtoRepository.findByCodigoDeBarras("789"))
                .thenReturn(Optional.of(new Produto()));

        // Act e Assert (ação e verificação simultaneos)
        RegraDeNegocioException erro = assertThrows(RegraDeNegocioException.class, () -> {
            produtoService.criarProduto(produtoDuplicado);
        });

        // Verifica a messagem de erro
        assertEquals("Já existe um produto cadastrado com esse código de barras.", erro.getMessage());

        // Verifica que o método save nunca foi chamado
        verify(produtoRepository, never()).save(any());
    }
}
