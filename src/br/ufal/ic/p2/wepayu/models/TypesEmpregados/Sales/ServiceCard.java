package br.ufal.ic.p2.wepayu.models;

import java.time.LocalDate;

public class ServiceCard {

    private LocalDate data;
    private float tax;

    public ServiceCard (LocalDate data, float tax) {
        this.data = data;
        this.tax= tax;
    }

    public LocalDate getData() {
        return this.data;
    }

    public Float getTax() {
        return this.tax;
    }
}