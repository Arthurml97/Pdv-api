package br.com.tutum.portfolio.pdvapi.model;

/**
 * Define as funções, no caso níveis de acesso que um usuário pode ter
 * Usamos enum para garantir que os valores sempre sejam estes
 */
public enum Role {
    ROLE_SUPERVISOR, // Funcionário com todos os privilégios
    ROLE_ATENDENTE, // Funcionário de caixa, com acesso restringido
}
