package br.ufal.ic.p2.wepayu.Models.KindEmployee;

import br.ufal.ic.p2.wepayu.Exceptions.ExceptionErrorMessage;
import br.ufal.ic.p2.wepayu.Models.KindCard.CardSale;
import br.ufal.ic.p2.wepayu.Models.Empregado;
import br.ufal.ic.p2.wepayu.utils.Utils;
import br.ufal.ic.p2.wepayu.utils.Validate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class EmpregadoComissionado extends Empregado {

    private String taxaComissao;
    private String salarioMensal;
    private ArrayList<CardSale> vendas;

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
            this.vendas.add(new CardSale(dataFormato, value));
        } catch (DateTimeParseException e) {
            throw new ExceptionErrorMessage("Data invalida.");
        }
    }

    public String getVendas(String dataInicial, String dataFinal) throws Exception {

        double vendasRealizadas = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

        LocalDate dateInit = null;
        LocalDate dateEnd = null;

        try {dateInit = LocalDate.parse(Validate.valiDate(dataInicial), formatter);}
        catch (Exception e) {throw new Exception("Data inicial invalida.");}

        try{dateEnd = LocalDate.parse(Validate.valiDate(dataFinal), formatter);}
        catch (Exception e) {throw new Exception("Data final invalida.");}

        if (dateInit.isAfter(dateEnd)) {
            throw new ExceptionErrorMessage("Data inicial nao pode ser posterior aa data final.");
        }

        if (dateInit.isEqual(dateEnd)) {
            return "0,00";
        }

        for (CardSale c : this.vendas) {
            if (c.getData().isEqual(dateInit) ||
                    (c.getData().isAfter(dateInit) && c.getData().isBefore(dateEnd))) {
                vendasRealizadas += c.getHoras();
            }
        }

        if (vendasRealizadas == 0) {
            return "0,00";
        }

        return Double.toString(vendasRealizadas).replace(".",",") + "0";
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
}
