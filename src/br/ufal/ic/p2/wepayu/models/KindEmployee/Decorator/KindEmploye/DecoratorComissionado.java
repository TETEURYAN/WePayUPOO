package br.ufal.ic.p2.wepayu.models.KindEmployee.Decorator.KindEmploye;

import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoComissionado;

public abstract class DecoratorComissionado extends EmpregadoComissionado {
    EmpregadoComissionado employ = null;

    protected DecoratorComissionado(EmpregadoComissionado e){
        employ = e;
    }
}
