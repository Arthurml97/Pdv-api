package br.com.tutum.portfolio.pdvapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Avisa ao Hibernate que essa classe representa uma tabela no banco de dados
@Entity
// Define o nome da tabela no banco de dados se omitirr, o nome da tabela será o mesmo da classe
@Table(name = "produtos")
public class Produto {
    
    // Define o campo id como chave primária
    @Id
    // GeneratedValue indica que o valor do campo será gerado automaticamente pelo banco de dados
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Permite personalizar a coluna no banco de dados
    @Column(name = "codigo_de_barras", nullable = false, unique = true)
    private String codigoDeBarras;

    @Column(nullable = false)
    private String descricao;

    @Column(name = "valor unitario", nullable = false)
    private Double valorUnitario;

    // --- Getters e Setters ---
    // Hibernate necessita de um construtor padrão vazio e getters e setters para funcionar corretamente

    public Produto() {
        // Construtor padrão vazio
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

}
