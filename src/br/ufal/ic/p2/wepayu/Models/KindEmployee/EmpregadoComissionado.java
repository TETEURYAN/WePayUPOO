package br.ufal.ic.p2.wepayu.Models.KindEmployee;

import br.ufal.ic.p2.wepayu.Exceptions.ExceptionErrorMessage;
import br.ufal.ic.p2.wepayu.Models.KindCard.CardSale;
import br.ufal.ic.p2.wepayu.Models.Empregado;
import br.ufal.ic.p2.wepayu.Models.MembroSindicato;
import br.ufal.ic.p2.wepayu.Utils.Utils;
import br.ufal.ic.p2.wepayu.Utils.Validate;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/*
    Classe referente ao Empregado Comissionado no Modelo Relacional
 */

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

    public EmpregadoComissionado(String nome, String endereco, String salarioMensal, String taxaDeComissao) { //Construtor do Empregado Comissionado
        super(nome, endereco);
        this.taxaComissao = taxaDeComissao;
        this.salarioMensal = salarioMensal;
        this.vendas = new ArrayList<CardSale>();
    }

    public String getSalarioMensal() {
        return salarioMensal;
    }

    public Double getSalarioFixo(){
        return Math.floor((Utils.quitValue(getSalario())*12D/52D)*2D * 100)/100F;
    }

    public String getTaxa() {
        return taxaComissao;
    }

    public void setTaxa(String taxaDeComissao) {
        this.taxaComissao = taxaDeComissao;
    }

    public void addVenda(String dataString, String valor) throws Exception { // Método para adicionar venda ao cartão de vendas
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

    public String getVendasRealizadas(String dataInicial, String dataFinal) throws Exception { // Método para calcular as horas realizadas

        double sales = 0;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate dateInit = null;
        LocalDate dateEnd = null;

        try {//Tentativa de validar hora inicial
            dateInit = LocalDate.parse(Validate.valiDate(dataInicial), formato);
        } catch (Exception e) {throw new Exception("Data inicial invalida.");}

        try{//Tentativa de calidar hora final
            dateEnd = LocalDate.parse(Validate.valiDate(dataFinal), formato);
        } catch (Exception e) {throw new Exception("Data final invalida.");}

        if (dateInit.isAfter(dateEnd)) throw new ExceptionErrorMessage("Data inicial nao pode ser posterior aa data final.");
        if (dateInit.isEqual(dateEnd)) return "0,00";

        for (CardSale c : this.vendas) { //For each em cada cartão de vendas
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

    public Double getDescontos(String dataInicial, String dataFinal) throws Exception{ //método para calcular descontos
        Double total = 0d;

        int dias = Utils.getIntervaloDias(dataInicial, dataFinal) + 1;

        if (getSindicato() != null) {
            MembroSindicato membro = getSindicato();
            total = membro.getTaxasServico(dataInicial, dataFinal) + dias*membro.getAdicionalSindicato();
        }

        return total;
    }
    public Double getComissoes(String dataInicial, String dataFinal) throws Exception{ //Métodos para calcular as comissões
        double allVendas = Utils.quitValue(getVendasRealizadas(dataInicial, dataFinal));
        double comissoes = Utils.quitValue(getTaxa());
        Double percentual = allVendas * comissoes;
        return Math.floor(percentual*100)/100F;
    }

    public double getBruto(String dataInicial, String dataFinal) throws Exception{
        return  getSalarioFixado() + getComissoes(dataInicial, dataFinal);
    }

    public Object[] getDataLine(String dataInicial, String data) throws Exception{// Método para calcular a linha de dados de cada EmpregadoComissionado

        List<Double> valores = new ArrayList<>();

        // Inicializa o vetor com as informações
        valores.add(getSalarioFixo());
        valores.add(Utils.quitValue(getVendasRealizadas(dataInicial, data)));
        valores.add(getComissoes(dataInicial, data));
        valores.add(getBruto(dataInicial, data));
        valores.add(getBruto(dataInicial, data));
        valores.add(getDescontos(dataInicial, data));
        valores.add(valores.get(3) - valores.get(4));

        // Cria strings dos dados numéricos para inserção na folha de pagamento
        String fixo = Utils.doubleToString(valores.get(0), false);
        String vendas = Utils.doubleToString(valores.get(1), false);
        String comissoes = Utils.doubleToString(valores.get(2), false);
        String bruto = Utils.doubleToString(valores.get(3), false);
        String descontos = Utils.doubleToString(valores.get(4), false);
        String liquido = Utils.doubleToString(valores.get(5), false);

        // Cria String da linha correspondente aos dados na folha de pagamento
        String linha = String.format("%-21s %8s %8s %8s %13s %9s %15s %s", getNome(),
                fixo, vendas, comissoes, bruto, descontos, liquido, getDataPayment());

        return new Object[]{linha, valores};
    }

    public ArrayList<CardSale> getVendas(){
        return vendas;
    }

    public void setVendas(ArrayList<CardSale> vendas) {
        this.vendas = vendas;
    }
}
