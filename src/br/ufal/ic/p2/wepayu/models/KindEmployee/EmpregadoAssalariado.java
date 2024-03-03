package br.ufal.ic.p2.wepayu.models.KindEmployee;

import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.Sindicato;
import br.ufal.ic.p2.wepayu.models.MetodoPagamento;

public class EmpregadoAssalariado extends Empregado {

    private double salarioMensal;

    public EmpregadoAssalariado () {

    }

    public EmpregadoAssalariado(String nome, String endereco, String agenda, double salarioMensal) {
        super(nome, endereco, agenda);
        this.salarioMensal = salarioMensal;
    }

    public double getSalario () {
        return salarioMensal;
    }

    public double getSalarioBruto() {

        String [] pagamento = super.getAgenda().split(" ");

        if (pagamento[0].equals("semanal")) {
            if (pagamento.length == 3) {
                int week = Integer.parseInt(pagamento[1]);

                return Math.floor((this.salarioMensal * 12D / 52D) * week * 100) / 100F;
            } else {
                return Math.floor((this.salarioMensal*12D/52D) * 100)/100F;
            }
        } else {
            return this.salarioMensal;
        }

    }

    @Override
    public Sindicato getSindicalizado() {
        return super.getSindicalizado();
    }

    @Override
    public void setSalario(double salario) {
        this.salarioMensal = salario;
    }

    @Override
    public String getTipo() {
        return "assalariado";
    }

    @Override
    public MetodoPagamento getMetodoPagamento() {
        return super.getMetodoPagamento();
    }

    @Override
    public String getEndereco() {
        return super.getEndereco();
    }

}
