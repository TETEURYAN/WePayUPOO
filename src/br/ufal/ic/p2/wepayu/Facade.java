package br.ufal.ic.p2.wepayu;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.NomeNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.EnderecoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.TipoInvalidoException;
import br.ufal.ic.p2.wepayu.Utils.Validate;
import br.ufal.ic.p2.wepayu.Employee.ManageEmployee;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.Types.EmpregadoComissionado;

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

    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws Exception {
        example = new Empregado(nome, endereco, tipo, salario, false);
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