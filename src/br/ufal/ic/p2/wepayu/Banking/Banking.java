package br.ufal.ic.p2.wepayu.Banking;

import br.ufal.ic.p2.wepayu.Managment.Manage;
import br.ufal.ic.p2.wepayu.Models.Empregado;
import br.ufal.ic.p2.wepayu.Models.KindCard.CardService;
import br.ufal.ic.p2.wepayu.Utils.Utils;


import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Banking {

    static String enderecoListaEmpregados = "./listaEmpregados.xml";
    public Banking(){

    }
    private static HashMap<String, Empregado> empregados = (HashMap<String, Empregado>) Utils.carregarEmpregadosDeXML("./listaEmpregados.xml");
    public static List<Empregado> GiveEmployXML(String arquivo) {
        List<Empregado> empregados = new ArrayList<>();
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(arquivo)))) {
            Object obj;
            while (true) {
                try {
                    obj = decoder.readObject();
                    if (obj instanceof Empregado) {
                        empregados.add((Empregado) obj);
                    }
                } catch (Exception ignored) {
                    break; // Não há mais objetos para ler
                }
            }
        } catch (Exception ignored) {
        }
        return empregados;
    }

    public static void setEmpregado(String key, Empregado empregado){
        empregados.put(key, empregado);
        Utils.saveXML(empregados, "./listaEmpregados.xml");
    }

    public static void addEmpregado(String ID, Empregado empregado) {
        empregados.put(ID, empregado);
        Utils.saveXML(empregados, "./listaEmpregados.xml");
    }

    public static String getEnderecoListaEmpregados(){
        return enderecoListaEmpregados;
    }


    public static void updateEmployByXML(){
        Manage.employee = Utils.carregarEmpregadosDeXML(enderecoListaEmpregados);
    }
    public static void removeEmploy(Empregado empregado) {
        String key = empregado.getIDEmploy();
        empregados.remove(key);
        Utils.saveXML(empregados, enderecoListaEmpregados);
    }

    public static void zerarSystem(){
        empregados.clear();
        Manage.employee = new HashMap<>();
        Manage.key = 0;
        Utils.saveXML(empregados, enderecoListaEmpregados);
    }
}
