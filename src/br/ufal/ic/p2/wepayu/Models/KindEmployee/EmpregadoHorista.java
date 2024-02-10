package br.ufal.ic.p2.wepayu.Models.KindEmployee;

import br.ufal.ic.p2.wepayu.Exceptions.ExceptionErrorMessage;
import br.ufal.ic.p2.wepayu.Models.KindCard.CardPoint;
import br.ufal.ic.p2.wepayu.Models.Empregado;
import br.ufal.ic.p2.wepayu.Models.KindCard.CardService;
import br.ufal.ic.p2.wepayu.Models.MembroSindicato;
import br.ufal.ic.p2.wepayu.Utils.Utils;
import br.ufal.ic.p2.wepayu.Utils.Validate;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/*
    Classe referente ao Empregado Horista no Modelo Relacional
 */

public class EmpregadoHorista extends Empregado implements Serializable {

    private String salarioPorHora;
    private ArrayList<CardPoint> cartao; //Vetor de cartão de ponto

    public EmpregadoHorista(){

    }

    public EmpregadoHorista(String nome, String endereco, String salarioPorHora) {// Construtor
        super(nome, endereco);
        this.salarioPorHora = salarioPorHora;
    }

    public String getSalarioPorHora() {
        return salarioPorHora;
    }

    public void addRegistro(String dataString, String horas) throws ExceptionErrorMessage { //Método para adicionar cartão de pnoto ao vetor de cartão de ponto
        if(this.cartao == null){
            this.cartao = new ArrayList<CardPoint>();
        }

        Validate.validHour(horas); //Averigua validade da hora

        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate dataFormato = LocalDate.parse(dataString, formato);

            this.cartao.add(new CardPoint(dataString, Utils.quitValue(horas.replace(",", ".")))); //Insere o cartão de ponto no vetor de pontos
        } catch (DateTimeParseException e) {
            throw new ExceptionErrorMessage("Data invalida.");
        }

    }

    public String getHorasNormaisTrabalhadas(String dataInicial, String dataFinal) throws Exception { // Método para puxar as horas normais trabalhadas no vetor de cartõde pontos

        if(this.cartao == null){
            this.cartao = new ArrayList<CardPoint>();
        }

        double horaExtra = 0;
        LocalDate dateInit;
        LocalDate dateEnd;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");

        try {dateInit = LocalDate.parse(Validate.valiDate(dataInicial), formato);}//Averigua a data inicial
        catch (Exception e) {throw new Exception("Data inicial invalida.");}

        try{dateEnd = LocalDate.parse(Validate.valiDate(dataFinal), formato);}//Averigua a data final
        catch (Exception e) {throw new Exception("Data final invalida.");}
        dateEnd = LocalDate.parse(dataFinal, formato);

        if (dateInit.isAfter(dateEnd)) throw new ExceptionErrorMessage("Data inicial nao pode ser posterior aa data final.");
        if (dateInit.isEqual(dateEnd)) return "0";

        for (CardPoint c : cartao) {// For each para cada cartão no vetor de cartao de ponto para somar as horas
            LocalDate data = LocalDate.parse(Validate.valiDate(c.getData()), formato);
            if (data.isEqual(dateInit) ||
                    (data.isAfter(dateInit) && data.isBefore(dateEnd))) {

                if (c.getHoras() > 8) {
                    horaExtra += 8.0;
                } else {
                    horaExtra += c.getHoras();
                }
            }
        }

        if (horaExtra != (int) horaExtra) return Utils.DoubleString(horaExtra);
        return Integer.toString((int) horaExtra);
    }

    public String getHorasExtrasTrabalhadas(String dataIncial, String dataFinal) throws Exception { // Método para calcular as horas extras a partir do cartão de ponto
        double horaExtra = 0;

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");

        LocalDate dateInit = LocalDate.parse(dataIncial, formato);
        LocalDate dateEnd = LocalDate.parse(dataFinal, formato);

        for (CardPoint c : cartao) {
            LocalDate data = LocalDate.parse(Validate.valiDate(c.getData()), formato);
            if (data.isEqual(dateInit) ||
                    (data.isAfter(dateInit) && data.isBefore(dateEnd))) {
                if (c.getHoras() > 8) {
                    horaExtra += (c.getHoras() - 8.0);
                }
            }
        }

        if (horaExtra != (int) horaExtra) return Double.toString(horaExtra).replace(".", ",");
        return Integer.toString((int) horaExtra);
    }

    public Double getDescontos(String dataInicial, String dataFinal) throws Exception{// Método para calcular os descontos por meio do sindicato
        Double total = 0d;

        int dias = Utils.getIntervaloDias(dataInicial, dataFinal);

        if (getSindicato() != null) {
            MembroSindicato membro = getSindicato();
            total = membro.getTaxasServico(dataInicial, dataFinal) +
                    dias*membro.getAdicionalSindicato();
        }

        return total;
    }

    public Object[] getDataLine(String dataInicial, String data) throws Exception{// Método para cacular a linha de dados do Empregado Horista para pôr no TXT

        List<Double> valores = new ArrayList<>();

        // Adiciona os dados numéricos a lista de valores
        valores.add(Utils.quitValue(getHorasNormaisTrabalhadas(dataInicial, data)));
        valores.add(Utils.quitValue(getHorasExtrasTrabalhadas(dataInicial, data)));
        valores.add(getBruto(dataInicial, data));

        // Confere se há salario suficiente para retirar os descontos
        Double desconto = getDescontos(dataInicial,data);
        if(valores.get(2) < desconto) {
            valores.add(0D);
            String dataCobranca = Utils.nextFriday(data);
            setTaxaExtra(dataCobranca, desconto);
        }
        else {
            desconto += (getSindicato() == null) ? getSindicato().getClearExtra() : 0D;
            valores.add(desconto);
        }

        // Adiciona dado de salário liquido
        valores.add(valores.get(2) - valores.get(3));

        // Cria strings dos dados numéricos para inserção na folha de pagamento
        String normais = Utils.doubleToString(valores.get(0), true);
        String extras = Utils.doubleToString(valores.get(1), true);
        String bruto = Utils.doubleToString(valores.get(2), false);
        String descontos = Utils.doubleToString(valores.get(3), false);
        String liquido = Utils.doubleToString(valores.get(4), false);

        // Cria String da linha correspondente aos dados na folha de pagamento
        String linha = String.format("%-36s %5s %5s %13s %9s %15s %s", getNome(),
                normais, extras, bruto, descontos, liquido, getDataPayment());

        return new Object[]{linha, valores};
    }


    public Double getBruto (String dataInicial, String dataFinal) throws Exception{ // Método para calcular o salário bruto
        Double horasNormais = Double.parseDouble(getHorasNormaisTrabalhadas(dataInicial, dataFinal));// Total de horas normais
        Double horasExtras = Double.parseDouble(getHorasExtrasTrabalhadas(dataInicial, dataFinal));// Total de horas extras
        return horasNormais*Double.parseDouble(getSalario()) + horasExtras*1.5*Double.parseDouble(getSalario()); //Conta do salário bruto
    }

    @Override
    public void setSalario (String salario) {
        this.salarioPorHora = salario;
    }

    @Override
    public String getSalario() {
        return this.salarioPorHora;
    }

    @Override
    public String getTipo() {
        return "horista";
    }


    public ArrayList<CardPoint> getCartao(){
        return this.cartao;
    }

    public void setCartao(ArrayList<CardPoint> cartao){
        this.cartao = cartao;
    }

    private void setTaxaExtra(String data, Double valor){ // Método para setar mais taxa extra no cartão de pontos
        if(getSindicato() != null){
            CardService extra = new CardService(data,
                    valor);
            getSindicato().addCardService(extra);
        }
    }
}
