package br.ufal.ic.p2.wepayu.models.TiposEmpregados.Decorator;

import br.ufal.ic.p2.wepayu.models.TiposCartao.CartaoDePonto;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.Decorator.KindEmploye.DecoratorHorista;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.EmpregadoHorista;

/**
 * Decorator do {@link EmpregadoHorista} que cria uma classe para adicionar horas ao {@link CartaoDePonto}
 * @author teteuryan faite100
 */
public class Horas extends DecoratorHorista {
    EmpregadoHorista empregado = null;//EmpregadoHorista

    /**
     * Cria uma instancia que adiciona diretamente em {@link EmpregadoHorista} as horas para um determinado dia no vetor de {@link CartaoDePonto}
     */
    public Horas(String data, double horas, EmpregadoHorista e) {
        super(e);
        this.empregado = e;
        addRegistro(data,horas);
    }

    /**
     * Método que busca as horas para ser usado no construtor e enfim passar ao {@link EmpregadoHorista}
     */
    public void addRegistro(String data, double horas) {
        empregado.getCartao().add(new CartaoDePonto(data, horas));
    }
}
