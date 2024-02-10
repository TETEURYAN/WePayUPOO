package br.ufal.ic.p2.wepayu.Utils;

import br.ufal.ic.p2.wepayu.Exceptions.ExceptionErrorMessage;
import br.ufal.ic.p2.wepayu.Managment.Manage;
import br.ufal.ic.p2.wepayu.Models.Empregado;
import br.ufal.ic.p2.wepayu.Models.KindEmployee.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.Models.Syndicate;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Validate {
    public static DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");
    public static String valiDate(String data) throws Exception{
        Pattern pattern = Pattern.compile("([0-3]?[0-9])/(0?[1-9]|1[0-2])/(\\d{4})");
        Matcher matcher = pattern.matcher(data);

        if(!matcher.matches()) throw new ExceptionErrorMessage("Data invalida.");
        else{
            int dia = Integer.parseInt(matcher.group(1));
            int mes = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));

            if (mes == 2) {
                if((Year.isLeap(year) && dia > 29) || dia > 28) throw new ExceptionErrorMessage("Data invalida.");
            } else if(mes == 5 || mes == 6 || mes == 9 || mes == 11) {
                if (dia > 30) throw new ExceptionErrorMessage("Data invalida.");
            } else{
                if(dia > 31) throw new ExceptionErrorMessage("Data invalida.");
            }
        }
        return data;
    }

    public static LocalDate toData(String data) throws
            Exception{
        LocalDate date;
        date = LocalDate.parse(data, formato);
        return date;
    }

    public static String lastDayComissionado(String data) {
        LocalDate dataParse = LocalDate.parse(data, formato);
        LocalDate dataInit = dataParse.minusDays(13);
        return dataInit.format(formato);
    }
    public static boolean validPayComissionado(String data) {
        LocalDate dataParse = LocalDate.parse(data, formato);
        DayOfWeek dayWeek = dataParse.getDayOfWeek();

        if (dayWeek != DayOfWeek.FRIDAY) return false;

        LocalDate initGeralDate = LocalDate.of(2005, 1, 1);
        long diferencaEmDias = ChronoUnit.DAYS.between(initGeralDate, dataParse);

        return (diferencaEmDias + 1) % 14 == 0;
    }

    public static boolean validLastDay(String data){
        LocalDate dataParse = LocalDate.parse(data, formato);
        LocalDate ultimo = dataParse.with(TemporalAdjusters.lastDayOfMonth());

        return ultimo.equals(dataParse);
    }

    public static boolean validFriday(String data){
        LocalDate dataParse = LocalDate.parse(data, formato);
        return dataParse.getDayOfWeek() == DayOfWeek.FRIDAY;
    }

    public static void validIDEmploy(String id) throws Exception {
        if(id.equals("")){
            throw new ExceptionErrorMessage("Identificacao do empregado nao pode ser nula.");
        }
    }

    public static void validIDSyndicate(String id) throws Exception {
        if(id.equals("")){
            throw new ExceptionErrorMessage("Identificacao do membro nao pode ser nula.");
        }
    }

    public static void validEmployInfo(String nome, String endereco, String tipo, String salario) throws ExceptionErrorMessage {
        if (nome.isEmpty())
            throw new ExceptionErrorMessage("Nome nao pode ser nulo.");

        if (endereco.isEmpty())
            throw new ExceptionErrorMessage("Endereco nao pode ser nulo.");

        if (tipo.equals("abc"))
            throw new ExceptionErrorMessage("Tipo invalido.");

        if (tipo.equals("comissionado"))
            throw new ExceptionErrorMessage("Tipo nao aplicavel.");

        if (salario.isEmpty())
            throw new ExceptionErrorMessage("Salario nao pode ser nulo.");

        if (!salario.matches("[0-9,-]+"))
            throw new ExceptionErrorMessage("Salario deve ser numerico.");

        if (salario.contains("-"))
            throw new ExceptionErrorMessage("Salario deve ser nao-negativo.");
    }

    public static void validEmploy(Empregado e) throws Exception {
        if(e == null){
            throw new ExceptionErrorMessage("Empregado nao existe.");
        }
    }

    public static void validEmploySyndicate(Empregado e) throws Exception {
        if(e == null){
            throw new ExceptionErrorMessage("Membro nao existe.");
        }
    }

    public static void validValue(double value) throws Exception {
        if (value <= 0) {
            throw new ExceptionErrorMessage("Valor deve ser positivo.");
        }
    }

    public static void validSalario(String valor) throws Exception{
        if (valor.isEmpty()) throw new ExceptionErrorMessage("Salario nao pode ser nulo.");
        try {
            double salario = Double.parseDouble(valor.replace(',', '.'));
            if (salario <= 0.0) throw new ExceptionErrorMessage("Salario deve ser nao-negativo.");

        } catch (NumberFormatException ex) {
            throw new ExceptionErrorMessage("Salario deve ser numerico.");
        }
    }

    public static void validComissao(String valor, Empregado e) throws Exception {
        if (valor.isEmpty()) throw new ExceptionErrorMessage("Comissao nao pode ser nula.");
        try {
            double comissao = Double.parseDouble(valor.replace(',', '.'));
            if (comissao <= 0.0) throw new ExceptionErrorMessage("Comissao deve ser nao-negativa.");
        } catch (NumberFormatException ex) {
            throw new ExceptionErrorMessage("Comissao deve ser numerica.");
        }
        if (!(e instanceof EmpregadoComissionado))
            throw new ExceptionErrorMessage("Empregado nao eh comissionado.");
    }

    public static boolean notInconsistency(String idSindicato){
        for (Map.Entry<String, Empregado> entry : Manage.employee.entrySet()) {
            Syndicate m = entry.getValue().getSindicato();
            if (m != null)
                if (m.getIdMembro().equals(idSindicato))
                    return true;
        }
        return false;
    }

    public static void syndicalWarm(String idSindicato, String taxaSindical) throws Exception {
        if (idSindicato.isEmpty()) throw new ExceptionErrorMessage("Identificacao do sindicato nao pode ser nula.");
        if (taxaSindical.isEmpty()) throw new ExceptionErrorMessage("Taxa sindical nao pode ser nula.");

        try {
            double taxaSindicalNumber = Double.parseDouble(taxaSindical.replace(",", "."));

            if (taxaSindicalNumber <= 0.0) throw new ExceptionErrorMessage("Taxa sindical deve ser nao-negativa.");

        } catch (NumberFormatException ex) {
            throw new ExceptionErrorMessage("Taxa sindical deve ser numerica.");
        }
    }

}
