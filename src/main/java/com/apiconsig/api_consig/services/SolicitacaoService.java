package com.apiconsig.api_consig.services;

import com.apiconsig.api_consig.model.SolicitacaoAprovacao;
import com.apiconsig.api_consig.repositories.SolicitacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;

    Logger log = LoggerFactory.getLogger(SolicitacaoService.class);

    public SolicitacaoService(SolicitacaoRepository solicitacaoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
    }

    public String iniciar(SolicitacaoAprovacao solicitacao) {
        var cpf = solicitacao.getCpf();
        var salario = solicitacao.getSalario();
        double JUROS = 0.02;
        int MESES = 24;

        var jaCadastrado = solicitacaoRepository.findByCpf(cpf);

        if (jaCadastrado.isPresent()) {
            log.info("Usuário já pediu consignado: {}", jaCadastrado);
            return "Negado";
        }

        var maximo = calcularValorMaximo(salario*0.30, JUROS, MESES);

        solicitacaoRepository.save(solicitacao);

        return String.valueOf(maximo);
    }

    private static Long calcularValorMaximo(double parcela, double taxaJuros, int meses) {
        return Math.round(parcela * ((Math.pow(1 + taxaJuros, meses) - 1) / taxaJuros));
    }
}
