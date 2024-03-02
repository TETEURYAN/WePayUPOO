package br.ufal.ic.p2.wepayu.models;

public abstract class MetodoPagamento implements Cloneable{

    public MetodoPagamento () {

    }

    public abstract String getMetodoPagamento();

    public abstract String getOutputFile();

    @Override
    public MetodoPagamento clone() {
        try {
            return (MetodoPagamento) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
