package br.ufal.ic.p2.wepayu.Models.KindPayment;

import br.ufal.ic.p2.wepayu.Models.PaymentWay;

public class Bank extends PaymentWay {
    private String banco;
    private String agencia;
    private String corrente;

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
}
