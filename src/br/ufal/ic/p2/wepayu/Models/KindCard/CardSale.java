package br.ufal.ic.p2.wepayu.Models.KindCard;

import java.time.LocalDate;

public class CardSale {
    private LocalDate data;
    private Double horas;

    public CardSale(LocalDate data, Double horas) {
        this.data = data;
        this.horas = horas;
    }

    public Double getHoras() {
        return horas;
    }

    public LocalDate getData() {
        return data;
    }
}
