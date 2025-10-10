package br.com.cooperativa.coopbank_api.model;

import jakarta.persistence.Entity;

@Entity
public class ContaPoupanca extends Conta {

    @Override
    public boolean podeSacar(double valor) {
        return getSaldo() >= valor;
    }
}
