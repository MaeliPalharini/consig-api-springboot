package com.apiconsig.api_consig.repositories;

import com.apiconsig.api_consig.model.SolicitacaoAprovacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SolicitacaoAprovacaoRepository extends JpaRepository<SolicitacaoAprovacao, Long> {
    Optional<SolicitacaoAprovacao> findByCpf(String cpf);
}

