package br.ufal.ic.p2.wepayu.Models.KindCard;

import java.time.LocalDate;

public class CardService {
    private LocalDate data;
    private double valor;

    public CardService(LocalDate data, double valor) {
        this.data = data;
        this.valor = valor;
    }

    public LocalDate getData() {
        return this.data;
    }

    public double getValor() {
        return this.valor;
    }
}
