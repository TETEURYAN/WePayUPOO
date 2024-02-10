package br.ufal.ic.p2.wepayu.Models.KindCard;

import java.io.Serializable;
import java.time.LocalDate;

/*
    Classe referente ao cartão de ponto do modelo relacional
 */

public class CardPoint implements Serializable {

    private String data;
    private Double horas;

    public CardPoint(){// Construtor vazio necessário para ser escrito no XML

    }
    public CardPoint(String data, Double horas) {// Construtor
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
