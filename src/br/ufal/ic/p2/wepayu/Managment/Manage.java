package br.ufal.ic.p2.wepayu.Managment;
import br.ufal.ic.p2.wepayu.Models.Empregado;
import br.ufal.ic.p2.wepayu.Models.Syndicate;

import java.util.HashMap;
import java.util.Map;

public class Manage {
    public static HashMap<String, Empregado> employee;
    public static int key = 0;

    public static String setEmpregado(Empregado e) {
        key++;
        employee.put(Integer.toString(key),e);
        return Integer.toString(key);
    }

    public static Empregado getEmpregado(String key) {
        return employee.get(key);
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
