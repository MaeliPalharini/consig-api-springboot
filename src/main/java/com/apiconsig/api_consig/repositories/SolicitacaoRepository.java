package com.apiconsig.api_consig.repositories;

import com.apiconsig.api_consig.model.SolicitacaoAprovacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SolicitacaoRepository extends JpaRepository<SolicitacaoAprovacao, Long> {
    Optional<SolicitacaoAprovacao> findByCpf(String cpf);
}
