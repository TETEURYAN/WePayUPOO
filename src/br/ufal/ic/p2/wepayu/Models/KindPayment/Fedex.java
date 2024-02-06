package br.ufal.ic.p2.wepayu.Models.KindPayment;

import br.ufal.ic.p2.wepayu.Models.PaymentWay;

public class Fedex extends PaymentWay {
    @Override
    public String getMetodoPagamento() {
        return "correios";
    }
}
