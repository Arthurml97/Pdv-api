package br.com.tutum.portfolio.pdvapi.repository;

import br.com.tutum.portfolio.pdvapi.model.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
    
}
