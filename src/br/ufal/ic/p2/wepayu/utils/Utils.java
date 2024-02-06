package br.ufal.ic.p2.wepayu.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Utils {
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
}
