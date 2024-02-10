package br.ufal.ic.p2.wepayu.Models.KindEmployee;

import br.ufal.ic.p2.wepayu.Exceptions.ExceptionErrorMessage;
import br.ufal.ic.p2.wepayu.Models.KindCard.CardSale;
import br.ufal.ic.p2.wepayu.Models.Empregado;
import br.ufal.ic.p2.wepayu.Utils.Utils;
import br.ufal.ic.p2.wepayu.Utils.Validate;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class EmpregadoComissionado extends Empregado implements Serializable {

    private String taxaComissao;
    private String salarioMensal;
    private ArrayList<CardSale> vendas;

    public String getTaxaComissao() {
        return taxaComissao;
    }

    public void setTaxaComissao(String taxaComissao) {
        this.taxaComissao = taxaComissao;
    }

    public void setSalarioMensal(String salarioMensal) {
        this.salarioMensal = salarioMensal;
    }

    public EmpregadoComissionado(){

    }

    public EmpregadoComissionado(String nome, String endereco, String salarioMensal, String taxaDeComissao) {
        super(nome, endereco);
        this.taxaComissao = taxaDeComissao;
        this.salarioMensal = salarioMensal;
        this.vendas = new ArrayList<CardSale>();
    }

    public String getSalarioMensal() {
        return salarioMensal;
    }

    public String getTaxa() {
        return taxaComissao;
    }

    public void setTaxa(String taxaDeComissao) {
        this.taxaComissao = taxaDeComissao;
    }

    public void addVenda(String dataString, String valor) throws Exception {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");
        double value = Utils.quitValue(valor);
        Validate.validValue(value);

        try {
            LocalDate dataFormato = LocalDate.parse(dataString, formato);
            this.vendas.add(new CardSale(dataString, value));
        } catch (DateTimeParseException e) {
            throw new ExceptionErrorMessage("Data invalida.");
        }
    }

    public String getVendasRealizadas(String dataInicial, String dataFinal) throws Exception {

        double sales = 0;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate dateInit = null;
        LocalDate dateEnd = null;

        try {
            dateInit = LocalDate.parse(Validate.valiDate(dataInicial), formato);
        } catch (Exception e) {throw new Exception("Data inicial invalida.");}

        try{
            dateEnd = LocalDate.parse(Validate.valiDate(dataFinal), formato);
        } catch (Exception e) {throw new Exception("Data final invalida.");}

        if (dateInit.isAfter(dateEnd)) throw new ExceptionErrorMessage("Data inicial nao pode ser posterior aa data final.");
        if (dateInit.isEqual(dateEnd)) return "0,00";

        for (CardSale c : this.vendas) {
            LocalDate data = LocalDate.parse(Validate.valiDate(c.getData()), formato);
            if (data.isEqual(dateInit) ||
                    (data.isAfter(dateInit) && data.isBefore(dateEnd))) {
                sales += c.getHoras();
            }
        }

        if (sales == 0) return "0,00";
        return Double.toString(sales).replace(".",",") + "0";
    }

    @Override
    public void setSalario (String salario) {
        this.salarioMensal = salario;
    }

    @Override
    public String getSalario() {
        return salarioMensal;
    }

    @Override
    public String getTipo() {
        return "comissionado";
    }

    public Double getSalarioFixado(){
        return Math.floor((Utils.quitValue(getSalario())*12D/52D)*2D * 100)/100F;
    }

    public Double getComissoes(String dataInicial, String dataFinal) throws Exception{
        double allVendas = Utils.quitValue(getVendasRealizadas(dataInicial, dataFinal));
        double comissoes = Utils.quitValue(getTaxa());
        Double percentual = allVendas * comissoes;
        return Math.floor(percentual*100)/100F;
    }

    public double getBruto(String dataInicial, String dataFinal) throws Exception{
        return  getSalarioFixado() + getComissoes(dataInicial, dataFinal);
    }

    public ArrayList<CardSale> getVendas(){
        return vendas;
    }

    public void setVendas(ArrayList<CardSale> vendas) {
        this.vendas = vendas;
    }
}
