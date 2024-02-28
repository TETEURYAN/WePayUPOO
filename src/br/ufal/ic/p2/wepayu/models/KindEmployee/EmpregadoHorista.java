package br.ufal.ic.p2.wepayu.models.KindEmployee;

import br.ufal.ic.p2.wepayu.exceptions.ExceptionEmpregado;
import br.ufal.ic.p2.wepayu.models.KindCard.CardPoint;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.utils.Utils;

import java.time.LocalDate;
import java.util.ArrayList;

public class EmpregadoHorista extends Empregado {

    private double descontos;
    private double salarioPorHora;
    private ArrayList<CardPoint> cartao;

    public EmpregadoHorista() {

    }

    public EmpregadoHorista(String nome, String endereco, double salarioPorHora) {
        super(nome, endereco);
        this.salarioPorHora = salarioPorHora;
        this.cartao = new ArrayList<CardPoint>();
        this.descontos = 0;
    }

    public double getDescontos() {
        return descontos;
    }

    public void setDescontos(double descontos) {
        this.descontos = descontos;
    }

//    public void addRegistro(String data, double horas) {
//        this.cartao.add(new CartaoDePonto(data, horas));
//    }

    public double getHorasNormaisTrabalhadas(LocalDate dataInicial, LocalDate dataFinal) throws Exception {

        double horasAcumuladas = 0;

        if (dataInicial.isEqual(dataFinal))
            return horasAcumuladas;

        if (dataInicial.isAfter(dataFinal)) {
            ExceptionEmpregado e = new ExceptionEmpregado();
            e.msgDataInicialPosteriorDataFinal();

            return horasAcumuladas;
        }

        for (CardPoint c : cartao) {

            LocalDate dataFormato = Utils.validData(c.getData(), "");

            if (dataFormato != null) {
                if (dataFormato.isEqual(dataInicial) ||
                        (dataFormato.isAfter(dataInicial) && dataFormato.isBefore(dataFinal))) {

                    if (c.getHoras() > 8) {
                        horasAcumuladas += 8.0;
                    } else {
                        horasAcumuladas += c.getHoras();
                    }
                }
            }

        }

        return horasAcumuladas;
    }

    public double getHorasExtrasTrabalhadas(LocalDate dataInicial, LocalDate dataFinal) throws Exception {
        double horasAcumuladas = 0;

        if (dataInicial.isAfter(dataFinal)) {
            ExceptionEmpregado e = new ExceptionEmpregado();
            e.msgDataInicialPosteriorDataFinal();

            return horasAcumuladas;
        }

        for (CardPoint c : cartao) {

            LocalDate dataFormato = Utils.validData(c.getData(), "");

            if (dataFormato != null) {
                if (dataFormato.isEqual(dataInicial) ||
                        (dataFormato.isAfter(dataInicial) && dataFormato.isBefore(dataFinal))) {

                    if (c.getHoras() > 8) {
                        horasAcumuladas += (c.getHoras() - 8.0);
                    }
                }
            }
        }

        return horasAcumuladas;
    }

    public ArrayList<CardPoint> getCartao() {
        return this.cartao;
    }

    public void setCartao(ArrayList<CardPoint> cartao) {
        this.cartao = cartao;
    }

    @Override
    public void setSalario(double salario) {
        this.salarioPorHora = salario;
    }

    @Override
    public double getSalario() {
        return this.salarioPorHora;
    }

    @Override
    public String getTipo() {
        return "horista";
    }

    public double getSalarioBruto(LocalDate dataInicial, LocalDate dataFinal) throws Exception {
        double valorHorasNormais = this.getHorasNormaisTrabalhadas(dataInicial, dataFinal) * this.salarioPorHora;
        double valorHorasExtras = 1.5 * this.getHorasExtrasTrabalhadas(dataInicial, dataFinal) * this.salarioPorHora;

        return valorHorasNormais + valorHorasExtras;
    }

    @Override
    public String toString() {

        return "EmpregadoHorista{" + this.getNome() + '}';
    }
}
