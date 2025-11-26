package br.com.tutum.portfolio.pdvapi.controller;

import br.com.tutum.portfolio.pdvapi.model.Produto;
import br.com.tutum.portfolio.pdvapi.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que essa classe é um controlador REST do Spring
@RequestMapping("/api/produtos") // Mapeia as requisições para /produtos para esse controlador
@CrossOrigin(origins = "*")
public class ProdutoController {

    // Autowired injeta automaticamente a dependência do ProdutoService
    @Autowired
    private ProdutoService produtoService;

    // --- Endpoints REST para gerenciar produtos (URLS) ---

    /**
     * Endpoint para listar todos os produtos.
     *
     * @return Uma ResponseEntity contendo a lista de produtos e o status HTTP 200
     *         (OK).
     */
    @PostMapping
    // Mapeia requisições HTTP POST para esse método
    // O @RequestBody indica que o corpo da requisição será convertido para um
    // objeto Produto
    // Retorna um ResponseEntity com o produto criado e o status HTTP 201 (Created)
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        Produto produtoSalvo = produtoService.criarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @GetMapping
    // Mapeia requisições HTTP GET para esse método
    // Retorna um ResponseEntity com a lista de produtos e o status HTTP 200 (OK)
    // O método listarTodos chama o serviço para obter todos os produtos
    public ResponseEntity<List<Produto>> listarTodos() {
        List<Produto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }
    // Outros endpoints como buscar por ID, atualizar, deletar podem ser adicionados
    // aqui
    // PUT /api/produtos/{id}, DELETE /api/produtos/{id}, GET /api/produtos/{id},
    // etc
}