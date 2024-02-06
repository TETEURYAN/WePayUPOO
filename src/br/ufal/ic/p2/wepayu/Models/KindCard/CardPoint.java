package br.ufal.ic.p2.wepayu.Models.KindCard;

import java.time.LocalDate;

public class CardPoint {

    private LocalDate data;
    private Double horas;

    public CardPoint(LocalDate data, Double horas) {
        this.data = data;
        this.horas = horas;
    }

    public LocalDate getData() {
        return this.data;
    }

    public Double getHoras() {
        return this.horas;
    }
}
