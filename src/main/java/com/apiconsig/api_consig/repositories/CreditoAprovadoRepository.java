package com.apiconsig.api_consig.repositories;

import com.apiconsig.api_consig.model.CreditoAprovado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditoAprovadoRepository extends JpaRepository<CreditoAprovado, Long> {
}
