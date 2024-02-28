package br.ufal.ic.p2.wepayu.models.KindPayment;

import br.ufal.ic.p2.wepayu.models.MetodoPagamento;

public class Banco extends MetodoPagamento {
    private String banco;
    private String agencia;
    private String contaCorrente;

    public Banco () {

    }

    public Banco(String banco, String agencia, String contaCorrente) {
        this.banco = banco;
        this.agencia = agencia;
        this.contaCorrente = contaCorrente;
    }

    public String getBanco() {
        return banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getContaCorrente() {
        return contaCorrente;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public void setContaCorrente(String contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    @Override
    public String getMetodoPagamento() {
        return "banco";
    }

    @Override
    public String getOutputFile() {
        return getBanco() + "," + " Ag. " + getAgencia() + " CC " + getContaCorrente();
    }
}
