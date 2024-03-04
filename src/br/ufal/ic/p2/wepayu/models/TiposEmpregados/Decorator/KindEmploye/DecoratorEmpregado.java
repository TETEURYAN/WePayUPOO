package br.ufal.ic.p2.wepayu.models.TiposEmpregados.Decorator.KindEmploye;

import br.ufal.ic.p2.wepayu.models.Empregado;

/**
 * Base do Decorator do {@link Empregado} para adicionar novas Taxas de Serviço
 * @author teteuryan faite100
 */
public abstract class DecoratorEmpregado extends Empregado{
    Empregado employ = null;
    protected DecoratorEmpregado(Empregado e){
        employ = e;
    }
}
