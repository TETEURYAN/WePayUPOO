package br.ufal.ic.p2.wepayu;
import br.ufal.ic.p2.wepayu.Employee.ManageEmployee;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.TypesEmpregados.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.TypesEmpregados.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.TypesEmpregados.EmpregadoHorista;


import java.util.ArrayList;

public class Facade {
    private static Empregado example= null;

    private static void zerarExample(){
        example = null;
    }

    public void zerarSistema() {
        ManageEmployee.initManage();
    };

    public String getAtributoEmpregado(String id, String atributo) throws Exception {
        return ManageEmployee.viewEmploy(id, atributo);
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws Exception {
        if(tipo.equalsIgnoreCase("assalariado")){
            example = new EmpregadoAssalariado(nome, endereco, tipo, salario);
        }else{
            example = new EmpregadoHorista(nome, endereco, tipo, salario);
        }
        String aux = ManageEmployee.createEmploy(example);
        zerarExample();
        return aux;
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws Exception {
        example = new EmpregadoComissionado(nome, endereco, tipo, salario, false, comissao);
        String aux = ManageEmployee.createEmploy(example);
        zerarExample();
        return aux;

    }

    public String getEmpregadoPorNome(String nome, int index) throws Exception {
        return ManageEmployee.viewEmployByName(nome, index);
    }

    public void lancaCartao(String emp, String data, String horas) throws Exception {
        Empregado e = ManageEmployee.getEmpregado(emp);

        if (emp.equals(""))
            throw new Exception("Identificacao do empregado nao pode ser nula.");

        if (e == null)
            throw new Exception("Empregado nao existe.");

        if (e instanceof EmpregadoHorista) {
            ((EmpregadoHorista) e).addRegistro(data, horas);
        } else {
            throw new Exception("Empregado nao eh horista.");
        }
    }
    public static String getHorasNormaisTrabalhadas(String id, String init, String end) throws Exception {
        Empregado e = ManageEmployee.getEmpregado(id);

        if (e instanceof EmpregadoHorista) {
            return ((EmpregadoHorista) e).getHorasNormaisTrabalhadas(init, end);
        }

        throw new Exception("Empregado nao eh horista.");

    }
    public String getHorasExtrasTrabalhadas (String id, String dataIncial, String dataFinal) throws Exception {
        Empregado e = ManageEmployee.getEmpregado(id);

        if (e instanceof EmpregadoHorista) {
            return ((EmpregadoHorista) e).getHorasExtrasTrabalhadas(dataIncial, dataFinal);
        }

        throw new Exception("Empregado nao eh horista.");
    }
    public void encerrarSistema() {

    }

    // Funções para alterar dados dos funcionários
    public void removerEmpregado(String id) throws Exception {
        ManageEmployee.removeEmploy(id);
    }

    public void alteraEmpregado(String id, String atributo, boolean valor) {

    }

    public void alteraEmpregado(String id, String atributo, String valor1, String valor2, String valor3) {

    }


}