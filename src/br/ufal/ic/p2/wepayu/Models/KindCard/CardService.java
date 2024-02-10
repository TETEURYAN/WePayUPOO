package br.ufal.ic.p2.wepayu.Models.KindCard;

import java.io.Serializable;
import java.lang.String;

public class CardService implements Serializable {
    private String data;
    private double valor;

    public CardService(){// Construtor vazio para preencher no XML

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
