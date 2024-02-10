package br.ufal.ic.p2.wepayu.Models.KindCard;

import java.io.Serializable;
import java.time.LocalDate;

public class CardPoint implements Serializable {

    private String data;
    private Double horas;

    public CardPoint(){

    }
    public CardPoint(String data, Double horas) {
        this.data = data;
        this.horas = horas;
    }

    public String getData() {
        return this.data;
    }

    public Double getHoras() {
        return this.horas;
    }

    public void setData(String data){
        this.data = data;
    }

    public void setHoras(Double horas) {
        this.horas = horas;
    }
}
