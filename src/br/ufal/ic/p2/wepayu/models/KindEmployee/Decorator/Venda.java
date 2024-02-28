package br.ufal.ic.p2.wepayu.models.KindEmployee.Decorator;

import br.ufal.ic.p2.wepayu.models.KindCard.CardSale;
import br.ufal.ic.p2.wepayu.models.KindEmployee.Decorator.KindEmploye.DecoratorComissionado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoComissionado;

public class Venda extends DecoratorComissionado {

    private EmpregadoComissionado empregado;

    public Venda(String data, double valor, EmpregadoComissionado e){
        super(e);
        this.empregado = e;
        addVenda(data, valor);
    }

    public void addVenda(String data, double valor) {
        empregado.getVendas().add(new CardSale(data, valor));
    }

}
