package br.com.adams.brejaapi.repository;

import br.com.adams.brejaapi.model.Cerveja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CervejaRepository extends JpaRepository<Cerveja, Long> {}
