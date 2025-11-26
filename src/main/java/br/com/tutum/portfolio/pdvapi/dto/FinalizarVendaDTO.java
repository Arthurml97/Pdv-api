package br.com.tutum.portfolio.pdvapi.dto;

import br.com.tutum.portfolio.pdvapi.model.FormaPagamento;
import java.util.List;

public class FinalizarVendaDTO {

    private String cpfCliente;

    private List<PagamentoInput> pagamentos;

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public List<PagamentoInput> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<PagamentoInput> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public static class PagamentoInput {
        private FormaPagamento metodo;
        private Double valor;

        public FormaPagamento getMetodo() {
            return metodo;
        }

        public void setMetodo(FormaPagamento metodo) {
            this.metodo = metodo;
        }

        public Double getValor() {
            return valor;
        }

        public void setValor(Double valor) {
            this.valor = valor;
        }
    }
}