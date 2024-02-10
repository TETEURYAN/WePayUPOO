package br.ufal.ic.p2.wepayu.Models;

import java.io.Serializable;

public abstract class PaymentWay implements Serializable {


    public PaymentWay(){

    }
    public abstract String getMetodoPagamento();
}
