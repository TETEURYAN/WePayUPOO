package br.ufal.ic.p2.wepayu.Models.KindPayment;

import br.ufal.ic.p2.wepayu.Models.PaymentWay;

import java.io.Serializable;

public class Bank extends PaymentWay implements Serializable {
    private String banco;
    private String agencia;
    private String corrente;

    public Bank(){

    }
    public Bank(String banco, String agencia, String contaCorrente) {
        this.banco = banco;
        this.agencia = agencia;
        this.corrente = contaCorrente;
    }

    public String getBanco() {
        return banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getCorrente() {
        return corrente;
    }

    @Override
    public String getMetodoPagamento() {
        return "banco";
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public void setCorrente(String corrente) {
        this.corrente = corrente;
    }
}
