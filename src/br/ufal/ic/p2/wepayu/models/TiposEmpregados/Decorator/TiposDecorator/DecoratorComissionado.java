package br.ufal.ic.p2.wepayu.models.TiposEmpregados.Decorator.TiposDecorator;

import br.ufal.ic.p2.wepayu.models.TiposEmpregados.EmpregadoComissionado;


/**
 * Base do Decorator do {@link EmpregadoComissionado} para adicionar novos Cartões de venda
 * @author teteuryan faite100
 */

public abstract class DecoratorComissionado extends EmpregadoComissionado {
    EmpregadoComissionado employ = null;
    protected DecoratorComissionado(EmpregadoComissionado e){
        employ = e;
    }
}
