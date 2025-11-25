package br.com.tutum.portfolio.pdvapi.dto;

import br.com.tutum.portfolio.pdvapi.model.FormaPagamento;

public class FinalizarVendaDTO {
    private FormaPagamento formaPagamento;
    private String cpfCliente;

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }
}
