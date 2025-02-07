package com.apiconsig.api_consig.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacaoAprovacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull(message = "O campo 'nome' é obrigatório")
    @Size(min = 3, max = 40, message = "O nome deve ter entre 3 e 40 caracteres")
    @Column
    private String nome;

    @Setter
    @NotNull(message = "O campo 'idade' é obrigatório")
    @Min(value = 18, message = "Idade mínima é 18 anos")
    @Max(value = 80, message = "Idade máxima é de 80 anos")
    @Column
    private int idade;

    @Setter
    @NotNull(message = "O campo 'salário' é obrigatório")
    @Min(value = 2250, message = "Renda mínima mensal necessária é 1.5 Salário Mínimo")
    @Column
    private int salario;

    @Setter
    @NotNull(message = "O campo 'vinculoEmpregaticio' é obrigatório")
    @Column
    private String vinculoEmpregaticio;

    @Setter
    @NotNull(message = "O campo 'temContaAtiva' é obrigatório")
    @Column
    private boolean temContaAtiva;

    @Setter
    @NotNull(message = "O Campo 'gerente' é obrigatório")
    @Column
    private String gerente;

    @NotBlank(message = "O Campo 'CPF' é obrigatório")
    @Pattern(
            regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11}",
            message = "O CPF deve estar no formato XXX.XXX.XXX-XX ou XXXXXXXXXXX"
    )
    @Column
    private String cpf;
}
