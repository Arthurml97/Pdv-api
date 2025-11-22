package br.com.tutum.portfolio.pdvapi.dto;

public class AdicionarItemDTO {
    private String codigoDeBarras;
    private Integer quantidade;

    // Getter e Setters
    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }
    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }
    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
