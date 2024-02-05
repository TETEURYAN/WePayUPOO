package br.ufal.ic.p2.wepayu.Employee;
import br.ufal.ic.p2.wepayu.Exception.AtributoException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.NomeException;
import br.ufal.ic.p2.wepayu.Exception.TipoNaoAplicavalExcpetion;
import br.ufal.ic.p2.wepayu.Utils.Validate;
import br.ufal.ic.p2.wepayu.models.TypesEmpregados.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.Utils.Utils;

import java.util.*;
import java.lang.*;

import static java.lang.String.valueOf;

public class ManageEmployee {
    static int interator = 0;
    private static HashMap<String, Empregado> mapaNomes = new HashMap<String, Empregado> ();
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



    public   static Empregado getEmpregado(String id) throws EmpregadoNaoExisteException {
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