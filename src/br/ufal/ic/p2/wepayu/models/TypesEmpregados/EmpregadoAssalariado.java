package br.ufal.ic.p2.wepayu.models.TypesEmpregados;

import br.ufal.ic.p2.wepayu.Exception.TipoNaoAplicavalExcpetion;
import br.ufal.ic.p2.wepayu.models.Empregado;

public class EmpregadoAssalariado extends Empregado {
    private String salarioMensal;
    public EmpregadoAssalariado(String nome, String endereco, String tipo, String salario) throws Exception{
        super(nome, endereco, tipo, salario, false);
        if(!tipo.equalsIgnoreCase("assalariado")){
            throw new TipoNaoAplicavalExcpetion("Tipo nao aplicavel.");
        }
        this.salarioMensal = salario;
    }

    @Override
    public String getTipo() {
        return "assalariado";
    }

    @Override
    public String getSalario() {
        return salarioMensal;
    }
}
