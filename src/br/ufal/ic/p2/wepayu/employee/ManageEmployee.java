package br.ufal.ic.p2.wepayu.employee;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
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
        if (atributo.equalsIgnoreCase("nome")) {
            return getEmpregado(trabalhador).getNome();
        } else if (atributo.equalsIgnoreCase("endereco")) {
            return getEmpregado(trabalhador).getEndereco();
        } else if (atributo.equalsIgnoreCase("tipo")) {
            return getEmpregado(trabalhador).getTipo();
        } else if (atributo.equalsIgnoreCase("salario")) {
//                String example = String.valueOf(String.format("%.2f",(float)getEmpregado(trabalhador).getSalario()));
//                example = example.replace(".",",");


                return Utils.toNumber(getEmpregado(trabalhador));
        } else if (atributo.equalsIgnoreCase("sindicalizado")) {
            return String.valueOf(getEmpregado(trabalhador).getSind());
        }else {
            throw new EmpregadoNaoExisteException();
        }

    }

    private  static Empregado getEmpregado(String id) throws EmpregadoNaoExisteException {
        if(!mapaNomes.containsKey(id)){
            throw new EmpregadoNaoExisteException();
        }
        return mapaNomes.get(id);
    }
    public static void initManage(){
        nullEmployees();
    }
    private static void nullEmployees(){
        mapaNomes = null;
    }
}
