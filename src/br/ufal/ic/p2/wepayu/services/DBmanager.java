package br.ufal.ic.p2.wepayu.services;
import br.ufal.ic.p2.wepayu.models.*;
import br.ufal.ic.p2.wepayu.models.FactoryEmployee.FactoryEmployee;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.services.XMLStrategy.XMLEmpregado;
import br.ufal.ic.p2.wepayu.utils.Utils;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DBmanager {

    private static DBmanager session;
    public static HashMap<String, Empregado> empregados;

    private static FactoryEmployee fabrica;
    public static int key;

    private DBmanager(){
        this.empregados = readEmpregados();
        this.fabrica = new FactoryEmployee();

    }

    public static DBmanager getDatabase() {
        if(session == null)
        {
            session = new DBmanager();
        }
        return session;
    }

    public static FactoryEmployee getFabrica(){
        return fabrica;
    }

    public static String add(Empregado e) {
        key++;
        String id = Integer.toString(key);
        empregados.put(id, e);
        return id;
    }

    public static Empregado getEmpregado(String key) {
        return empregados.get(key);
    }

    public static HashMap<String, EmpregadoHorista> getEmpregadoHoristas() {

        HashMap <String, EmpregadoHorista> empregadoHoristas = new HashMap<String,EmpregadoHorista>();

        for (Map.Entry<String, Empregado> e: empregados.entrySet()) {

            Empregado empregado = e.getValue();

            if (empregado.getTipo().equals("horista")) {
                empregadoHoristas.put(e.getKey(), (EmpregadoHorista) empregado);
            }
        }

        return empregadoHoristas;
    }

    public static HashMap<String, EmpregadoComissionado> getEmpregadoComissionado() {

        HashMap <String, EmpregadoComissionado> empregadoHoristas = new HashMap<String,EmpregadoComissionado>();

        for (Map.Entry<String, Empregado> e: empregados.entrySet()) {

            Empregado empregado = e.getValue();

            if (empregado.getTipo().equals("comissionado")) {
                empregadoHoristas.put(e.getKey(), (EmpregadoComissionado) empregado);
            }
        }

        return empregadoHoristas;
    }

    public static HashMap<String, EmpregadoAssalariado> getEmpregadoAssalariado() {

        HashMap <String, EmpregadoAssalariado> empregadoHoristas = new HashMap<String,EmpregadoAssalariado>();

        for (Map.Entry<String, Empregado> e: empregados.entrySet()) {

            Empregado empregado = e.getValue();

            if (empregado.getTipo().equals("assalariado")) {
                empregadoHoristas.put(e.getKey(), (EmpregadoAssalariado) empregado);
            }
        }

        return empregadoHoristas;
    }

    public static String getEmpregadoPorIdSindical (String idSindical) {

        for (Map.Entry<String, Empregado> entry : DBmanager.empregados.entrySet()) {
            Empregado e = entry.getValue();

            Sindicato sindicalizado = e.getSindicalizado();

            if (sindicalizado != null) {
                if (idSindical.equals(sindicalizado.getIdMembro()))
                    return entry.getKey();
            }
        }

        return null;
    }

    public static void setValue(String key, Empregado e) { empregados.replace(key, e); }

    public void delete(String emp) throws Exception {
        Empregado e = Utils.validEmpregado(emp);

        if (e == null)
            return;

        File file = new File( emp + ".xml" );

        if (file.exists()) {
            System.out.println("Empredado deletado!");
            file.delete();
        }

        empregados.remove(emp);
    }

    public HashMap<String, Empregado> readEmpregados() {

        HashMap<String, Empregado> empregados = new HashMap<>();

        for (int i = 1; i <= 1000; i++) {
            String id = Integer.toString(i);

            Empregado empregado = read(id + ".xml");

            if (empregado != null) {
                empregados.put(id, empregado);
                DBmanager.key = i;
            }

        }

        return empregados;
    }


    public Empregado read(String path) {

        File file = new File(path);

        if (file.exists()) {
            try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(path)))) {
                Empregado empregado = (Empregado) decoder.readObject();

                return empregado;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void initSystem () {
        empregados = new HashMap<>();
        key = 0;
    }
    public static void deleteFilesXML () {

        for (int i = 1; i <= 1000; i++) {
            File file = new File( i + ".xml" );

            if (file.exists()) {
                file.delete();
            } else {
                return;
            }
        }
    }

    public static void deleteFolhas () {
        File[] folhas = new File("./").listFiles();

        for (File f : folhas) {
            if (f.getName().endsWith(".txt")) {
                // Excluir o arquivo
                f.delete();
            }
        }
    }

    public void deleteSystem() {
        initSystem();
        deleteFilesXML();
        deleteFolhas();
    }

    public void finishSystem(){
        XMLEmpregado xml = new XMLEmpregado();
        xml.save(DBmanager.empregados);
    }

}


