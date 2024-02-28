package br.ufal.ic.p2.wepayu.models.KindEmployee.Decorator;

import br.ufal.ic.p2.wepayu.models.KindCard.CardPoint;
import br.ufal.ic.p2.wepayu.models.KindEmployee.Decorator.KindEmploye.DecoratorHorista;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoHorista;

public class Horas extends DecoratorHorista {
    EmpregadoHorista empregado = null;

    public Horas(String data, double horas, EmpregadoHorista e) {
        super(e);
        this.empregado = e;
        addRegistro(data,horas);
    }

    public void addRegistro(String data, double horas) {
        empregado.getCartao().add(new CardPoint(data, horas));
    }
}
