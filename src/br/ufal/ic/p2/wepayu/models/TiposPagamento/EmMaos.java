package br.ufal.ic.p2.wepayu.models.TiposPagamento;

import br.ufal.ic.p2.wepayu.models.MetodoPagamento;

/**
 * Classe do {@link EmMaos que herda de {@link MetodoPagamento}
 * @author teteuryan faite100
 */
public class EmMaos extends MetodoPagamento {

    public EmMaos () {//Construtor vazaio qpara ser serializado para a Persistencia em XML

    }

    @Override
    public String getMetodoPagamento() {
        return "emMaos";
    }

    /**
     * Método usado para retornar as informações usadas para a Folha de Pagamento
     */
    @Override
    public String getOutputFile() {
        return "Em maos";
    }
}
