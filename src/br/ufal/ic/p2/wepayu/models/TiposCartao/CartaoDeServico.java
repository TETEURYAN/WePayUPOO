package br.ufal.ic.p2.wepayu.models.TiposCartao;

import java.lang.String;

public class CartaoDeServico implements Cloneable {
    private String data;
    private double valor;

    public CartaoDeServico() {

    }

    public CartaoDeServico(String data, double valor) {
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

    @Override
    public CartaoDeServico clone() {
        try {
            return (CartaoDeServico) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
