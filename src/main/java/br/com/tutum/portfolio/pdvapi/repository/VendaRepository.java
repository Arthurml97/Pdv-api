package br.com.tutum.portfolio.pdvapi.repository;

import br.com.tutum.portfolio.pdvapi.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    
}
