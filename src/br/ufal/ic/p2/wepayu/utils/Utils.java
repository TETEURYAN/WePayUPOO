package br.ufal.ic.p2.wepayu.utils;

import br.ufal.ic.p2.wepayu.Exceptions.Employ.EmpregadoException;
import br.ufal.ic.p2.wepayu.exceptions.Banco.*;
import br.ufal.ic.p2.wepayu.exceptions.Employ.TypeEmpregadoException;
import br.ufal.ic.p2.wepayu.exceptions.Query.MidDataException;
import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.models.*;
import br.ufal.ic.p2.wepayu.services.Settings;

import java.io.File;
import java.io.FileWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * Classe para o uso de algoritmos de formatação, linearização
 * ordenação e entre outros
 */
public class Utils {

    public static Map<String, String> sortHashMap(HashMap<String, String> hashMap) {

        List<Map.Entry<String, String>> entryList = new ArrayList<>(hashMap.entrySet());

        entryList.sort(Map.Entry.comparingByValue());

        Map<String, String> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static boolean validMetodoPagamento(String valor) throws Exception {//Método para saber se  meio de pagamento é valido
        if (valor.equals("correios") || valor.equals("emMaos") || valor.equals("banco"))
            return true;
        else
            throw new PaymentWayInvalidException();
    }

    public static boolean validTipoEmpregado(Empregado e, String tipo) throws Exception {//M<étodo que averigua se o tipo de Empregado é valido

        if (e.getTipo().equals(tipo)) {
            return true;
        }else throw new TypeEmpregadoException(tipo);
    }

    public static double toDouble(String salario) throws Exception {// Metodo de conversão simples de String para double, sem tratamento
        return Double.parseDouble(salario.replace(",", "."));
    }


    public static double validHoras(String horas) throws Exception {// método para validar as horas dada uma string

        if (horas.isEmpty()) throw new EmpregadoException("Horas devem ser positivas.");
        if (!horas.matches("[0-9,-]+"))throw new EmpregadoException("Horas devem ser numericas.");

        double horasFormato = Double.parseDouble(horas.replace(",", "."));
        if (horasFormato <= 0) throw new EmpregadoException("Horas devem ser positivas.");

        return horasFormato;
    }

    public static LocalDate validData(String data, String tipo) throws Exception {//Método para validar a data e retornar um LocalData da data correta

        String[] blocos = data.split("/");

        int d = Integer.parseInt(blocos[0]);
        int m = Integer.parseInt(blocos[1]);
        int y = Integer.parseInt(blocos[2]);

        if (m > 12 || m < 1) {
            throw new MidDataException(tipo);
        }

        YearMonth yearMonth = YearMonth.of(y, m);

        if (d > yearMonth.lengthOfMonth()) {
            throw new MidDataException(tipo);
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");
        return LocalDate.parse(data, formato);
    }


    public static String convertDoubleToString(double value) {//Converte de double para string sem considerar as casas decimais
        if (value != (int) value) return Double.toString(value).replace('.', ',');
        else return Integer.toString((int) value);
    }

    public static String convertDoubleToString(double value, int decimalPlaces) {//Concerte Double para String cosniderando as casas decimais
        return String.format(("%." + decimalPlaces + "f"), value).replace(".", ",");
    }

    public static boolean ultimoDiaMes(LocalDate data){//Método para saber se é o ultimo dia do mes
        LocalDate ultimo = data.with(TemporalAdjusters.lastDayOfMonth());
        return ultimo.equals(data);
    }

    public static String getPrimeiroDiaMes(LocalDate data){//Método para calcular o primeiro dia do mes
        return data.with(TemporalAdjusters.firstDayOfMonth()).format(Settings.formatter);
    }

    public static String getUltimoDiaDePagamento(LocalDate data, Agenda agenda){// método para calcilar o ultimo dia de pagmento
        LocalDate dataInicial;
        int dia = agenda.getDia();
        if(agenda.getTipo().equals("mensal")){
            if(dia == -1) {
                return getPrimeiroDiaMes(data);
            }
            dataInicial = data.minusMonths(1);
        }else{
            if(agenda.getSemana() > 0){
                int semana = agenda.getSemana();
                dataInicial = data.minusDays((semana * 7L) - 1);
            }else{
                dataInicial = data.with(TemporalAdjusters.previous(DayOfWeek.of(dia)));
            }
        }
        return dataInicial.format(Settings.formatter);
    }

    public static boolean diaDePagamento(LocalDate data, Agenda agenda){// Método para saber se é dia de pagamento
        DayOfWeek diaDaSemana = data.getDayOfWeek();

        if(agenda.getTipo().equals("mensal")){
            int dia = agenda.getDia();
            if(dia == -1){
                return ultimoDiaMes(data);
            }
            else return data.getDayOfMonth() == dia;
        }
        else{
            int dia = agenda.getDia();
            if(agenda.getSemana() > 0){

                if(data.getDayOfWeek() != DayOfWeek.of(dia)) return false;

                LocalDate dataContratacao = LocalDate.of(2005, 1, 1);
                long totalSemanas = ChronoUnit.WEEKS.between(dataContratacao, data) + 1;
                return totalSemanas % agenda.getSemana() == 0;
            }
            else{
                if(diaDaSemana == DayOfWeek.of(dia)) return true;
            }
        }
        return false;
    }

    public static String EspacosEsquerda(String value, int length) {

        if (value.length() >= length) {
            return value;
        }

        int padLength = length - value.length();

        return " ".repeat(padLength) + value;
    }


    public static String EspacosDireita(String value, int length) {

        if (value.length() >= length) {
            return value;
        }

        int padLength = length - value.length();

        return value + " ".repeat(padLength);
    }

    public static String EspacosDireita(String value, int length, String padChar) {

        if (value.length() >= length) {
            return value;
        }

        int padLength = length - value.length();

        return value + padChar.repeat(padLength);
    }


    public static ArrayList<Agenda> getPadraoAgenda() throws Exception {
        List<Agenda> agendas = new ArrayList<>();
        for(String agenda: Settings.PADRAO_AGENDA){
            agendas.add(new Agenda(agenda));
        }
        return (ArrayList<Agenda>) agendas;
    }

        public static void writeEmpregadoHeader(FileWriter writter, String type) {
            try {

                writter.write("=".repeat(127) + "\n");
                writter.write(EspacosDireita(String.format("===================== %s ", type), 127, "=") + "\n");
                writter.write("=".repeat(127) + "\n");
                if (type.equals("HORISTAS")) {
                    writter.write("Nome                                 Horas Extra Salario Bruto Descontos Salario Liquido Metodo\n");
                    writter.write("==================================== ===== ===== ============= ========= =============== ======================================" + "\n");
                }

                if (type.equals("ASSALARIADOS")) {
                    writter.write("Nome                                             Salario Bruto Descontos Salario Liquido Metodo\n");
                    writter.write("================================================ ============= ========= =============== ======================================" + "\n");
                }

                if (type.equals("COMISSIONADOS")) {
                    writter.write("Nome                  Fixo     Vendas   Comissao Salario Bruto Descontos Salario Liquido Metodo\n");
                    writter.write("===================== ======== ======== ======== ============= ========= =============== ======================================\n");
                }

            } catch (Exception e) {
                System.out.println("Erro ao criar arquivo");
            }

        }

        public static void escreveHorista(FileWriter writter, String nome, double horas, double extras, double bruto, double descontos, double liquido, String metodo) {
            String line = EspacosDireita(nome, 36);

            line += EspacosEsquerda(convertDoubleToString(horas), 6);
            line += EspacosEsquerda(convertDoubleToString(extras), 6);
            line += EspacosEsquerda(convertDoubleToString(bruto, 2), 14);
            line += EspacosEsquerda(convertDoubleToString(descontos, 2), 10);
            line += EspacosEsquerda(convertDoubleToString(liquido, 2), 16);
            line += " " + metodo + "\n";

            try {
                writter.write(line);
            } catch (Exception e) {
                System.out.println("Erro durante a escrita do funcionário Horista");
            }

        }

        public static void escreveAssalariado(FileWriter writter, String nome, double bruto, double descontos, double liquido, String metodo ) {
            String line = EspacosDireita(nome, 48);


            line += EspacosEsquerda(convertDoubleToString(bruto, 2), 14);
            line += EspacosEsquerda(convertDoubleToString(descontos, 2), 10);
            line += EspacosEsquerda(convertDoubleToString(liquido, 2), 16);
            line += " " + metodo + "\n";

            try {
                writter.write(line);
            } catch (Exception e) {
                System.out.println("Erro durante a escrita do funcionário");
            }

        }

        public static void escreveComissionado(FileWriter writter, String nome, double fixo, double vendas, double comissao, double bruto, double descontos, double liquido, String metodo) {
            String line = EspacosDireita(nome, 21);

            line += EspacosEsquerda(convertDoubleToString(fixo, 2), 9);
            line += EspacosEsquerda(convertDoubleToString(vendas, 2), 9);
            line += EspacosEsquerda(convertDoubleToString(comissao, 2), 9);
            line += EspacosEsquerda(convertDoubleToString(bruto, 2), 14);
            line += EspacosEsquerda(convertDoubleToString(descontos, 2), 10);
            line += EspacosEsquerda(convertDoubleToString(liquido, 2), 16);
            line += " " + metodo + "\n";

            try {
                writter.write(line);
            } catch (Exception e) {
                System.out.println("Erro durante a escrita do funcionário");
            }
        }

        public static <T extends Empregado> HashMap<String, String> ordenaEmpregadosByName(HashMap<String, T> empregados){
            HashMap<String, String> sortedEmpregados = new HashMap<>();

            for (Map.Entry<String, T> entry : empregados.entrySet()) {
                sortedEmpregados.put(entry.getKey(), entry.getValue().getNome());
            }

            List<Map.Entry<String, String>> entryList = new ArrayList<>(sortedEmpregados.entrySet());

            entryList.sort(Map.Entry.comparingByValue());

            HashMap<String, String> sortedMap = new LinkedHashMap<>();
            for (Map.Entry<String, String> entry : entryList) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }

            return sortedMap;
        }
    }

