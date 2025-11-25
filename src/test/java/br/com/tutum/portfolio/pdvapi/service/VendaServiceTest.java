package br.com.tutum.portfolio.pdvapi.service;

import br.com.tutum.portfolio.pdvapi.dto.AdicionarItemDTO;
import br.com.tutum.portfolio.pdvapi.model.Produto;
import br.com.tutum.portfolio.pdvapi.model.Venda;
import br.com.tutum.portfolio.pdvapi.repository.ProdutoRepository;
import br.com.tutum.portfolio.pdvapi.repository.VendaRepository;
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
class VendaServiceTest {

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private VendaService vendaService;

    @Test
    @DisplayName("Deve adicionar item e recalcular o total da venda corretamente")
    void adicionarItem_CenarioSucesso() {

        // 1 Passo: Criar uma venda falsa com o total 0
        Venda vendaExistente = new Venda();
        vendaExistente.setId(1L);
        vendaExistente.setValorTotal(0.0);

        // 2 Passo: Criar um produto falso que custa 10
        Produto produtoMock = new Produto();
        produtoMock.setId(10L);
        produtoMock.setValorUnitario(10.0);
        produtoMock.setCodigoDeBarras("789");

        // 3 Passo: Criar o DTO, Mock vai pedir duas unidades do produto
        AdicionarItemDTO dto = new AdicionarItemDTO();
        dto.setCodigoDeBarras("789");
        dto.setQuantidade(2);

        // Ensinando o Mock o comportamento

        // Quando buscarem a venda ID 1, retorne a nossa vendaExistente
        when(vendaRepository.findById(1L)).thenReturn(Optional.of(vendaExistente));

        // Quando buscarem o produto "789", retorne o produtoMock
        when(produtoRepository.findByCodigoDeBarras("789")).thenReturn(Optional.of(produtoMock));

        // Quando mandarem salvar a venda, retorne ela mesma (só para não dar erro)
        when(vendaRepository.save(any(Venda.class))).thenReturn(vendaExistente);

        // Act (ação)
        Venda vendaAtualizada = vendaService.adicionarItem(1L, dto);

        // Assert (verificação)
        assertEquals(20.0, vendaAtualizada.getValorTotal());

        // A lista de itens deve ter 1 item agora
        assertEquals(1, vendaAtualizada.getItens().size());

        // O item adicionando deve ter o preço congelado de 10
        assertEquals(10.0, vendaAtualizada.getItens().get(0).getPrecoUnitario());

        // Verifica se o save foi chamado
        verify(vendaRepository, times(1)).save(vendaExistente);
    }
}
