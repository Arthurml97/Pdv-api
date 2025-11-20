package br.com.tutum.portfolio.pdvapi.repository;

import br.com.tutum.portfolio.pdvapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // O Spring vai criar o SQL automaticamente: SELECT * FROM usuarios WHERE username = ?
    Optional<Usuario> findByUsername(String username);
}
