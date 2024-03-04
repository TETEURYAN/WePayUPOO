package br.ufal.ic.p2.wepayu.services.XMLStrategy;

import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.services.Settings;

import java.beans.XMLEncoder;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class XMLEmpregado {

    public static void save(HashMap<String, Empregado> empregados){
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(Settings.PATH_PERSISTENCIA + ".xml")))) {
            for (Map.Entry<String, Empregado> entrada : empregados.entrySet()) {
                encoder.writeObject(entrada.getKey());
                encoder.writeObject(entrada.getValue());
            }
            encoder.flush();
        } catch (Exception ignored) {
        }
    }
}
