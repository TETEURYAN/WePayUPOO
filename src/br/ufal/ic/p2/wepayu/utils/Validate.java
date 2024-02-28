package br.ufal.ic.p2.wepayu.utils;

import br.ufal.ic.p2.wepayu.Exceptions.DataException;
import br.ufal.ic.p2.wepayu.Exceptions.Employ.AtributoException;
import br.ufal.ic.p2.wepayu.Exceptions.Employ.EmpregadoException;
import br.ufal.ic.p2.wepayu.Exceptions.ExceptionErrorMessage;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.Sindicato;
import br.ufal.ic.p2.wepayu.services.DBmanager;


import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Biblioteca destinada a validar atributos de forma booleana de outras classes

 */

public class Validate {

    private static DBmanager session = null;
    private Validate(){
        this.session = DBmanager.getDatabase();
    }
    public static DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy"); //formato universal de data a ser adotado pelas outras classes

    //Método para ver se uma data é existente
    public static String valiDate(String data) throws Exception{
        Pattern pattern = Pattern.compile("([0-3]?[0-9])/(0?[1-9]|1[0-2])/(\\d{4})");
        Matcher matcher = pattern.matcher(data);

        if(!matcher.matches()) throw new DataException();
        else{
            int dia = Integer.parseInt(matcher.group(1));
            int mes = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));

            if (mes == 2) {
                if((Year.isLeap(year) && dia > 29) || dia > 28) throw new DataException();
            } else if(mes == 5 || mes == 6 || mes == 9 || mes == 11) {
                if (dia > 30) throw new DataException();
            } else{
                if(dia > 31) throw new DataException();
            }
        }
        return data;
    }

    //Método para converter uma data em string para data em LocalDate
    public static LocalDate toData(String data) throws
            Exception{
        LocalDate date;
        date = LocalDate.parse(data, formato);
        return date;
    }

    //Seção de métodos para trablhar com comissionado
    public static String lastDayComissionado(String data) { // Método para puxar o dia de pagamento do comissionado
        LocalDate dataParse = LocalDate.parse(data, formato);
        LocalDate dataInit = dataParse.minusDays(13);
        return dataInit.format(formato);
    }
    public static boolean validPayComissionado(String data) { // Método para checar se é um dia válido para pagar o comissionado
        LocalDate dataParse = LocalDate.parse(data, formato);
        DayOfWeek dayWeek = dataParse.getDayOfWeek();

        if (dayWeek != DayOfWeek.FRIDAY) return false;

        LocalDate initGeralDate = LocalDate.of(2005, 1, 1);
        long diferencaEmDias = ChronoUnit.DAYS.between(initGeralDate, dataParse);

        return (diferencaEmDias + 1) % 14 == 0;
    }

    public static boolean validLastDay(String data){// Método para validar o último dia
        LocalDate dataParse = LocalDate.parse(data, formato);
        LocalDate ultimo = dataParse.with(TemporalAdjusters.lastDayOfMonth());

        return ultimo.equals(dataParse);
    }

    public static void validEmpoySettings(String nome, String endereco, String tipo, String salario, String comissao) throws ExceptionErrorMessage, AtributoException {//Método para validar os atributos de umn empregado
        if (nome.isEmpty())
            throw new AtributoException("Nome nao pode ser nulo.");

        if (endereco.isEmpty())
            throw new AtributoException("Endereco nao pode ser nulo.");
        if (tipo.equals("abc"))
            throw new AtributoException("Tipo invalido.");

        if (tipo.equals("horista") || tipo.equals("assalariado"))
            throw new AtributoException("Tipo nao aplicavel.");

        if (salario.isEmpty())
            throw new AtributoException("Salario nao pode ser nulo.");

        if (!salario.matches("[0-9,-]+"))
            throw new AtributoException("Salario deve ser numerico.");

        if (salario.contains("-"))
            throw new AtributoException("Salario deve ser nao-negativo.");

        if (comissao.isEmpty())
            throw new AtributoException("Comissao nao pode ser nula.");

        if (!comissao.matches("[0-9,-]+"))
            throw new AtributoException("Comissao deve ser numerica.");

        if (comissao.contains("-"))
            throw new AtributoException("Comissao deve ser nao-negativa.");
    }

    public static boolean validFriday(String data){// Métodos para validar se é uma sexta-feira
        LocalDate dataParse = LocalDate.parse(data, formato);
        return dataParse.getDayOfWeek() == DayOfWeek.FRIDAY;
    }

    public static void validIDEmploy(String id) throws Exception { //método para avaliar se o ID é válido
        if(id.equals("")){
            throw new AtributoException("Identificacao do empregado nao pode ser nula.");
        }
    }

    public static void validIDSyndicate(String id) throws Exception { //Métopdo para avaliar de a ID no Sindicato é válido
        if(id.equals("")){
            throw new AtributoException("Identificacao do membro nao pode ser nula.");
        }
    }

    public static void validEmployInfo(String nome, String endereco, String tipo, String salario) throws ExceptionErrorMessage, AtributoException {
        if (nome.isEmpty())
            throw new AtributoException("Nome nao pode ser nulo.");

        if (endereco.isEmpty())
            throw new AtributoException("Endereco nao pode ser nulo.");

        if (tipo.equals("abc"))
            throw new AtributoException("Tipo invalido.");

        if (tipo.equals("comissionado"))
            throw new AtributoException("Tipo nao aplicavel.");

        if (salario.isEmpty())
            throw new AtributoException("Salario nao pode ser nulo.");

        if (!salario.matches("[0-9,-]+"))
            throw new AtributoException("Salario deve ser numerico.");

        if (salario.contains("-"))
            throw new AtributoException("Salario deve ser nao-negativo.");
    }

