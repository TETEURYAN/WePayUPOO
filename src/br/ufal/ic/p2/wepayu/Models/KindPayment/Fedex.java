package br.ufal.ic.p2.wepayu.Models.KindPayment;

import br.ufal.ic.p2.wepayu.Models.MetodoPagamento;

import java.io.Serializable;

/*
    Classe referente ao meio de pagamento banco
 */

public class Fedex extends MetodoPagamento implements Serializable {

    public Fedex(){

    }
    @Override
    public String getMetodoPagamento() {
        return "correios";
    }
}
