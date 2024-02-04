package br.ufal.ic.p2.wepayu.models.Types;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.models.Empregado;

public class EmpregadoHorista extends Empregado {
    private String comissao;
    private String SalarioHora;

    public EmpregadoHorista(String nome, String endereco, String tipo, String salario) throws Exception {
        super(nome, endereco, tipo, salario, false);
        if(!tipo.equalsIgnoreCase("horista")){
            throw new TipoNaoAplicavalExcpetion("Tipo nao aplicavel.");
        }
        this.SalarioHora = salario;

    }

    public String getComissao() {
        return comissao;
    }

    public String getSalarioHora(){
        return SalarioHora;
    }
}
