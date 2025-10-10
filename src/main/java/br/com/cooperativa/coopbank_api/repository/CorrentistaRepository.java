package br.com.cooperativa.coopbank_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.cooperativa.coopbank_api.model.Correntista;

public interface CorrentistaRepository extends JpaRepository<Correntista, Long> {

}
