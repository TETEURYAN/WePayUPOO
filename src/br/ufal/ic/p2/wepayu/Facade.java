package br.ufal.ic.p2.wepayu;
import br.ufal.ic.p2.wepayu.Managment.Manage;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.Services.Syndicate;
import br.ufal.ic.p2.wepayu.models.TypesEmpregados.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.TypesEmpregados.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.TypesEmpregados.EmpregadoHorista;

public class Facade {
    private static Empregado example= null;

    private static void zerarExample(){
        example = null;
    }

    public void zerarSistema() {
        Manage.initManage();
    };

    public String getAtributoEmpregado(String id, String atributo) throws Exception {
        return Manage.viewEmploy(id, atributo);
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws Exception {
        if(tipo.equalsIgnoreCase("assalariado")){
            example = new EmpregadoAssalariado(nome, endereco, tipo, salario);
        }else{
            example = new EmpregadoHorista(nome, endereco, tipo, salario);
        }
        String aux = Manage.createEmploy(example);
        zerarExample();
        return aux;
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws Exception {
        example = new EmpregadoComissionado(nome, endereco, tipo, salario, false, comissao);
        String aux = Manage.createEmploy(example);
        zerarExample();
        return aux;

    }

    public String getEmpregadoPorNome(String nome, int index) throws Exception {
        return Manage.viewEmployByName(nome, index);
    }

    public void lancaCartao(String emp, String data, String horas) throws Exception {
        Empregado e = Manage.getEmpregado(emp);

//        if (emp.equals(""))
//            throw new Exception("Identificacao do empregado nao pode ser nula.");
//
//        if (e == null)
//            throw new Exception("Empregado nao existe.");

        if (e instanceof EmpregadoHorista) {
            ((EmpregadoHorista) e).addRegistroHorista(data, horas);
        } else {
            throw new Exception("Empregado nao eh horista.");
        }
    }
    public static String getHorasNormaisTrabalhadas(String id, String init, String end) throws Exception {
        Empregado e = Manage.getEmpregado(id);

        if (e instanceof EmpregadoHorista) {
            return ((EmpregadoHorista) e).getHorasNormaisTrabalhadas(init, end);
        }

        throw new Exception("Empregado nao eh horista.");

    }
    public String getHorasExtrasTrabalhadas (String id, String dataIncial, String dataFinal) throws Exception {
        Empregado e = Manage.getEmpregado(id);

        if (e instanceof EmpregadoHorista) {
            return ((EmpregadoHorista) e).getHorasExtrasTrabalhadas(dataIncial, dataFinal);
        }

        throw new Exception("Empregado nao eh horista.");
    }

    public void lancaVenda(String id, String data, String valor) throws Exception {
        String ans =  valor.replace(",", ".");
        float value = Float.parseFloat(ans);
        Empregado e = Manage.getEmpregado(id);
        if (e instanceof EmpregadoComissionado) {
            ((EmpregadoComissionado) e).addRegistroComissao(data, value);
        } else {
            throw new Exception("Empregado nao eh comissionado.");
        }
    }

    public String getVendasRealizadas(String id, String dataInicial, String dataFinal) throws Exception {
        Empregado e = Manage.getEmpregado(id);

        if (e instanceof EmpregadoComissionado) {
            return ((EmpregadoComissionado) e).getSales(dataInicial, dataFinal);
        }
        throw new Exception("Empregado nao eh comissionado.");

    }

    public String getTaxasServico(String id, String dataInit, String dataFinal) throws Exception {
        return Manage.seeTax(id, dataInit,dataFinal);
    }

    public void encerrarSistema() {

    }

    public void lancaTaxaServico(String id, String data, String value) throws Exception {
        Manage.addTax(id, data, value);
    }

    // Funções para alterar dados dos funcionários
    public void removerEmpregado(String id) throws Exception {
        Manage.removeEmploy(id);
    }

    public void alteraEmpregado(String id, String atributo, String valor) throws Exception {
        Manage.changeEmploy(id, atributo, valor);
    }

    public void alteraEmpregado(String id, String atributo, boolean valor, String IdSind, String addValue) throws Exception {
        Manage.changeEmploy(id, atributo, valor, IdSind, addValue);
    }
    public void alteraEmpregado(String id, String atributo, boolean valor) throws Exception {
        Manage.changeEmploy(id, atributo, valor);
    }

    public void alteraEmpregado(String id, String atributo, String valor1, String valor2, String valor3) {

    }


}