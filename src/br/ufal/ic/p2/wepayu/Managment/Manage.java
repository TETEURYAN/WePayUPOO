package br.ufal.ic.p2.wepayu.Managment;
import br.ufal.ic.p2.wepayu.Exceptions.ExceptionErrorMessage;
import br.ufal.ic.p2.wepayu.Models.Empregado;
import br.ufal.ic.p2.wepayu.Models.Syndicate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Manage implements Serializable {

    public void Manage(){

    }
    public static HashMap<String, Empregado> employee;
    public static int key = 0;

    public static String setEmpregado(Empregado e) {
        key++;
        String ID = Integer.toString(key);
        e.setIDEmploy(ID);
        employee.put(ID,e);
        return Integer.toString(key);
    }

    public static Empregado getEmpregado(String key) {
        return employee.get(key);
    }

    public static String getEmpregadoIDByNome(String nome, int index) throws ExceptionErrorMessage {
        int iterator = 0;

        for (Map.Entry<String, Empregado> entry : employee.entrySet()) {
            Empregado e = entry.getValue();

            if (nome.contains(e.getNome())) iterator++;
            if (iterator == index) return entry.getKey();
        }
        throw new ExceptionErrorMessage("Nao ha empregado com esse nome.");
    }

    public static String getEmpregadoByIDSind(String idSindical) {

        for (Map.Entry<String, Empregado> entry : Manage.employee.entrySet()) {
            Empregado e = entry.getValue();

            Syndicate sindicalizado = e.getSindicato();

            if (sindicalizado != null) {
                if (idSindical.equals(sindicalizado.getIdMembro()))
                    return entry.getKey();
            }
        }

        return null;
    }

    public static void setValue(String key, Empregado e) {
        employee.replace(key, e);
    }


}
