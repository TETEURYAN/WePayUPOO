package br.ufal.ic.p2.wepayu.models.TiposEmpregados;

import br.ufal.ic.p2.wepayu.models.Agenda;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.Sindicato;
import br.ufal.ic.p2.wepayu.models.MetodoPagamento;

/**
 * Classe do {@link EmpregadoAssalariado} que herda de {@link Empregado}
 * @author teteuryan faite100
 */
public class EmpregadoAssalariado extends Empregado {

    private double salarioMensal;

    public EmpregadoAssalariado () { // Construtor vazio para ser serializado na Persistencia XML

    }

    /**
     * Cria uma instancia de {@link EmpregadoAssalariado}, os atributos de {@link Empregado} sao direcionados ao
     * super e armazena o Salario Mensal
     */
    public EmpregadoAssalariado(String nome, String endereco, Agenda agenda, double salarioMensal) throws Exception {
        super(nome, endereco,agenda);
        this.salarioMensal = salarioMensal;
    }

    public double getSalario () {
        return salarioMensal;
    }

    public double getSalarioBruto() {//Retorna o salario bruto do empregado assalariado

        if(getAgenda().getTipo().equals("mensal")){
            return getSalario();
        }
        else{
            int semana = getAgenda().getSemana();
            if (semana == 0) semana = 1;
            return Math.floor(((getSalario()*12D/52D)*semana)*100)/100F;
        }
    }

    public Sindicato getSindicalizado() {
        return super.getSindicalizado();
    }

    public void setSalario(double salario) {
        this.salarioMensal = salario;
    }

    public String getTipo() {
        return "assalariado";
    }

    public MetodoPagamento getMetodoPagamento() {
        return super.getMetodoPagamento();
    }

    public String getEndereco() {
        return super.getEndereco();
    }

}
