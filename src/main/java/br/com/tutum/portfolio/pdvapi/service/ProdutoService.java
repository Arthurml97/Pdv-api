package br.com.tutum.portfolio.pdvapi.service;

import br.com.tutum.portfolio.pdvapi.model.Produto;
import br.com.tutum.portfolio.pdvapi.repository.ProdutoRepository;
import br.com.tutum.portfolio.pdvapi.service.exception.RegraDeNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; 

@Service // Indica que essa classe é um serviço do Spring e o Srping deve gerenciá-la
public class ProdutoService {
    
    // Autowired injeta automaticamente a dependência do ProdutoRepository
    // Em vez de criar uma instância manualmente, o Spring cuida disso
    // Isso promove o princípio de Inversão de Controle (IoC) e facilita o teste e a manutenção do código
    @Autowired
    private ProdutoRepository produtoRepository;

    // --- Métodos de serviço para gerenciar produtos --- A lógica de negócio fica aqui

    /**
        * Busca todos os produtos cadastrados no sistema.
        *
        * @return Uma lista de todos os produtos.
    */
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    } 

    /**
        * Busca um produto pelo seu ID.
        *
        * @param id O ID do produto a ser buscado.
        * @return Um Optional contendo o produto se encontrado, ou vazio se não encontrado.
    */

    public Optional<Produto> buscarPorCodigoDeBarras(String codigoDeBarras) {
        return produtoRepository.findByCodigoDeBarras(codigoDeBarras);
    }

    /**
        * Cadastra um novo produto no sistema.
        *
        * @param produto O produto a ser cadastrado.
        * @return O produto cadastrado.
        * @throws RegraDeNegocioException se já existir um produto com o mesmo código de barras.
    */
    public Produto criarProduto(Produto produto) {
        // Verifica se já existe um produto com o mesmo código de barras
        Optional<Produto> produtoExistente = produtoRepository.findByCodigoDeBarras(produto.getCodigoDeBarras());
        // Se existir, lança uma exceção customizada de regra de negócio que eu criei
        if (produtoExistente.isPresent()) {
            throw new RegraDeNegocioException("Já existe um produto cadastrado com esse código de barras.");
        }
        return produtoRepository.save(produto);
    }

    // Outros métodos de serviço como atualizar, deletar, etc., podem ser adicionados aqui
}
