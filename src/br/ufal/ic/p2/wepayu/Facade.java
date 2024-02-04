package br.ufal.ic.p2.wepayu;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.employee.ManageEmployee;
import br.ufal.ic.p2.wepayu.models.Empregado;

import java.util.ArrayList;

public class Facade {
    private static Empregado example= null;
    private static void zerarExample(){
        example = null;
    }

    public void zerarSistema() {
//        ManageEmployee.initManage();
    };

    public String getAtributoEmpregado(String emp, String atributo) throws Exception {
        return ManageEmployee.viewEmploy(emp, atributo);
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException {
        example = new Empregado(nome, endereco, tipo, salario, false);
        String aux = ManageEmployee.createEmploy(example);
        zerarExample();
        return aux;

    }

    public void encerrarSistema() {
        // Salvar dados em disco ou outro meio persistente
    }

    // Funções para alterar dados dos funcionários
    public void alteraEmpregado(String id, String atributo, String valor1) {

    }

    public void alteraEmpregado(String id, String atributo, boolean valor) {

    }

    public void alteraEmpregado(String id, String atributo, String valor1, String valor2, String valor3) {

    }


}