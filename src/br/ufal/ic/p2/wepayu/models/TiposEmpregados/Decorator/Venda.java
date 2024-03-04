package br.ufal.ic.p2.wepayu.models.TiposEmpregados.Decorator;

import br.ufal.ic.p2.wepayu.models.TiposCartao.CartaoDeVenda;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.Decorator.KindEmploye.DecoratorComissionado;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.EmpregadoComissionado;

/**
 * Decorator do {@link EmpregadoComissionado} que cria uma classe para adicionar vendas ao {@link CartaoDeVenda}
 * @author teteuryan faite100
 */
public class Venda extends DecoratorComissionado {

    private EmpregadoComissionado empregado;

    /**
     * Cria uma instancia que adiciona vendas diretamente em {@link EmpregadoComissionado} as
     * taxas para um determinado dia no vetor de {@link CartaoDeVenda}
     */
    public Venda(String data, double valor, EmpregadoComissionado e){
        super(e);
        this.empregado = e;
        addVenda(data, valor);
    }

    /**
     * Método que adiciona as Vendas para ser usado no
     * construtor e enfim passar ao {@link EmpregadoComissionado}
     */
    public void addVenda(String data, double valor) {
        empregado.getVendas().add(new CartaoDeVenda(data, valor));
    }

}
