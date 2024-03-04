package br.ufal.ic.p2.wepayu.dao.KindDao;

import br.ufal.ic.p2.wepayu.exceptions.Query.DontExistNameException;
import br.ufal.ic.p2.wepayu.models.Agenda;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.services.FactoryEmpregados.FactoryEmpregados;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.TiposPagamento.Banco;
import br.ufal.ic.p2.wepayu.models.TiposPagamento.Correios;
import br.ufal.ic.p2.wepayu.models.TiposPagamento.EmMaos;
import br.ufal.ic.p2.wepayu.models.MetodoPagamento;
import br.ufal.ic.p2.wepayu.models.Sindicato;
import br.ufal.ic.p2.wepayu.services.Memento;
import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.services.Settings;
import br.ufal.ic.p2.wepayu.utils.Utils;
import br.ufal.ic.p2.wepayu.utils.Validate;

import java.util.Map;

/**
 * Data Access Object (DAO) para gerenciamento de Empregado
 * @author teteuryan faite100
 */

public class EmpregadoDao {

    private final DBmanager session; //Banco de dados
    private FactoryEmpregados fabrica ;// Fábrica de Empregados
    private Memento backup;//Memento

    /**
     * Cria uma instância de {@link EmpregadoDao} com base em uma sessão
     * @param session {@link DBmanager} do banco de dados
     * Usa tambem os estados armazenados em {@link Memento}
     */
    public EmpregadoDao(DBmanager session, Memento backup) {
        this.session = session;
        this.fabrica = session.getFabrica();
        this.backup = backup;
    }

    /**
     * Método de criação de um novo {@link Empregado} com validação de 4 atributos
     */
    public String create(String nome, String endereco, String tipo, String salario) throws Exception {
        Validate.validEmployInfo(nome, endereco, tipo, salario);

        double salarioFormato = Utils.toDouble(salario);
        Empregado e = fabrica.makeEmployee(nome, endereco, tipo, salarioFormato);

        return session.add(e);

    }

    /**
     * Método de criação de um novo {@link Empregado} com validação de 5 atributos
     */
    public String create(String nome, String endereco, String tipo, String salario, String comissao) throws Exception {
        Validate.validEmployInfo(nome, endereco, tipo, salario, comissao);

        Validate.validSalario(salario);
        double salarioFormato = Utils.toDouble(salario);
        double comissaoFormato = Utils.toDouble(comissao);

        Empregado e = fabrica.makeEmployee(nome, endereco, tipo, salarioFormato, comissaoFormato);
        return session.add(e);
    }

    /**
     * Método de alteração de um novo {@link Empregado} com validação de 3 atributos
     */
    public void update(String emp, String atributo, String valor) throws Exception {
        Empregado e = session.getEmpregado(emp);

        Validate.validAtributo(atributo);

        switch (atributo) {
            case "nome" -> {
                    Validate.validNome(valor);
                    e.setNome(valor);
            }
            case "endereco" -> {
                    Validate.validEndereco(valor);
                    e.setEndereco(valor);
            }
            case "salario" -> {
                Validate.validSalario(valor);
                e.setSalario(Utils.toDouble(valor));
            }
            case "sindicalizado" -> {
                Validate.validAtributoSyndicate(valor);
                e.setSindicalizado(null);
            }
            case "comissao" -> {
                Validate.validComissao(valor,e.getTipo());
                double comissao = Utils.toDouble(valor);
                ((EmpregadoComissionado) e).setTaxaDeComissao(comissao);
            }
            case "tipo" -> {

                Validate.validAlterarTipo(e, valor);
                String nome = e.getNome();
                String endereco = e.getEndereco();
                double salario = e.getSalario();

                switch (valor) {
                    case "horista" -> session.setValue(emp,fabrica.makeEmployee(nome, endereco, Settings.HORISTA, salario) );
                    case "assalariado" -> session.setValue(emp, fabrica.makeEmployee(nome, endereco, Settings.ASSALARIADO, salario));
                    case "comissionado" -> session.setValue(emp, fabrica.makeEmployee(nome, endereco, Settings.COMISSIONADO, salario, 0));
                }
            }

            case "metodoPagamento" -> {

                if (!Utils.validMetodoPagamento(valor))
                    return;

                if (valor.equals("correios")) e.setMetodoPagamento(new Correios());
                else if (valor.equals("emMaos")) e.setMetodoPagamento(new EmMaos());
            }
            case "agendaPagamento" ->{
                Validate.validAgenda(valor, session);
                e.setAgenda(new Agenda(valor));
            }
        }
    }

