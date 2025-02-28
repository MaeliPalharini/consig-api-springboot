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
@Table(name = "credito_aprovado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditoAprovado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotBlank(message = "O Campo 'CPF' é obrigatório")
    @Pattern(
            regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11}",
            message = "O CPF deve estar no formato XXX.XXX.XXX-XX ou XXXXXXXXXXX"
    )
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "valor_aprovado", nullable = false)
    private BigDecimal valorAprovado;

    @Column(name = "taxa_juros", nullable = false)
    private BigDecimal taxaJuros;
}
