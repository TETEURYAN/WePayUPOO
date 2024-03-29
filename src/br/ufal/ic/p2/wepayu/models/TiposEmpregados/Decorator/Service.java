package br.ufal.ic.p2.wepayu.models.TiposEmpregados.Decorator;

import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.TiposCartao.CartaoDeServico;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.Decorator.TiposDecorator.DecoratorEmpregado;


/**
 * Decorator do {@link Empregado} que cria uma classe para adicionar horas ao {@link Service}
 * @author teteuryan faite100
 */
public abstract class Service extends DecoratorEmpregado {
    Empregado empregado = null;//Empregado

    /**
     * Cria uma instancia que adiciona diretamente em {@link Empregado} as
     * taxas para um determinado dia no vetor de {@link Service}
     */
    protected Service(String data, double valor,Empregado e) {
        super(e);
        this.empregado = e;
        addTaxaServico(new CartaoDeServico(data, valor));
    }

    /**
     * Método que adiciona as taxas para ser usado no
     * construtor e enfim passar ao {@link Empregado}
     */
    public void addTaxaServico (CartaoDeServico taxaServico) {
        empregado.getSindicalizado().addTaxaServico(taxaServico);
    }
}
