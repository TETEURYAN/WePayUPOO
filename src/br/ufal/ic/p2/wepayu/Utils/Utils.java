package br.ufal.ic.p2.wepayu.Utils;

import br.ufal.ic.p2.wepayu.Managment.Manage;
import br.ufal.ic.p2.wepayu.Models.Empregado;
import br.ufal.ic.p2.wepayu.Models.KindEmployee.EmpregadoHorista;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
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

    public static String lastFriday(LocalDate date){
        String inData = date.minusDays(7).format(Validate.formato);
        return inData;
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
