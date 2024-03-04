package br.ufal.ic.p2.wepayu.services.FactoryEmpregados;

import br.ufal.ic.p2.wepayu.models.Agenda;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.services.Settings;

/**
 * Classe {@link FactoryEmpregados} para servir de fábrica para
 * os Empregados, independente do tipo de empregado
 * @author teteuryan faite100
 */
public class FactoryEmpregados {

    /**
     * Metodo que gera {@link EmpregadoHorista} e {@link EmpregadoComissionado}, adicionando
     * a agenda inicial de ambos tipos de empregados
     */
    public Empregado makeEmployee(String nome, String endereco, String tipo, double salario) throws Exception {
        switch (tipo){
            case "horista" -> {
                Agenda ag = new Agenda(Settings.PADRAO_HORISTA);
                return new EmpregadoHorista(nome, endereco,ag, salario);
            }
            case "assalariado" -> {
                Agenda ag = new Agenda(Settings.PADRAO_ASSALARIADO);
                return new EmpregadoAssalariado(nome, endereco,ag, salario);
            }
        }
        return null;
    }

    /**
     * Metodo que gera {@link EmpregadoComissionado} adicionando
     * a agenda inicial do Empregado Comissionado
     */
    public Empregado makeEmployee(String nome, String endereco, String tipo, double salario, double comissao) throws Exception {
        switch (tipo){
            case "comissionado" ->{
                Agenda ag = new Agenda(Settings.PADRAO_COMISSIONADO);
                return new EmpregadoComissionado(nome, endereco, ag, salario, comissao );
            }
        }
        return null;
    }
}
