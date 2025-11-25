package br.com.tutum.portfolio.pdvapi.service;

import br.com.tutum.portfolio.pdvapi.dto.NotaFiscalDTO;
import br.com.tutum.portfolio.pdvapi.model.StatusVenda;
import br.com.tutum.portfolio.pdvapi.model.Venda;
import br.com.tutum.portfolio.pdvapi.repository.VendaRepository;
import br.com.tutum.portfolio.pdvapi.service.exception.RegraDeNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class NotaFiscalService {

    @Autowired
    private VendaRepository vendaRepository;

    public NotaFiscalDTO emitirNota(Long idVenda) {
        // 1 Passo: Buscar a venda
        Venda venda = vendaRepository.findById(idVenda)
                .orElseThrow(() -> new RegraDeNegocioException("Venda não encontrada"));

        // 2 Passo: Só emitir nota fiscal de Venda finalizada
        if (venda.getStatus() != StatusVenda.FINALIZADA) {
            throw new RegraDeNegocioException("Não é possível emitir nota fiscal de uma venda em aberto ou cancelada");
        }

        // 3 Passo: Montar o DTO
        NotaFiscalDTO nota = new NotaFiscalDTO();
        nota.setNumeroVenda(venda.getId());
        nota.setDataHora(venda.getDataHora());
        // Gerar um código aleatório para simular uma chave da receita
        nota.setChaveAcessoFake(UUID.randomUUID().toString().replace("-", "").toUpperCase());

        // 4 Passo: Dados do cliente, caso possua
        if (venda.getCliente() != null) {
            nota.setCpfCliente(venda.getCliente().getCpf());
            nota.setNomeCliente(venda.getCliente().getNome());
        } else {
            nota.setNomeCliente("Consumidor não identificado");
        }

        // 5 Passo: Impostos
        nota.setValorTotal(venda.getValorTotal());
        nota.setImpostosEstimados(venda.getValorTotal() * 0.30);

        // 5.1 Passo: Converter os itens para um formato simples de leitura
        List<Map<String, Object>> itensFormatados = new ArrayList<>();
        venda.getItens().forEach(item -> {
            Map<String, Object> linha = new HashMap<>();
            linha.put("produto", item.getProduto().getDescricao());
            linha.put("quantidade", item.getQuantidade());
            linha.put("preço", item.getPrecoUnitario());
            linha.put("total", item.getSubTotal());
            itensFormatados.add(linha);
        });
        nota.setItens(itensFormatados);

        return nota;
    }
}