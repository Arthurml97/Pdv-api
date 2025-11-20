package br.com.tutum.portfolio.pdvapi.repository;

import br.com.tutum.portfolio.pdvapi.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository; // Fornece métodos JPA padrão para operações de banco de dados
import org.springframework.stereotype.Repository; // Indica que essa interface é um repositório Spring

import java.util.Optional;

@Repository // Indica que essa interface é um repositório Spring
// Extende JpaRepository para herdar métodos padrão de CRUD para a entidade Produto
// O primeiro parâmetro é a entidade gerenciada (Produto) e o segundo é o tipo do ID (Long)
// Com isso, não é necessário implementar métodos básicos como salvar, buscar, atualizar e deletar
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Query method para buscar um produto pelo código de barras
    // O Spring Data JPA implementa automaticamente esse método com base na convenção de nomenclatura
    // Retorna um Optional contendo o Produto se encontrado, ou vazio se não encontrado
    // Exemplo de uso: produtoRepository.findByCodigoDeBarras("1234567890123");
    // O Spring Data JPA interpreta o nome do método e gera a consulta SQL correspondente
    // Isso elimina a necessidade de escrever consultas SQL manualmente para operações simples como essa
    // O uso de Optional ajuda a evitar NullPointerExceptions ao lidar com resultados que podem não existir
    Optional<Produto> findByCodigoDeBarras(String codigoDeBarras);
} 