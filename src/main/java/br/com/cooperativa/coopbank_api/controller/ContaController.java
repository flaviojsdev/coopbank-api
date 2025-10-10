package br.com.cooperativa.coopbank_api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.cooperativa.coopbank_api.model.*;
import br.com.cooperativa.coopbank_api.repository.*;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CorrentistaRepository correntistaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    // --------------------
    // Criar Conta Corrente
    // --------------------
    @PostMapping("/corrente")
    public String criarContaCorrente(@RequestParam Long correntistaId,
                                     @RequestParam Double saldo,
                                     @RequestParam Double limite) {
        Optional<Correntista> correntista = correntistaRepository.findById(correntistaId);
        if (correntista.isEmpty()) return "Correntista não encontrado";

        ContaCorrente conta = new ContaCorrente();
        conta.setCorrentista(correntista.get());
        conta.setSaldo(saldo);
        conta.setLimite(limite);

        contaRepository.save(conta);
        return "Conta Corrente criada com sucesso";
    }

    // ----------------------
    // Criar Conta Poupança
    // ----------------------
    @PostMapping("/poupanca")
    public String criarContaPoupanca(@RequestParam Long correntistaId,
                                     @RequestParam Double saldo) {
        Optional<Correntista> correntista = correntistaRepository.findById(correntistaId);
        if (correntista.isEmpty()) return "Correntista não encontrado";

        ContaPoupanca conta = new ContaPoupanca();
        conta.setCorrentista(correntista.get());
        conta.setSaldo(saldo);

        contaRepository.save(conta);
        return "Conta Poupança criada com sucesso";
    }

    // --------------------
    // Depósito
    // --------------------
    @PostMapping("/{id}/deposito")
    public String deposito(@PathVariable Long id, @RequestParam Double valor) {
        Optional<Conta> contaOpt = contaRepository.findById(id);
        if (contaOpt.isEmpty()) return "Conta não encontrada";

        Conta conta = contaOpt.get();
        conta.setSaldo(conta.getSaldo() + valor);
        contaRepository.save(conta);

        Transacao t = new Transacao();
        t.setConta(conta);
        t.setTipo("DEPÓSITO");
        t.setValor(valor);
        transacaoRepository.save(t);

        return "Depósito realizado com sucesso";
    }

    // --------------------
    // Saque
    // --------------------
    @PostMapping("/{id}/saque")
    public String saque(@PathVariable Long id, @RequestParam Double valor) {
        Optional<Conta> contaOpt = contaRepository.findById(id);
        if (contaOpt.isEmpty()) return "Conta não encontrada";

        Conta conta = contaOpt.get();

        double limiteSaque = conta instanceof ContaCorrente ?
                conta.getSaldo() + ((ContaCorrente) conta).getLimite() :
                conta.getSaldo();

        if (valor > limiteSaque) return "Saldo insuficiente";

        conta.setSaldo(conta.getSaldo() - valor);
        contaRepository.save(conta);

        Transacao t = new Transacao();
        t.setConta(conta);
        t.setTipo("SAQUE");
        t.setValor(valor);
        transacaoRepository.save(t);

        return "Saque realizado com sucesso";
    }

    // --------------------
    // Rendimento Poupança
    // --------------------
    @PostMapping("/rendimento/{id}")
    public String rendimento(@PathVariable Long id, @RequestParam Double taxa) {
        Optional<Conta> contaOpt = contaRepository.findById(id);
        if (contaOpt.isEmpty()) return "Conta não encontrada";

        Conta conta = contaOpt.get();
        if (!(conta instanceof ContaPoupanca)) return "Conta não é Poupança";

        double rendimento = conta.getSaldo() * taxa;
        conta.setSaldo(conta.getSaldo() + rendimento);
        contaRepository.save(conta);

        Transacao t = new Transacao();
        t.setConta(conta);
        t.setTipo("RENDIMENTO");
        t.setValor(rendimento);
        transacaoRepository.save(t);

        return "Rendimento aplicado com sucesso";
    }

    // --------------------
    // Juros Conta Corrente
    // --------------------
    @PostMapping("/juros/{id}")
    public String juros(@PathVariable Long id, @RequestParam Double taxa) {
        Optional<Conta> contaOpt = contaRepository.findById(id);
        if (contaOpt.isEmpty()) return "Conta não encontrada";

        Conta conta = contaOpt.get();
        if (!(conta instanceof ContaCorrente)) return "Conta não é Corrente";

        if (conta.getSaldo() >= 0) return "Saldo positivo, não há juros";

        double juros = conta.getSaldo() * taxa;
        conta.setSaldo(conta.getSaldo() + juros); // saldo negativo + juros (negativo)
        contaRepository.save(conta);

        Transacao t = new Transacao();
        t.setConta(conta);
        t.setTipo("JUROS");
        t.setValor(juros);
        transacaoRepository.save(t);

        return "Juros aplicados sobre saldo negativo";
    }
    
    
}
