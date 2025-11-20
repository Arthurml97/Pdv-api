package br.com.tutum.portfolio.pdvapi.controller;

import br.com.tutum.portfolio.pdvapi.model.Usuario;
import br.com.tutum.portfolio.pdvapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios") // URL base para usuarios
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint para Cadastrar um novo usuário
     * URL: POST http://localhost:8080/api/usuarios
     * Este endpoint será usado para cadastrar Supervisores e Atendentes
     */
    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        // O Service já faz a validação e a criptografia da senha
        Usuario novoUsuario = usuarioService.cadastrarUsuario(usuario);
        
        //Apagar a senha antes de devolver o objeto
        novoUsuario.setPassword(null);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }
    // Espaço para Listar usuarios para ser preenchida futuramente
}
