package br.ufal.ic.p2.wepayu.models.TiposEmpregados;

import br.ufal.ic.p2.wepayu.models.Agenda;
import br.ufal.ic.p2.wepayu.models.TiposCartao.CartaoDeVenda;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.Sindicato;
import br.ufal.ic.p2.wepayu.models.MetodoPagamento;
import br.ufal.ic.p2.wepayu.utils.Utils;
import br.ufal.ic.p2.wepayu.utils.Validate;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe do {@link EmpregadoComissionado} que herda de {@link Empregado}
 * @author teteuryan faite100
 */
public class EmpregadoComissionado extends Empregado {

    private double taxaDeComissao;
    private double salarioMensal;
    private ArrayList<CartaoDeVenda> vendas;

    public EmpregadoComissionado() {//Contrutor vazio para ser serialziado na Persistencia com XML

    }

    /**
     * Cria uma instancia de {@link EmpregadoComissionado}, os atributos de {@link Empregado} sao direcionados ao
     * super e armazena o Salario Mensal, a taxa de comissao e inicializa o vetor de {@link CartaoDeVenda}
     */
    public EmpregadoComissionado(String nome, String endereco, Agenda agenda, double salarioMensal, double taxaDeComissao) throws Exception {
        super(nome, endereco,agenda);
        this.taxaDeComissao = taxaDeComissao;
        this.salarioMensal = salarioMensal;
        this.vendas = new ArrayList<CartaoDeVenda>();
    }

    public double getTaxaDeComissao() {
        return taxaDeComissao;
    }

    public void setTaxaDeComissao(double taxaDeComissao) {
        this.taxaDeComissao = taxaDeComissao;
    }

    public void addVenda(String data, double valor) {
        this.vendas.add(new CartaoDeVenda(data, valor));
    }

    public double getVendasRealizadas(LocalDate dataInicial, LocalDate dataFinal) throws Exception {//Método que retorna as vendas realizadas em um período de tempo

        double vendasRealizadas = 0;

        Validate.validCorrectData(dataInicial, dataFinal);
        if (dataInicial.isEqual(dataFinal))
            return vendasRealizadas;

        for (CartaoDeVenda c : this.vendas) {

            LocalDate dataFormato = Utils.validData(c.getData(), "");

            if (dataFormato != null) {
                if (dataFormato.isEqual(dataInicial) ||
                        (dataFormato.isAfter(dataInicial) && dataFormato.isBefore(dataFinal))) {
                    vendasRealizadas += c.getHoras();
                }
            }
        }

        return vendasRealizadas;
    }

    public void setSalario(double salario) {
        this.salarioMensal = salario;
    }

    public double getSalario() {
        return salarioMensal;
    }

    public String getTipo() {
        return "comissionado";
    }

    public MetodoPagamento getMetodoPagamento() {
        return super.getMetodoPagamento();
    }

    public String getEndereco() {
        return super.getEndereco();
    }

    public Sindicato getSindicalizado() {
        return super.getSindicalizado();
    }

    public String getNome() {
        return super.getNome();
    }

    public ArrayList<CartaoDeVenda> getVendas() {
        return this.vendas;
    }

    public double getComissao(LocalDate dataInicial, LocalDate dataCriacao) throws Exception {//Método para calcular a comissao dado um período de temo
        double comissao = this.getVendasRealizadas(dataInicial, dataCriacao) * this.taxaDeComissao;
        comissao = Math.floor(comissao*100)/100F;
        return comissao;
    }

    public double getSalarioBruto(LocalDate dataInicial, LocalDate dataCriacao) throws Exception {//Método para calcaular o salário bruto dado um periodo de tempo

        Agenda agenda = getAgenda();
        if(agenda.getTipo().equals("mensal"))
            return getSalario() + getComissao(dataInicial, dataCriacao);
        else{
            int semana = Math.max(agenda.getSemana(), 1);
            return Math.floor((getSalario()*12D/52D)*semana * 100)/100F + getComissao(dataInicial, dataCriacao);
        }
    }


    public void setEndereco(String endereco) {
        super.setEndereco(endereco);
    }


    public void setSindicalizado(Sindicato sindicalizado) {
        super.setSindicalizado(sindicalizado);
    }


    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        super.setMetodoPagamento(metodoPagamento);
    }

    public void setVendas(ArrayList<CartaoDeVenda> vendas) {
        this.vendas = vendas;
    }
}
