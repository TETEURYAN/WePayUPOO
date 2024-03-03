package br.ufal.ic.p2.wepayu.services.XMLStrategy;

import br.ufal.ic.p2.wepayu.models.Agenda;
import br.ufal.ic.p2.wepayu.services.Settings;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;

public class XMLAgenda {
    public void save(ArrayList<Agenda> ag) {

        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(Settings.PATH_AGENDA+".xml")))) {
            // Gravar o objeto Java no arquivo XML
            encoder.writeObject(ag);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
