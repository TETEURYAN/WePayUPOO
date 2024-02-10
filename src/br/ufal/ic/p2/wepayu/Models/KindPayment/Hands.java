package br.ufal.ic.p2.wepayu.Models.KindPayment;

import br.ufal.ic.p2.wepayu.Models.PaymentWay;

import java.io.Serializable;

public class Hands extends PaymentWay implements Serializable {

    private String way = "emMaos";
    public Hands(){

    }
    @Override
    public String getMetodoPagamento() {
        return way;
    }
}
