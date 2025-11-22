package br.com.tutum.portfolio.pdvapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

// import java.math.BigDecimal; 
@Entity
@Table(name = "itens_venda")
public class ItemVenda {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    // Qual produto foi vendido?
    @ManyToOne // Vários itens podem ser do mesmo produto (em vendas diferentes)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    // Quando for usar dinheiro devo usar BigDecimal por ser mais preciso que o Double
    // Para simplificar neste momento estarei usando o Double
    @Column(name = "preco_unitario", nullable = false)
    private Double precoUnitario;

    // A qual venda este item pertence?
    @ManyToOne
    @JoinColumn(name = "venda_id", nullable = false)
    @JsonIgnore // Importante para evitar Loop Infinito ao transformar em JSON
    private Venda venda;

    // Construtor vazio, Getters and Setters

    public ItemVenda() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Double getSubTotal() {
        return quantidade * precoUnitario;
    }

}
