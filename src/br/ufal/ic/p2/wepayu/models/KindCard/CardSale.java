package br.ufal.ic.p2.wepayu.models.KindCard;

import java.lang.String;

public class CardSale implements Cloneable{
    private String data;
    private Double horas;

    public CardSale() {

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

    public void setData(String data) {
        this.data = data;
    }

    public void setHoras(Double horas) {
        this.horas = horas;
    }

    @Override
    public CardSale clone() {
        try {
            return (CardSale) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
