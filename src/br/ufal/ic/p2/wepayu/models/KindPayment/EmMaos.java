package br.ufal.ic.p2.wepayu.models.KindPayment;

import br.ufal.ic.p2.wepayu.models.MetodoPagamento;

public class EmMaos extends MetodoPagamento {

    public EmMaos () {

    }

    @Override
    public String getMetodoPagamento() {
        return "emMaos";
    }

    @Override
    public String getOutputFile() {
        return "Em maos";
    }
}
