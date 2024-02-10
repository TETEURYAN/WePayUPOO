package br.ufal.ic.p2.wepayu.Managment;
import br.ufal.ic.p2.wepayu.Exceptions.ExceptionErrorMessage;
import br.ufal.ic.p2.wepayu.Models.Empregado;
import br.ufal.ic.p2.wepayu.Models.MembroSindicato;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/*
    Classe referente ao controle dos Empregados

 */
public class Manage implements Serializable {

    public void Manage(){

    }
    public static HashMap<String, Empregado> employee; //Estrutura de dados usada para relacionar <String, Empregados>
    public static int key = 0;

    public static String setEmpregado(Empregado e) {
        key++;
        String ID = Integer.toString(key);
        e.setIDEmploy(ID);
        employee.put(ID,e);
        return Integer.toString(key);
    }

    public static Empregado getEmpregado(String key) { //Método que retorna o Empregado a partir de um ID/chave
        return employee.get(key);
    }

    public static String getEmpregadoIDByNome(String nome, int index) throws ExceptionErrorMessage {// método para encontar o ID Empregado a partir do nome do Empregado
        int iterator = 0;

        for (Map.Entry<String, Empregado> entry : employee.entrySet()) { // For eaaaaach na HashMap para encontrar o Empregado solicitado
            Empregado e = entry.getValue();

            if (nome.contains(e.getNome())) iterator++; //Caso haja mais de um empregado com o mesmo nome, ele percorrerá até encontrar o requerido pelo index
            if (iterator == index) return entry.getKey();
        }
        throw new ExceptionErrorMessage("Nao ha empregado com esse nome.");
    }

    public static String getEmpregadoByIDSind(String idSindical) {// método que retorna o ID de um Empregado a patri do ID Sindical

        for (Map.Entry<String, Empregado> entry : Manage.employee.entrySet()) {
            Empregado e = entry.getValue();

            MembroSindicato sindicalizado = e.getSindicato();// Localizando o sindicado de cada membro

            if (sindicalizado != null) {//Averiguando se o sindicato é nulo
                if (idSindical.equals(sindicalizado.getIdMembro()))
                    return entry.getKey();
            }
        }

        return null;
    }

    public static void setValue(String key, Empregado e) {//Método para atualizar valor na hashmap de empregados
        employee.replace(key, e);
    }


}
