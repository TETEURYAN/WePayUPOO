package br.ufal.ic.p2.wepayu.models.TiposEmpregados.Decorator.KindEmploye;

import br.ufal.ic.p2.wepayu.models.TiposEmpregados.EmpregadoHorista;

/**
 * Base do Decorator do {@link EmpregadoHorista} para adicionar novos Cartões de Ponto
 * @author teteuryan faite100
 */
public class DecoratorHorista extends EmpregadoHorista {
    EmpregadoHorista employ = null;
    protected DecoratorHorista(EmpregadoHorista e){
        employ = e;
    }
}
