package br.ufal.ic.p2.wepayu.Models.KindEmployee;

import br.ufal.ic.p2.wepayu.Models.Empregado;
import br.ufal.ic.p2.wepayu.Models.MembroSindicato;
import br.ufal.ic.p2.wepayu.Utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
    Classe referente ao Empregado Assalariado no Modelo Relacional
 */

public class EmpregadoAssalariado extends Empregado implements Serializable {// Construtor do Empregado Assalariado

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


    public Double getDescontos(String dataInicial, String dataFinal) throws Exception{ // Método para calcular os descontos
        Double total = 0d;

        int dias = Utils.getIntervaloDias(dataInicial, dataFinal) + 1;

        if (getSindicato() != null) {
            MembroSindicato membro = getSindicato();
            total = membro.getTaxasServico(dataInicial, dataFinal) + dias*membro.getAdicionalSindicato();
        }

        return total;
    }

    public Double getSalarioLiquido(String dataInicial, String dataFinal) throws Exception{// Método oara calcular o salário líquido
        return Utils.quitValue(getSalario()) - getDescontos(dataInicial, dataFinal);
    }
    public String getSalario () {
        return salarioMensal;
    }

    public Object[] getDataLine(String dataInicial, String data) throws Exception{

        List<Double> valores = new ArrayList<>();

        // Adiciona os dados numéricos a lista de valores
        valores.add(Utils.quitValue(getSalario()));
        valores.add(getDescontos(dataInicial, data));
        valores.add(getSalarioLiquido(dataInicial, data));

        // Cria strings dos dados numéricos para inserção na folha de pagamento
        String bruto = Utils.doubleToString(Utils.quitValue(getSalario()), false);
        String descontos = Utils.doubleToString(getDescontos(dataInicial, data), false);
        String liquido = Utils.doubleToString(getSalarioLiquido(dataInicial, data), false);

        // Cria String da linha correspondente aos dados na folha de pagamento
        String linha = String.format("%-48s %13s %9s %15s %s", getNome(),
                bruto, descontos, liquido, getDataPayment());

        return new Object[]{linha, valores};
    }

    @Override
    public String getTipo() {
        return "assalariado";
    }

}
