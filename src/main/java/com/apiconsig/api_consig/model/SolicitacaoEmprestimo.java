package com.apiconsig.api_consig.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacaoEmprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotNull(message = "O campo 'valorConsignado' é obrigatório")
    @Min(value = 1000, message = "O valor mínimo do empréstimo deve ser R$ 1.000")
    private double valorConsignado;

    @NotBlank(message = "O Campo 'CPF' é obrigatório")
    @Pattern(
            regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11}",
            message = "O CPF deve estar no formato XXX.XXX.XXX-XX ou XXXXXXXXXXX"
    )
    private String cpf;

    Optional<SolicitacaoEmprestimo> findByCpf(String cpf) {
        return null;
    }

    void deleteByCpf(String cpf) {
    }
}
