package br.com.tutum.portfolio.pdvapi.controller;

import br.com.tutum.portfolio.pdvapi.model.Venda;
import br.com.tutum.portfolio.pdvapi.service.VendaService;
import br.com.tutum.portfolio.pdvapi.dto.AdicionarItemDTO;
import br.com.tutum.portfolio.pdvapi.dto.FinalizarVendaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {
    
    @Autowired
    private VendaService vendaService;

    // 1 Passo: Abrir uma nova venda(POST /api/vendas/)
    @PostMapping
    public ResponseEntity<Venda> abrirVenda() {
        // Spring pega o usuário que está logado no momento
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Venda novaVenda = vendaService.abrirVenda(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaVenda);
    }
    // 2 Passo: Adicionar o item (POST /api/vendas/{id}/itens)
    @PostMapping("/{id}/itens")
    public ResponseEntity<Venda> adicionarItem(@PathVariable Long id, @RequestBody AdicionarItemDTO dto) {
        Venda vendaAtualizada = vendaService.adicionarItem(id, dto);
        return ResponseEntity.ok(vendaAtualizada);
    }
    // 3 Passo: Remover o item, apenas o Supervisor poderá fazer isso
    @DeleteMapping("/{idVenda}/itens/{idItem}")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public ResponseEntity<Venda> removerItem(@PathVariable Long idVenda, @PathVariable Long idItem) {
        Venda vendaAtualizada = vendaService.removerItem(idVenda, idItem);
        return ResponseEntity.ok(vendaAtualizada);
    }
    // 4 Passo: Finalizar a venda
    @PostMapping("/{id}/finalizar")
    public ResponseEntity<Venda> finalizarVenda(@PathVariable Long id, @RequestBody FinalizarVendaDTO dto) {
        Venda vendaFinalizada = vendaService.finalizarVenda(id, dto);
        return ResponseEntity.ok(vendaFinalizada);
    }
}
