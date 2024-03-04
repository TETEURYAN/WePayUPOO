package br.ufal.ic.p2.wepayu.models.TiposCartao;

import java.lang.String;

public class CartaoDeVenda {

    private String data;
    private Double horas;

    public CartaoDeVenda() {

    }

    public CartaoDeVenda(String data, Double horas) {
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

}
