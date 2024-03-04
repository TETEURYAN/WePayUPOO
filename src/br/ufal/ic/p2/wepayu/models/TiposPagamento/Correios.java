package br.ufal.ic.p2.wepayu.models.TiposPagamento;

import br.ufal.ic.p2.wepayu.models.MetodoPagamento;

/**
 * Classe do {@link Correios que herda de {@link MetodoPagamento}
 * @author teteuryan faite100
 */
public class Correios extends MetodoPagamento {

    public Correios() {//Construtor vazaio qpara ser serializado para a Persistencia em XML

    }

    @Override
    public String getMetodoPagamento() {
        return "correios";
    }

    /**
     * Método usado para retornar as informações usadas para a Folha de Pagamento
     */
    @Override
    public String getOutputFile() {
        return "Correios";
    }
}
