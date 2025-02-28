package com.apiconsig.api_consig.repositories;

import com.apiconsig.api_consig.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
  Cliente findByCpf(String cpf);
}
