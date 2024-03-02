package br.ufal.ic.p2.wepayu.models.KindCard;

public class CardPoint implements Cloneable{

    private String data;
    private Double horas;

    public CardPoint() {

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

    public void setData(String data) {
        this.data = data;
    }

    public void setHoras(Double horas) {
        this.horas = horas;
    }

    @Override
    public CardPoint clone() {
        try {
            return (CardPoint) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
