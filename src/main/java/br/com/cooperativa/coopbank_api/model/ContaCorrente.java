package br.com.cooperativa.coopbank_api.model;

import jakarta.persistence.Entity;

@Entity
public class ContaCorrente extends Conta {

    private Double limite;

    @Override
    public boolean podeSacar(double valor) {
        return getSaldo() + limite >= valor;
    }

    // Getters e Setters
    public Double getLimite() { return limite; }
    public void setLimite(Double limite) { this.limite = limite; }
}
