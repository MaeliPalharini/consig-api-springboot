package com.apiconsig.api_consig.repositories;

import com.apiconsig.api_consig.model.SolicitacaoEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SolicitacaoEmprestimoRepository extends JpaRepository<SolicitacaoEmprestimo, Long> {
    Optional<SolicitacaoEmprestimo> findByCpf(String cpf);

    void deleteByCpf(String cpf);
}
