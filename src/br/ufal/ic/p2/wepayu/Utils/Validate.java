package br.ufal.ic.p2.wepayu.Utils;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.HashSet;
import br.ufal.ic.p2.wepayu.Utils.Utils;

public class Validate {
    public static boolean isNull(String texto) {

        if ((texto == null) || (texto.replace(" ", "").equals(""))){
            return true;
        }
        return false;
    }
    public static boolean isNotType(String texto) {
        Set<String> tiposValidos = new HashSet<>();
        tiposValidos.add("assalariado");
        tiposValidos.add("horista");
        tiposValidos.add("comissionado");
        return !tiposValidos.contains(texto.toLowerCase());
    }

    public static boolean isNotSalary(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c) && c != '.' && c != ',') {
                return true;
            }
        }
        return false;
    }

    public static boolean isNegative(String str) {
        if (str.charAt(0) != '-') {
            return false;
        }
        for (int i = 1; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    public static boolean isNotAtributo(String texto) {
        Set<String> tiposValidos = new HashSet<>();
        tiposValidos.add("nome");
        tiposValidos.add("endereco");
        tiposValidos.add("tipo");
        tiposValidos.add("salario");
        tiposValidos.add("sindicalizado");
        tiposValidos.add("comissao");
        return !tiposValidos.contains(texto.toLowerCase());
    }

    public static boolean isValidDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate d = LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
