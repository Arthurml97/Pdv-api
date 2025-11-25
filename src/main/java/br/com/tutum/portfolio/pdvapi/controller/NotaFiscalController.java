package br.com.tutum.portfolio.pdvapi.controller;

import br.com.tutum.portfolio.pdvapi.dto.NotaFiscalDTO;
import br.com.tutum.portfolio.pdvapi.service.NotaFiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fiscal")
public class NotaFiscalController {

    @Autowired
    private NotaFiscalService notaFiscalService;

    // GET /api/fiscal/{idVenda}
    @GetMapping("/{idVenda}")
    public ResponseEntity<NotaFiscalDTO> emitirNota(@PathVariable Long idVenda) {
        NotaFiscalDTO nota = notaFiscalService.emitirNota(idVenda);
        return ResponseEntity.ok(nota);
    }
}
