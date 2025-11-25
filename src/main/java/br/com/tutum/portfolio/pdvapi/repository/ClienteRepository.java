package br.com.tutum.portfolio.pdvapi.repository;

import br.com.tutum.portfolio.pdvapi.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // O Spring cria o SQL automaticamente: SELECT * FROM clientes WHERE cpf = ?
    Optional<Cliente> findByCpf(String cpf);
}
