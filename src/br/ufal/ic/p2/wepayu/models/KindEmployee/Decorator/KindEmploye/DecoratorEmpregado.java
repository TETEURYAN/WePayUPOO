package br.ufal.ic.p2.wepayu.models.KindEmployee.Decorator.KindEmploye;

import br.ufal.ic.p2.wepayu.models.Empregado;

public abstract class DecoratorEmpregado extends Empregado{
    Empregado employ = null;
    protected DecoratorEmpregado(Empregado e){
        employ = e;
    }
}
