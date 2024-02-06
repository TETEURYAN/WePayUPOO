package br.ufal.ic.p2.wepayu.utils;

import br.ufal.ic.p2.wepayu.Exceptions.ExceptionErrorMessage;
import br.ufal.ic.p2.wepayu.Managment.Manage;
import br.ufal.ic.p2.wepayu.Models.Empregado;
import br.ufal.ic.p2.wepayu.Models.Syndicate;

import java.time.Year;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Validate {
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

    public static void validEmployEmploy(Empregado e) throws Exception {
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