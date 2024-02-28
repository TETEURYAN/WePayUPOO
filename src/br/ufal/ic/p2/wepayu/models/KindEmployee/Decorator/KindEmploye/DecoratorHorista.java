package br.ufal.ic.p2.wepayu.models.KindEmployee.Decorator.KindEmploye;

import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoHorista;

public class DecoratorHorista extends EmpregadoHorista {
    EmpregadoHorista employ = null;
    protected DecoratorHorista(EmpregadoHorista e){
        employ = e;
    }
}
