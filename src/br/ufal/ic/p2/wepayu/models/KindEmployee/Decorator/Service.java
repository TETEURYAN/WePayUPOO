package br.ufal.ic.p2.wepayu.models.KindEmployee.Decorator;

import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.KindCard.CardService;
import br.ufal.ic.p2.wepayu.models.KindEmployee.Decorator.KindEmploye.DecoratorEmpregado;

public abstract class Service extends DecoratorEmpregado {
    Empregado empregado = null;
    protected Service(String data, double valor,Empregado e) {
        super(e);
        this.empregado = e;
        addTaxaServico(new CardService(data, valor));
    }

    public void addTaxaServico (CardService taxaServico) {
        empregado.getSindicalizado().addTaxaServico(taxaServico);
    }
}
