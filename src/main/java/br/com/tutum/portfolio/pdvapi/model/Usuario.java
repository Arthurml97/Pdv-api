package br.com.tutum.portfolio.pdvapi.model;

import jakarta.persistence.*; // Função para importar tudo do Jakarta.persistence

@Entity
@Table(name = "usuario") // Nome da tabela dentro do banco
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // Onde será o login, supervisor ou atendente

    @Column(nullable = false)
    private String password; // Senha criptografada(em hashed)
    
    // Savalmento de um Enum dentro do banco
    // 1 Passo: @Enumerated: Avisa ao Hibernate que este campo é um Enum
    // 2 Passo: EnumType.STRING: Manda o Hibernate salvar o texto do Enum no banco
    // Como por exemplo, ROLE_SUPERVISOR. Caso não use, ele salvaria um número, o que torna péssimo para se ler
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;  // O Enum em si

    // Getters e Setters
    
    public Usuario() {
        // Construtor vazio
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
