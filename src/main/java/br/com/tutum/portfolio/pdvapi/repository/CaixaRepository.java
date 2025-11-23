package br.com.tutum.portfolio.pdvapi.repository;

import br.com.tutum.portfolio.pdvapi.model.Caixa;
import br.com.tutum.portfolio.pdvapi.model.StatusCaixa;
import br.com.tutum.portfolio.pdvapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaixaRepository extends JpaRepository<Caixa, Long> {

    // Busca se existe algum caixa com status ABERTO para este usuario
    Optional<Caixa> findByUsuarioAndStatus(Usuario usuario, StatusCaixa status);
}
