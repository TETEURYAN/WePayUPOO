package br.ufal.ic.p2.wepayu.Models.KindPayment;

import br.ufal.ic.p2.wepayu.Models.MetodoPagamento;

import java.io.Serializable;

/*
    Classe referente ao meio de pagamento EmMaos no modelo relacional
 */

public class Hands extends MetodoPagamento implements Serializable {

    private String way = "emMaos";
    public Hands(){

    }
    @Override
    public String getMetodoPagamento() {
        return way;
    }
}
