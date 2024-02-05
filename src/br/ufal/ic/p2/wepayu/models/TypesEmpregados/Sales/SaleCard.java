package br.ufal.ic.p2.wepayu.models.TypesEmpregados.Sales;

import java.time.LocalDate;

public class SaleCard {
    private LocalDate saleDate;
    private float value;

    public SaleCard(LocalDate saleDate, float value){
        this.saleDate = saleDate;
        this.value = value;
    }


    public LocalDate getSaleDate() {
        return saleDate;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
