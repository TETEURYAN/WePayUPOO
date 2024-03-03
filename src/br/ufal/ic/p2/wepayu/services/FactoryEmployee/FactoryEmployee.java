package br.ufal.ic.p2.wepayu.services.FactoryEmployee;

import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.services.Settings;

public class FactoryEmployee {
    public Empregado makeEmployee(String nome, String endereco, String tipo, double salario) throws Exception {
        switch (tipo){
            case "horista" -> {
                return new EmpregadoHorista(nome, endereco, Settings.PADRAO_HORISTA, salario);
            }
            case "assalariado" -> {
                return new EmpregadoAssalariado(nome, endereco,Settings.PADRAO_ASSALARIADO, salario);
            }
        }
        return null;
    }

    public Empregado makeEmployee(String nome, String endereco, String tipo, double salario, double comissao) throws Exception {
        switch (tipo){
            case "comissionado" ->{
                return new EmpregadoComissionado(nome, endereco, Settings.PADRAO_COMISSIONADO, salario, comissao );
            }
        }
        return null;
    }
}
