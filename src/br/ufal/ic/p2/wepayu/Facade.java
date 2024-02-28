package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.dao.Manager;
import br.ufal.ic.p2.wepayu.managment.ParcialManagment;
import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.services.SistemaFolha;

public class Facade {

    private ParcialManagment controleV1;
    private Manager controle;

    private DBmanager data;

    private SistemaFolha folha;

    public Facade() {

        this.controleV1 = new ParcialManagment();
        this.data = DBmanager.getDatabase();
        this.controle = new Manager(data);
    }

    public void zerarSistema() {
        this.controleV1.zerarSistema();
    }

    public void encerrarSistema() {
        this.controleV1.encerrarSistema();
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws Exception {
        return controle.getEmpregadoDao().create(nome, endereco, tipo, salario);
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws Exception {
        return controle.getEmpregadoDao().create(nome, endereco, tipo, salario, comissao);
    }

    public void lancaCartao(String emp, String data, String horas) throws Exception {
        controle.getCartaoDao().addCard(emp, data, horas);
    }

    public void lancaVenda(String emp, String data, String valor) throws Exception {
        controle.getVendaDao().addVenda(emp, data, valor);
    }

    public void lancaTaxaServico(String membro, String data, String valor) throws Exception {
        controle.getTaxaDao().addService(membro, data, valor);
    }

    public String getEmpregadoPorNome(String nome, int indice) throws Exception {
        return controle.getEmpregadoDao().getByName(nome, indice);
    }

    public String getAtributoEmpregado(String emp, String atributo) throws Exception {
        return controle.getEmpregadoDao().getAtributo(emp, atributo);
    }

    public String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws Exception {
        return controle.getCartaoDao().getNormalJob(emp, dataInicial, dataFinal);
    }

    public String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal) throws Exception {
        return controle.getCartaoDao().getExtraJob(emp, dataInicial, dataFinal);
    }

    public String getVendasRealizadas(String emp, String dataInicial, String dataFinal) throws Exception {
        return controle.getVendaDao().getVendas(emp, dataInicial, dataFinal);
    }

    public String getTaxasServico(String emp, String dataInicial, String dataFinal) throws Exception {
        return controle.getTaxaDao().getService(emp, dataInicial, dataFinal);
    }

    public void alteraEmpregado(String emp, String atributo, String valor) throws Exception {
        controle.getEmpregadoDao().update(emp, atributo, valor);
    }

    public void alteraEmpregado(String emp, String atributo, String valor, String salario) throws Exception {
        controle.getEmpregadoDao().update(emp, atributo, valor, salario);
    }

    public void alteraEmpregado(String emp, String atributo, String valor, String idSindicato, String taxaSindical) throws Exception {
        controle.getEmpregadoDao().update(emp, atributo, valor, idSindicato, taxaSindical);
    }

    public void alteraEmpregado(String emp, String atributo, String tipo, String banco, String agencia, String contaCorrente) throws Exception {
        controle.getEmpregadoDao().update(emp, atributo, tipo, banco, agencia, contaCorrente);
    }

    public void removerEmpregado(String emp) throws Exception {
        data.delete(emp);
    }

    public String totalFolha(String data) throws Exception {
        return this.controleV1.totalFolha(data);
    }

    public void rodaFolha(String data, String saida) throws Exception {
        this.controleV1.rodaFolha(data, saida);
    }

}
