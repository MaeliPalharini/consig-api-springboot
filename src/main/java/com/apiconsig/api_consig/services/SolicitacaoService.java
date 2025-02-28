package com.apiconsig.api_consig.services;

import com.apiconsig.api_consig.model.SolicitacaoAprovacao;
import com.apiconsig.api_consig.model.SolicitacaoEmprestimo;
import com.apiconsig.api_consig.repositories.SolicitacaoAprovacaoRepository;
import com.apiconsig.api_consig.repositories.SolicitacaoEmprestimoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
public class SolicitacaoService {

    private final SolicitacaoAprovacaoRepository solicitacaoAprovacaoRepository;
    private final SolicitacaoEmprestimoRepository solicitacaoEmprestimoRepository; // 🔹 Correção: Criado o repositório de empréstimos

    private static final double JUROS = 0.02;
    private static final int MESES = 24;

    private static final Logger log = LoggerFactory.getLogger(SolicitacaoService.class);

    public SolicitacaoService(SolicitacaoAprovacaoRepository solicitacaoAprovacaoRepository,
                              SolicitacaoEmprestimoRepository solicitacaoEmprestimoRepository) {
        this.solicitacaoAprovacaoRepository = solicitacaoAprovacaoRepository;
        this.solicitacaoEmprestimoRepository = solicitacaoEmprestimoRepository;
    }

    public String iniciar(SolicitacaoAprovacao solicitacao) {
        String cpf = solicitacao.getCpf();
        double salario = solicitacao.getSalario();

        // Verifica se o CPF já solicitou empréstimo
        var jaCadastrado = solicitacaoAprovacaoRepository.findByCpf(cpf);

        if (jaCadastrado.isPresent()) {
            log.info("Usuário já pediu consignado: {}", jaCadastrado);
            return "Negado";
        }

        // Calcula o valor máximo de empréstimo permitido
        long maximo = calcularValorMaximo(salario * 0.30, JUROS, MESES);
        log.info("Valor máximo aprovado para CPF {}: R$ {}", cpf, maximo);

        // Salva a solicitação no banco de dados
        solicitacaoAprovacaoRepository.save(solicitacao);
        log.info("Solicitação de empréstimo salva para CPF {}", cpf);

        return String.valueOf(maximo);
    }

    public String verificarElegibilidade(SolicitacaoEmprestimo solicitacao) {
        String cpf = solicitacao.getCpf();
        double salario = solicitacao.getValorConsignado();

        var jaCadastrado = solicitacaoAprovacaoRepository.findByCpf(cpf);

        if (jaCadastrado.isPresent()) {
            log.info("Usuário já possui um empréstimo ativo: {}", jaCadastrado);
            return "Negado";
        }

        if (salario < 1000) {
            log.info("Empréstimo negado para CPF {} - valor menor que R$ 1000", cpf);
            return "Negado";
        }

        log.info("Empréstimo aprovado para CPF {} - valor de R$ {}", cpf, salario);
        return "Aprovado";
    }

    // 🔹 Método para buscar todas as solicitações aprovadas
    public List<SolicitacaoEmprestimo> buscarAprovados() {
        return solicitacaoEmprestimoRepository.findAll();
    }

    // 🔹 Método para buscar uma solicitação pelo ID
    public Optional<SolicitacaoEmprestimo> buscarPorId(Long id) {
        return solicitacaoEmprestimoRepository.findById(id);
    }

    // 🔹 Método para atualizar uma solicitação pelo ID
    public void atualizar(Long id, SolicitacaoEmprestimo novaSolicitacao) {
        Optional<SolicitacaoEmprestimo> existente = solicitacaoEmprestimoRepository.findById(id);

        if (existente.isPresent()) {
            SolicitacaoEmprestimo solicitacao = existente.get();
            solicitacao.setCpf(novaSolicitacao.getCpf());
            solicitacao.setValorConsignado(novaSolicitacao.getValorConsignado());

            solicitacaoEmprestimoRepository.save(solicitacao);
        }
    }

    // 🔹calcular o valor máximo do empréstimo
    private static Long calcularValorMaximo(double parcela, double taxaJuros, int meses) {
        return Math.round(parcela * ((Math.pow(1 + taxaJuros, meses) - 1) / taxaJuros));
    }

    // 🔹buscar solicitação por CPF
    public Optional<SolicitacaoEmprestimo> buscarPorCpf(String cpf) {
        return solicitacaoEmprestimoRepository.findByCpf(cpf);
    }

    // 🔹deletar solicitação por CPF
    public void deletarPorCPF(String cpf) {
        solicitacaoEmprestimoRepository.deleteByCpf(cpf);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        log.error("Erro na solicitação: {}", errors);
        return ResponseEntity.badRequest().body(errors);
    }
}
