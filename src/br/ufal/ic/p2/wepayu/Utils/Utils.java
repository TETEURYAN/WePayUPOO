package br.ufal.ic.p2.wepayu.Utils;

import br.ufal.ic.p2.wepayu.Models.Empregado;

import java.io.*;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public Utils(){

    }
    public static boolean isValidDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate d = LocalDate.parse(date, formatter);
            return false;
        } catch (DateTimeParseException e) {
            return true;
        }
    }

    public static String convertDoubleToString(double value) {
        if (value != (int) value) return Double.toString(value).replace('.', ',');
        else return Integer.toString((int) value);
    }
    public static String convertDoubleToString(double value, int decimalPlaces) {
        return String.format(("%." + decimalPlaces +"f"), value).replace(".", ",");
    }

    public static  String DoubleString(double value){
        return String.format("%.2f", value).replace(".", ",");
    }

    public static double quitValue(String value){
        return Double.parseDouble(value.replace(',', '.'));
    }

    public static List<Double> concatenarListas(List<Double> listaOne, List<Double> listaTwo) throws Exception{
        if(listaOne.isEmpty()) return listaTwo;
        if(listaTwo.isEmpty()) return listaOne;
        if(listaOne.size() != listaTwo.size()) return null;

        List<Double> soma = new ArrayList<>();
        for(int i = 0; i < listaOne.size(); i++) soma.add(listaOne.get(i) + listaTwo.get(i));
        return soma;
    }

    public static String doubleToString(Double valor, boolean dynamic){;
        DecimalFormat formatter = new DecimalFormat(dynamic ? "#.##" : "0.00");
        return formatter.format(valor);
    }

    public static int getIntervaloDias(String dataInicial, String dataFinal){
        LocalDate Inicial = LocalDate.parse(dataInicial, Validate.formato);
        LocalDate Final = LocalDate.parse(dataFinal, Validate.formato);

        return (int) ChronoUnit.DAYS.between(Inicial, Final);
    }
    public static String lastFriday(LocalDate date){
        String inData = date.minusDays(7).format(Validate.formato);
        return inData;
    }

    public static String nextFriday(String data) {
        LocalDate dataParse = LocalDate.parse(data, Validate.formato);
        LocalDate Inicial = dataParse.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        return Inicial.format(Validate.formato);
    }

    public static boolean lastDayOfMonth(String data){
        LocalDate dataParse = LocalDate.parse(data, Validate.formato);
        LocalDate ultimo = dataParse.with(TemporalAdjusters.lastDayOfMonth());

        return ultimo.equals(dataParse);
    }

    public static String getPrimeiroDiaMes(String data){
        LocalDate dataParse = LocalDate.parse(data, Validate.formato);

        return dataParse.with(TemporalAdjusters.firstDayOfMonth()).
                format(Validate.formato);
    }

    public static void saveXML(HashMap<String, Empregado> empregados, String arquivo){
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(arquivo)))) {
            for (Map.Entry<String, Empregado> entrada : empregados.entrySet()) {
                encoder.writeObject(entrada.getKey());
                encoder.writeObject(entrada.getValue());
            }
            encoder.flush();
        } catch (Exception ignored) {
        }
    }

    public static HashMap<String, Empregado>  carregarEmpregadosDeXML(String arquivo) {
        HashMap<String, Empregado> empregados = new HashMap<>();
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(arquivo)))) {
            Object obj;
            while (true) {
                try {
                    obj = decoder.readObject();
                    if (obj instanceof String) {
                        String chave = (String) obj;
                        obj = decoder.readObject();
                        if (obj instanceof Empregado) {
                            empregados.put(chave, (Empregado) obj);
                        }
                    }
                } catch (Exception ignored) {
                    break; // Não há mais objetos para ler
                }
            }
        } catch (Exception ignored) {
        }
        return empregados;
    }

}
