package br.ufal.ic.p2.wepayu.Models.KindPayment;

import br.ufal.ic.p2.wepayu.Models.MetodoPagamento;

import java.io.Serializable;

/*
    Classe referente ao meio de pagamento banco
 */
public class Bank extends MetodoPagamento implements Serializable {
    private static String banco;
    private static String agencia;
    private static String corrente;

    public Bank(){

    }
    public Bank(String banco, String agencia, String contaCorrente) { //Construtor do banco
        this.banco = banco;
        this.agencia = agencia;
        this.corrente = contaCorrente;
    }

    public static String getBanco() {
        return banco;
    }

    public static String getAgencia() {
        return agencia;
    }

    public static String getCorrente() {
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
