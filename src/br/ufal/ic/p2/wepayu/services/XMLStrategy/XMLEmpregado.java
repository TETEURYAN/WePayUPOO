package br.ufal.ic.p2.wepayu.services.XMLStrategy;

import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoHorista;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XMLEmpregado {

    public void save(HashMap <String, Empregado> empregados) {

        for (Map.Entry<String, Empregado> m: empregados.entrySet()) {
            String id = m.getKey();
            Empregado e = m.getValue();

            if (e.getTipo().equals("horista")) {
                XMLHorista xml = new XMLHorista();

                xml.save(id, (EmpregadoHorista) e);
            }

            if (e.getTipo().equals("comissionado")) {
                XMLComissionado xml = new XMLComissionado();

                xml.save(id, (EmpregadoComissionado) e);
            }

            if (e.getTipo().equals("assalariado")) {
                XMLAssalariado xml = new XMLAssalariado();

                xml.save(id, (EmpregadoAssalariado) e);
            }
        }
    }
}
