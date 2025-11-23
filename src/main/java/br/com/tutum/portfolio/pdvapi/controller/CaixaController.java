package br.com.tutum.portfolio.pdvapi.controller;

import br.com.tutum.portfolio.pdvapi.dto.AbrirCaixaDTO;
import br.com.tutum.portfolio.pdvapi.dto.SangriaDTO;
import br.com.tutum.portfolio.pdvapi.model.Caixa;
import br.com.tutum.portfolio.pdvapi.service.CaixaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/caixas")
public class CaixaController {

    @Autowired
    private CaixaService caixaService;

    // POST /api/caixas/abrir
    @PostMapping("/abrir")
    public ResponseEntity<Caixa> abrirCaixa(@RequestBody AbrirCaixaDTO dto) {
        // 1 Passo: Obter o usuario autenticado (Legado)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Caixa caixaAberto = caixaService.abrirCaixa(username, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(caixaAberto);
    }

    // POST /api/caixas/fechar
    @PostMapping("/fechar")
    public ResponseEntity<Caixa> fecharCaixa() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Caixa caixaFechado = caixaService.fecharCaixa(username);
        return ResponseEntity.ok(caixaFechado);
    }

    // POST /api/caixas/sangria
    @PostMapping("/sangria")
    public ResponseEntity<Caixa> realizarSangria(@RequestBody SangriaDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Caixa caixaAtualizado = caixaService.realizarSangria(username, dto);
        return ResponseEntity.ok(caixaAtualizado);
    }
}
