package br.com.tutum.portfolio.pdvapi.model;

public enum StatusVenda {
    ABERTA, // Adicionando itens ao sistema
    FINALIZADA, // A transação foi paga e concluida
    CANCELADA // Quando algum item for computado errado ou desistencia do cliente
}
