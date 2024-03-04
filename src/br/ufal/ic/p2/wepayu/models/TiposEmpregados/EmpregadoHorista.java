package br.ufal.ic.p2.wepayu.models.TiposEmpregados;

import br.ufal.ic.p2.wepayu.exceptions.Query.CronoDataException;
import br.ufal.ic.p2.wepayu.models.Agenda;
import br.ufal.ic.p2.wepayu.models.TiposCartao.CartaoDePonto;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.utils.Utils;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe do {@link EmpregadoHorista} que herda de {@link Empregado}
 * @author teteuryan faite100
 */

public class EmpregadoHorista extends Empregado {

    private double descontos;//Descontos do Empregado Horista
    private double salarioPorHora;//Salario por hora do Empregado Horista
    private ArrayList<CartaoDePonto> cartao;//Vetor de Cartão de Ponto do Empregado Horista

    public EmpregadoHorista() {//Contrutor fazio para ser serializado para a Persistencia em XML

    }

    /**
     * Cria uma instancia de {@link EmpregadoHorista}, os atributos de {@link Empregado} sao direcionados ao
     * super e armazena o Salario por Hora e inicializa o vetor de {@link CartaoDePonto}
     */
    public EmpregadoHorista(String nome, String endereco, Agenda agenda, double salarioPorHora) throws Exception {
        super(nome, endereco, agenda);
        this.salarioPorHora = salarioPorHora;
        this.cartao = new ArrayList<CartaoDePonto>();
        this.descontos = 0;
    }

    public double getDescontos() {
        return descontos;
    }

    public void setDescontos(double descontos) {
        this.descontos = descontos;
    }

    public double getHorasNormaisTrabalhadas(LocalDate dataInicial, LocalDate dataFinal) throws Exception {//Método para calcular as Horas Normais trabalhadas diretamente da calsse EmpregadoHorista

        double horasAcumuladas = 0;

        if (dataInicial.isEqual(dataFinal))
            return horasAcumuladas;

        if (dataInicial.isAfter(dataFinal)) {
            throw new CronoDataException("Data inicial nao pode ser posterior aa data final.");
        }

        for (CartaoDePonto c : cartao) {

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

    public double getHorasExtrasTrabalhadas(LocalDate dataInicial, LocalDate dataFinal) throws Exception {// Método para calcualr as horas extras trabalhadas diretamente da classe EmpregadoHorista
        double horasAcumuladas = 0;

        if (dataInicial.isAfter(dataFinal)) {
            throw new CronoDataException("Data inicial invalida.");
        }

        for (CartaoDePonto c : cartao) {

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

    public ArrayList<CartaoDePonto> getCartao() {
        return this.cartao;
    }

    public void setCartao(ArrayList<CartaoDePonto> cartao) {
        this.cartao = cartao;
    }

    public void setSalario(double salario) {
        this.salarioPorHora = salario;
    }

    public double getSalario() {
        return this.salarioPorHora;
    }

    public String getTipo() {
        return "horista";
    }

    public double getSalarioBruto(LocalDate dataInicial, LocalDate dataFinal) throws Exception {//Método para calcular o salario bruto do Empregado Horista considerando um intervalod e tempo
        double valorHorasNormais = this.getHorasNormaisTrabalhadas(dataInicial, dataFinal) * this.salarioPorHora;
        double valorHorasExtras = 1.5 * this.getHorasExtrasTrabalhadas(dataInicial, dataFinal) * this.salarioPorHora;

        return valorHorasNormais + valorHorasExtras;
    }

    public String toString() {

        return "EmpregadoHorista{" + this.getNome() + '}';
    }
}
