package br.com.tutum.portfolio.pdvapi.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class NotaFiscalDTO {

    // Dados da Loja
    private String nomeLoja = "Supermercado Tutum";
    private String cnpj = "12.345.678/0001-99";

    // Dados da Venda
    private Long numeroVenda;
    private LocalDateTime dataHora;
    private String chaveAcessoFake; // Simulação de Chave de Acesso da Receita Federal

    // Cliente
    private String cpfCliente;
    private String nomeCliente;

    // Itens e Valores
    private List<Map<String, Object>> itens; // Lista simplificada para impressão
    private Double valorTotal;
    private Double impostosEstimados;
    private List<Map<String, Object>> pagamentos;

    // Getters e Setters
    public String getNomeLoja() {
        return nomeLoja;
    }

    public void setNomeLoja(String nomeLoja) {
        this.nomeLoja = nomeLoja;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Long getNumeroVenda() {
        return numeroVenda;
    }

    public void setNumeroVenda(Long numeroVenda) {
        this.numeroVenda = numeroVenda;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getChaveAcessoFake() {
        return chaveAcessoFake;
    }

    public void setChaveAcessoFake(String chaveAcessoFake) {
        this.chaveAcessoFake = chaveAcessoFake;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public List<Map<String, Object>> getItens() {
        return itens;
    }

    public void setItens(List<Map<String, Object>> itens) {
        this.itens = itens;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getImpostosEstimados() {
        return impostosEstimados;
    }

    public void setImpostosEstimados(Double impostosEstimados) {
        this.impostosEstimados = impostosEstimados;
    }

    public List<Map<String, Object>> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Map<String, Object>> pagamentos) {
        this.pagamentos = pagamentos;
    }
}
