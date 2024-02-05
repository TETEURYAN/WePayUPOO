package br.ufal.ic.p2.wepayu.Managment;
import br.ufal.ic.p2.wepayu.Exception.AtributoException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.NomeException;
import br.ufal.ic.p2.wepayu.Exception.TipoNaoAplicavalExcpetion;
import br.ufal.ic.p2.wepayu.Utils.Validate;
import br.ufal.ic.p2.wepayu.models.ServiceCard;
import br.ufal.ic.p2.wepayu.models.TypesEmpregados.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.Utils.Utils;
import br.ufal.ic.p2.wepayu.models.Services.Syndicate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.lang.*;

import static java.lang.String.valueOf;

public class Manage {
    static int interator = 0;
    private static HashMap<String, Empregado> mapaNomes = new HashMap<String, Empregado> ();
//    private static Syndicate sindicato = new Syndicate();
    private static HashMap<String,ArrayList<ServiceCard>> services = new HashMap<>();

    public static String createEmploy(Empregado trabalhador) throws EmpregadoNaoExisteException {
        String example = String.valueOf(interator++);
        mapaNomes.put(example, trabalhador);
        return example;
    }
    public static String viewEmploy(String trabalhador, String atributo) throws Exception {
        if(contains(trabalhador)){
            trabalhador = viewEmployByName(trabalhador,1);
        }
        if(Validate.isNull(trabalhador)){
            throw new TipoNaoAplicavalExcpetion("Identificacao do empregado nao pode ser nula.");
        } else if (atributo.equalsIgnoreCase("nome")) {
            return getEmpregado(trabalhador).getNome();
        } else if (atributo.equalsIgnoreCase("endereco")) {
            return getEmpregado(trabalhador).getEndereco();
        } else if (atributo.equalsIgnoreCase("tipo")) {
            return getEmpregado(trabalhador).getTipo();
        } else if (atributo.equalsIgnoreCase("salario")) {
            return Utils.toNumber(getEmpregado(trabalhador));
        } else if (atributo.equalsIgnoreCase("sindicalizado")) {
            return String.valueOf(getEmpregado(trabalhador).getSind());
        }else if(getEmpregado(trabalhador) instanceof EmpregadoComissionado example && atributo.equalsIgnoreCase("comissao")) {
            return String.valueOf(EmpregadoComissionado.getComissao());
        }else if(Validate.isNotAtributo(atributo)){
            throw new AtributoException("Atributo nao existe.");
        }else{
            throw new EmpregadoNaoExisteException();
        }
    }

    public static String viewEmployByName(String nome, int indice) throws Exception{
        int key = 0;
        for (Map.Entry<String, Empregado> entry : mapaNomes.entrySet()) {
            Empregado empregado = entry.getValue();
            String chave = entry.getKey();
            if(empregado.getNome().equalsIgnoreCase(nome)){
                key++;
            }
            if(key == indice)
                return chave;
        }
        throw new NomeException("Nao ha empregado com esse nome.");
    }


    public static void removeEmploy(String id) throws Exception{
        if(Validate.isNull(id)) throw new TipoNaoAplicavalExcpetion("Identificacao do empregado nao pode ser nula.");
        if(mapaNomes.containsKey(id))
            mapaNomes.remove(id);
        else throw new EmpregadoNaoExisteException();
    }

    public static void changeEmploy(String id, String atributo, boolean valor) throws Exception {
        Empregado e = getEmpregado(id);
        if(atributo.equalsIgnoreCase("sindicalizado") && e.getSind() == true){
            e.setSind(valor);
        }
        mapaNomes.put(id, e);
    }

    public static void changeEmploy(String id, String atributo, boolean valor, String sindicateID, String additional) throws Exception {
        Empregado e = getEmpregado(id);
        if(atributo.equalsIgnoreCase("sindicalizado") && e.getSind() == false){
            e.setSind(valor);
        }
        e.setSindicateID(sindicateID);
        e.setAdditionalSindicate(additional);
        e.setSindicato(sindicateID, additional);
        mapaNomes.put(id, e);
    }

    public static void changeEmploy(String id, String atributo, String valor) throws Exception {
        Empregado e = getEmpregado(id);
        if (atributo.equalsIgnoreCase("nome")) {

        } else if (atributo.equalsIgnoreCase("endereco")) {

        } else if (atributo.equalsIgnoreCase("tipo")) {

        } else if (atributo.equalsIgnoreCase("salario")) {

        } else if (atributo.equalsIgnoreCase("sindicalizado")) {

        }

        mapaNomes.put(id, e);
    }

    private static boolean contains(String nome)throws Exception{

        for (Map.Entry<String, Empregado> entry : mapaNomes.entrySet()) {
            Empregado empregado = entry.getValue();
            String chave = entry.getKey();
            if(empregado.getNome().equalsIgnoreCase(nome)){
                return true;
            }
        }
        return false;
    }

    public static Empregado getEmployBySindicate(String ans)throws Exception{

        for (Map.Entry<String, Empregado> entry : mapaNomes.entrySet()) {
            Empregado e= entry.getValue();
            if(e.getSindicateID().equalsIgnoreCase(ans)){
                return e;
            }
        }
        throw new EmpregadoNaoExisteException();
    }

    public static void addTax(String idSindicate, String dataString, String value) throws Exception {
        float ans = Utils.toFloat(value);
        ArrayList<ServiceCard> example = services.get(idSindicate);
        if(example == null){
            example = new ArrayList<ServiceCard>();
        }
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate dataFormato = LocalDate.parse(dataString, formato);

            example.add(new ServiceCard(dataFormato, ans));
//            services.replace(idSindicate, services.get((idSindicate)), example);
            services.put(idSindicate, example);
        } catch (DateTimeParseException e) {
            throw new Exception("Data invalida.");
        }
    }

    public static String seeTax(String id, String dataInicial, String dataFinal) throws Exception {
        String idSincidato = getEmpregado(id).getSindicateID();
        float Taxxing = 0;
        ArrayList<ServiceCard> example = services.get(idSincidato);
        if(example == null){
            return "0,00";
        }
        LocalDate dateInit;
        LocalDate dateEnd;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");
        dateEnd = LocalDate.parse(dataFinal, formato);

        try {
            dateInit = LocalDate.parse(dataInicial, formato);
        } catch (DateTimeParseException e) {
            throw new Exception("Data inicial invalida.");
        }

        if(dateEnd.isBefore(dateInit)){
            throw new Exception("Data inicial nao pode ser posterior aa data final.");
        }

        for (ServiceCard c : example) {
            if (c.getData().isEqual(dateInit) ||
                    (c.getData().isAfter(dateInit) && c.getData().isBefore(dateEnd))){
                Taxxing += c.getTax();
//
            }
        }
        return String.format("%.2f",Taxxing).replace(".", ",");
    }

    public void uptadeEmploy(Empregado ans){
        mapaNomes.get(ans);
    }

    public   static Empregado getEmpregado(String id) throws Exception {
        if(Validate.isNull(id)){
            throw new TipoNaoAplicavalExcpetion("Identificacao do empregado nao pode ser nula.");
        }
        if(!mapaNomes.containsKey(id)){
            throw new EmpregadoNaoExisteException();
        }
        return mapaNomes.get(id);
    }
    public static void initManage(){
        nullEmployees();
        mapaNomes = new HashMap<String, Empregado> ();
    }
    private static void nullEmployees(){
        mapaNomes = null;
    }
}