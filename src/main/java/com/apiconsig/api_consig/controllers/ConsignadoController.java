package com.apiconsig.api_consig.controllers;

import com.apiconsig.api_consig.model.SolicitacaoAprovacao;
import com.apiconsig.api_consig.services.SolicitacaoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api")
public class ConsignadoController {

    private final SolicitacaoService solicitacaoService;

    Logger log = LoggerFactory.getLogger(ConsignadoController.class);

    public ConsignadoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @PostMapping("/solicitar-aprovacao")
    public ResponseEntity<String> solicitarAprovacao(@Valid @RequestBody SolicitacaoAprovacao solicitacao) {
        log.info("Começou uma nova solicitação: {}", solicitacao);

        var resposta = solicitacaoService.iniciar(solicitacao);

        if (Objects.equals(resposta, "Negado")) {
            return ResponseEntity.status(409).body("Negado");
        }

        return ResponseEntity.status(202).body(resposta);
    }
}
