package com.apiconsig.api_consig.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @NotBlank(message = "O Campo 'CPF' é obrigatório")
    @Pattern(
            regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11}",
            message = "O CPF deve estar no formato XXX.XXX.XXX-XX ou XXXXXXXXXXX"
    )
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "renda_mensal", nullable = false)
    private BigDecimal rendaMensal;
}

