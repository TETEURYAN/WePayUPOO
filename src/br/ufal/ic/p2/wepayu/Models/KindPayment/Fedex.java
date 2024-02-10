package br.ufal.ic.p2.wepayu.Models.KindPayment;

import br.ufal.ic.p2.wepayu.Models.PaymentWay;

import java.io.Serializable;

public class Fedex extends PaymentWay implements Serializable {

    public Fedex(){

    }
    @Override
    public String getMetodoPagamento() {
        return "correios";
    }
}
