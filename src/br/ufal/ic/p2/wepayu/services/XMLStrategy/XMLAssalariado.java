package br.ufal.ic.p2.wepayu.services.XMLStrategy;

import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoAssalariado;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XMLAssalariado {

    public void save(String id, EmpregadoAssalariado empregado) {

        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream( id + ".xml")))) {
            encoder.writeObject(empregado);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
