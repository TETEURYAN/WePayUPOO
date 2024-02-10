package br.ufal.ic.p2.wepayu.Models.KindEmployee;

import br.ufal.ic.p2.wepayu.Models.Empregado;

import java.io.Serializable;

public class EmpregadoAssalariado extends Empregado implements Serializable {

    private String salarioMensal;

    public EmpregadoAssalariado(){

    }

    public EmpregadoAssalariado(String nome, String endereco, String salarioMensal) {
        super(nome, endereco);
        this.salarioMensal = salarioMensal;
    }

    public String getSalarioMensal() {
        return salarioMensal;
    }


    @Override
    public void setSalario(String salario) {
        this.salarioMensal = salario;
    }

    public String getSalario () {
        return salarioMensal;
    }

    @Override
    public String getTipo() {
        return "assalariado";
    }

}