//    public static void validHour(String data) throws ExceptionErrorMessage {
//        if (Utils.quitValue(data.replace(",", ".")) <= 0) {
//            throw new ExceptionErrorMessage("Horas devem ser positivas.");
//        }
//    }
    public static void validEmploy(Empregado e) throws Exception {//Método para avaliar a existência de um empregado
        if(e == null){
            throw new EmpregadoException("Empregado nao existe.");
        }
    }

    public static void validEmploySyndicate(Empregado e) throws Exception {//Método para avaliar a existêcia de um empregado no sindicato
        if(e == null){
            throw new EmpregadoException("Membro nao existe.");
        }
    }

    public static void validValue(double value) throws Exception {//Método para avaliar se um valor é válido
        if (value <= 0) {
            throw new AtributoException("Valor deve ser positivo.");
        }
    }

    public static void validSalario(String valor) throws Exception{//Método para avaliar se o salário é válido
        if (valor.isEmpty()) throw new AtributoException("Salario nao pode ser nulo.");
        try {
            double salario = Double.parseDouble(valor.replace(',', '.'));
            if (salario <= 0.0) throw new AtributoException("Salario deve ser nao-negativo.");

        } catch (NumberFormatException ex) {
            throw new AtributoException("Salario deve ser numerico.");
        }
    }

    public static void validComissao(String valor, Empregado e) throws Exception {//Método para avaliar se a comissão é válida
        if (valor.isEmpty()) throw new AtributoException("Comissao nao pode ser nula.");
        try {
            double comissao = Double.parseDouble(valor.replace(',', '.'));
            if (comissao <= 0.0) throw new AtributoException("Comissao deve ser nao-negativa.");
        } catch (NumberFormatException ex) {
            throw new AtributoException("Comissao deve ser numerica.");
        }
        if (!(e instanceof EmpregadoComissionado))
            throw new AtributoException("Empregado nao eh comissionado.");
    }

    public static boolean notInconsistency(String idSindicato){//Método para avaliar se há um membro com aquele ID
        for (Map.Entry<String, Empregado> entry : session.empregados.entrySet()) {
            Sindicato m = entry.getValue().getSindicalizado();
            if (m != null)
                if (m.getIdMembro().equals(idSindicato))
                    return true;
        }
        return false;
    }

    public static void syndicalWarm(String idSindicato, String taxaSindical) throws Exception { //Método para avaliar os atributos do sindicato
        if (idSindicato.isEmpty()) throw new AtributoException("Identificacao do sindicato nao pode ser nula.");
        if (taxaSindical.isEmpty()) throw new AtributoException("Taxa sindical nao pode ser nula.");

        try {
            double taxaSindicalNumber = Double.parseDouble(taxaSindical.replace(",", "."));

            if (taxaSindicalNumber <= 0.0) throw new AtributoException("Taxa sindical deve ser nao-negativa.");

        } catch (NumberFormatException ex) {
            throw new AtributoException("Taxa sindical deve ser numerica.");
        }
    }

}
