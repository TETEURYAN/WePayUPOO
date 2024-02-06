package br.ufal.ic.p2.wepayu.Models.KindEmployee;

import br.ufal.ic.p2.wepayu.Exceptions.ExceptionErrorMessage;
import br.ufal.ic.p2.wepayu.Models.KindCard.CardPoint;
import br.ufal.ic.p2.wepayu.Models.Empregado;
import br.ufal.ic.p2.wepayu.utils.Utils;
import br.ufal.ic.p2.wepayu.utils.Validate;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class EmpregadoHorista extends Empregado {

    private String salarioPorHora;
    private ArrayList<CardPoint> cartao;

    public EmpregadoHorista(String nome, String endereco, String salarioPorHora) {
        super(nome, endereco);
        this.salarioPorHora = salarioPorHora;
        this.cartao = new ArrayList<CardPoint>();
    }

    public String getSalarioPorHora() {
        return salarioPorHora;
    }

    public void addRegistro(String dataString, String horas) throws ExceptionErrorMessage {

        if (Double.parseDouble(horas.replace(",", ".")) <= 0) {
            throw new ExceptionErrorMessage("Horas devem ser positivas.");
        }

        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate dataFormato = LocalDate.parse(dataString, formato);

            this.cartao.add(new CardPoint(dataFormato, Double.parseDouble(horas.replace(",", "."))));
        } catch (DateTimeParseException e) {
            throw new ExceptionErrorMessage("Data invalida.");
        }

    }

    public String getHorasNormaisTrabalhadas(String dataInicial, String dataFinal) throws Exception {

        double horaExtra = 0;
        LocalDate dateInit;
        LocalDate dateEnd;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");

        try {dateInit = LocalDate.parse(Validate.valiDate(dataInicial), formato);}
        catch (Exception e) {throw new Exception("Data inicial invalida.");}

        try{dateEnd = LocalDate.parse(Validate.valiDate(dataFinal), formato);}
        catch (Exception e) {throw new Exception("Data final invalida.");}
        dateEnd = LocalDate.parse(dataFinal, formato);

        if (dateInit.isAfter(dateEnd)) throw new ExceptionErrorMessage("Data inicial nao pode ser posterior aa data final.");
        if (dateInit.isEqual(dateEnd)) return "0";

        for (CardPoint c : cartao) {
            if (c.getData().isEqual(dateInit) ||
                    (c.getData().isAfter(dateInit) && c.getData().isBefore(dateEnd))) {

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

    public String getHorasExtrasTrabalhadas(String dataIncial, String dataFinal) {
        double horaExtra = 0;

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");

        LocalDate dateInit = LocalDate.parse(dataIncial, formato);
        LocalDate dateEnd = LocalDate.parse(dataFinal, formato);

        for (CardPoint c : cartao) {
            if (c.getData().isEqual(dateInit) ||
                    (c.getData().isAfter(dateInit) && c.getData().isBefore(dateEnd))) {

                if (c.getHoras() > 8) {
                    horaExtra += (c.getHoras() - 8.0);
                }
            }
        }

        if (horaExtra != (int) horaExtra) return Double.toString(horaExtra).replace(".", ",");
        return Integer.toString((int) horaExtra);
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
}
