package br.ufal.ic.p2.wepayu.Models;

import java.io.Serializable;

/*
    Classe referente ao método de pagamento

 */
public abstract class MetodoPagamento implements Serializable {


    public MetodoPagamento(){

    }
    public abstract String getMetodoPagamento();
}
