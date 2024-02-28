package br.ufal.ic.p2.wepayu.models.KindCard;

import java.lang.String;

public class CardService {
    private String data;
    private double valor;

    public CardService() {

    }

    public CardService(String data, double valor) {
        this.data = data;
        this.valor = valor;
    }

    public String getData() {
        return this.data;
    }

    public double getValor() {
        return this.valor;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}