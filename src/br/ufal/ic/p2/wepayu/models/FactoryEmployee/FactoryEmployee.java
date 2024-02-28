package br.ufal.ic.p2.wepayu.models.FactoryEmployee;

import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.utils.Utils;

public class FactoryEmployee {
    public Empregado makeEmployee(String nome, String endereco, String tipo, double salario) throws Exception {
        switch (tipo){
            case "horista" -> {
                return new EmpregadoHorista(nome, endereco, salario);
            }
            case "assalariado" -> {
                return new EmpregadoAssalariado(nome, endereco, salario);
            }
        }
        return null;
    }

    public Empregado makeEmployee(String nome, String endereco, String tipo, double salario, double comissao) throws Exception {
        switch (tipo){
            case "comissionado" ->{
                return new EmpregadoComissionado(nome, endereco, salario, comissao );
            }
        }
        return null;
    }
}