    /**
     * Método de alteração de um novo {@link Empregado} com validação de 4 atributos
     */
    public void update(String emp, String atributo, String valor, String sal) throws Exception {
        Empregado e = session.getEmpregado(emp);

        Validate.validAtributo(atributo);
        String nome = e.getNome();
        String endereco = e.getEndereco();

        if (valor.equals("comissionado")) {
            double salario = e.getSalario();
            Validate.validComissao(sal, valor);
            double comissao = Utils.toDouble(sal);

            session.setValue(emp, fabrica.makeEmployee(nome, endereco, valor, salario, comissao));
        } else if (valor.equals("horista")) {
            Validate.validSalario(sal);
            double novoSalario = Utils.toDouble(sal);

            session.setValue(emp, fabrica.makeEmployee(nome, endereco, valor, novoSalario) );
        }
    }

    /**
     * Método de alteração de um novo {@link Empregado} com validação de 5 atributos
     */
    public void update(String emp, String atributo, String valor, String idSindicato, String taxaSindical) throws Exception {

        Validate.validsyndicalWarm(idSindicato, taxaSindical);
        double taxaSindicalNumber = Utils.toDouble(taxaSindical);

        if (atributo.equals("sindicalizado") && valor.equals("true")) {
                Validate.validDuplicateID(idSindicato);
                Empregado e = session.getEmpregado(emp);

                e.setSindicalizado(new Sindicato(idSindicato, taxaSindicalNumber));
        }
    }

    /**
     * Método de alteração de um novo {@link Empregado} com validação de 6 atributos
     */
    public void update(String emp, String atributo, String tipo, String banco, String agencia, String contaCorrente) throws Exception {
        Empregado e = session.getEmpregado(emp);

        Validate.validAtributo(atributo);
        Validate.validWayBanco(banco, agencia, contaCorrente);
            e.setMetodoPagamento(new Banco(banco, agencia, contaCorrente));
    }

    /**
     * Método de procura de um {@link Empregado} a partir do nome
     */
    public String getByName(String nome, int indice) throws Exception {

        int count = 0;

        for (Map.Entry<String, Empregado> entry : DBmanager.empregados.entrySet()) {
            Empregado e = entry.getValue();
            if (nome.contains(e.getNome())) count++;
            if (count == indice) return entry.getKey();
        }
        throw new DontExistNameException();

    }

    /**
     * Método de procura de um atributo {@link Empregado} a partir do ID do Empregado
     */
    public String getAtributo(String emp, String atributo) throws Exception {

        Empregado e = session.getEmpregado(emp);
        Validate.validAtributo(atributo);

        switch (atributo) {

            case "nome" -> {return e.getNome();}
            case "tipo" -> {return e.getTipo();}
            case "salario" -> {return Utils.convertDoubleToString(e.getSalario(), 2);}
            case "endereco" -> {return e.getEndereco();}
            case "comissao" -> {
                    Validate.validComissionado(e);
                    return Utils.convertDoubleToString(((EmpregadoComissionado) e).getTaxaDeComissao(), 2);
            }
            case "metodoPagamento" -> {return e.getMetodoPagamento().getMetodoPagamento();}
            case "banco", "agencia", "contaCorrente" -> {
                MetodoPagamento metodoPagamento = e.getMetodoPagamento();
                Validate.validBankPaymentWay(metodoPagamento);
                return (atributo.equals("banco")) ? ((Banco) metodoPagamento).getBanco() : (atributo.equals("agencia")) ?  ((Banco) metodoPagamento).getAgencia() : ((Banco) metodoPagamento).getContaCorrente();
            }
            case "sindicalizado" -> {return (e.getSindicalizado() == null) ? "false" : "true";}
            case "idSindicato", "taxaSindical" -> {
                Sindicato ms = e.getSindicalizado();
                Validate.validSyndicate(ms);
                return (atributo.equals("idSindicato")) ? ms.getIdMembro() : Utils.convertDoubleToString(e.getSindicalizado().getTaxaSindical(), 2);
            }
            case "agendaPagamento" -> {return  e.getAgenda().getDescricao();}
        }
        return null;
    }
}
