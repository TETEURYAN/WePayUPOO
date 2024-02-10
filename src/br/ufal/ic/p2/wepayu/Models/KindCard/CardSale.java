package br.ufal.ic.p2.wepayu.Models.KindCard;

import java.io.Serializable;

public class CardSale implements Serializable {
    private String data;
    private Double horas;

    public CardSale(){

    }
    public CardSale(String data, Double horas) {
        this.data = data;
        this.horas = horas;
    }

    public Double getHoras() {
        return horas;
    }

    public String getData() {
        return data;
    }

    public void setHoras(Double horas) {
        this.horas = horas;
    }

    public void setData(String data) {
        this.data = data;
    }
}
