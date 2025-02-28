package com.apiconsig.api_consig.controllers;

import com.apiconsig.api_consig.model.SolicitacaoAprovacao;
import com.apiconsig.api_consig.model.SolicitacaoEmprestimo;
import com.apiconsig.api_consig.services.SolicitacaoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ConsignadoController {

    private final SolicitacaoService solicitacaoService;
    private static final Logger log = LoggerFactory.getLogger(ConsignadoController.class);

    public ConsignadoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @PostMapping("/solicitar-aprovacao")
    public ResponseEntity<String> solicitarAprovacao(@Valid @RequestBody SolicitacaoAprovacao solicitacao) {
        log.info("Começou uma nova solicitação: {}", solicitacao);
        var resposta = solicitacaoService.iniciar(solicitacao);

        if ("Negado".equals(resposta)) {
            log.warn("Solicitação negada para CPF: {}", solicitacao.getCpf());
            return ResponseEntity.status(409).body("Solicitação negada: CPF já cadastrado ou não atende aos critérios.");
        }

        log.info("Solicitação aprovada para CPF: {} - Valor máximo: {}", solicitacao.getCpf(), resposta);
        return ResponseEntity.status(202).body(resposta);
    }

    @PostMapping("/solicitar-emprestimo")
    public ResponseEntity<String> solicitarCredito(@Valid @RequestBody SolicitacaoEmprestimo solicitacao) {
        log.info("Recebida solicitação de empréstimo: {}", solicitacao);
        var resultado = solicitacaoService.verificarElegibilidade(solicitacao);

        if (Objects.equals(resultado, "Negado")) {
            return ResponseEntity.status(409).body("Negado");
        }
        return ResponseEntity.status(202).body("Aprovado");
    }

    @GetMapping("/solicitacoes-aprovadas")
    public ResponseEntity<List<SolicitacaoEmprestimo>> buscarSolicitacoesAprovadas() {
        log.info("Buscando todas as solicitações de empréstimos aprovadas");

        List<SolicitacaoEmprestimo> solicitacoes = solicitacaoService.buscarAprovados();

        if (solicitacoes.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.status(200).body(solicitacoes);
    }

    @PutMapping("/atualizar-solicitacao/{id}")
    public ResponseEntity<String> atualizarSolicitacao(@PathVariable Long id, @Valid @RequestBody SolicitacaoEmprestimo solicitacaoAtualizada) {
        log.info("Atualizando solicitação de empréstimo com ID: {}", id);
        Optional<SolicitacaoEmprestimo> solicitacaoExistente = solicitacaoService.buscarPorId(id);

        if (solicitacaoExistente.isPresent()) {
            solicitacaoService.atualizar(id, solicitacaoAtualizada);
            return ResponseEntity.ok("Solicitação atualizada com sucesso");
        } else {
            return ResponseEntity.status(404).body("Solicitação não encontrada");
        }
    }

    @DeleteMapping("/delete-solicitacao/{cpf}")
    public ResponseEntity<String> deletarSolicitacao(@PathVariable String cpf) {
        Optional<SolicitacaoEmprestimo> emprestimo = solicitacaoService.buscarPorCpf(cpf);

        if (emprestimo.isPresent()) {
            solicitacaoService.deletarPorCPF(cpf);
            return ResponseEntity.ok("A solicitação de empréstimo foi cancelada e removida.");
        } else {
            return ResponseEntity.status(404).body("Nenhuma solicitação encontrada para o CPF: " + cpf);
        }
    }

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
    }
}
