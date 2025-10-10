package br.com.cooperativa.coopbank_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.cooperativa.coopbank_api.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

}
