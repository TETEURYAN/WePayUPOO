package br.ufal.ic.p2.wepayu.dao.KindDao;

import br.ufal.ic.p2.wepayu.managment.ParcialManagment;
import br.ufal.ic.p2.wepayu.exceptions.ExceptionEmpregado;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.FactoryEmployee.FactoryEmployee;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.KindPayment.Banco;
import br.ufal.ic.p2.wepayu.models.KindPayment.Correios;
import br.ufal.ic.p2.wepayu.models.KindPayment.EmMaos;
import br.ufal.ic.p2.wepayu.models.MetodoPagamento;
import br.ufal.ic.p2.wepayu.models.Sindicato;
import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.utils.Utils;

import java.util.Map;

public class EmpregadoDao {

    private final DBmanager session;
    private FactoryEmployee fabrica = ParcialManagment.getFabrica();

    public EmpregadoDao(DBmanager session) {
        this.session = session;
    }

    public String create(String nome, String endereco, String tipo, String salario) throws Exception {
        if (!Utils.validCriarEmpregado(nome, endereco, tipo, salario)) return null;

        if (!Utils.validTipoNotComissionado(tipo)) return null;

        double salarioFormato = Utils.validSalario(salario);

        if (salarioFormato <= 0) return null;

        return session.add(fabrica.makeEmployee(nome, endereco, tipo, salarioFormato));

    }

    public String create(String nome, String endereco, String tipo, String salario, String comissao) throws Exception {
        if (!Utils.validCriarEmpregado(nome, endereco, tipo, salario, comissao))
            return null;

        if (!Utils.validTipoComissionado(tipo))
            return null;

        double salarioFormato = Utils.validSalario(salario);

        if (salarioFormato <= 0)
            return null;

        double comissaoFormato = Utils.validComissao(comissao);

        if (comissaoFormato <= 0)
            return null;

        return session.add(fabrica.makeEmployee(nome, endereco, tipo, salarioFormato, comissaoFormato));
    }

    public void update(String emp, String atributo, String valor) throws Exception {

        Empregado e = Utils.validEmpregado(emp);

        if (e == null)
            return;

        if (!Utils.validGetAtributo(emp, atributo)) {
            return;
        }

        switch (atributo) {
            case "nome" -> {
                if (Utils.validNome(valor))
                    e.setNome(valor);
            }
            case "endereco" -> {
                if (Utils.validEndereco(valor))
                    e.setEndereco(valor);
            }
            case "salario" -> {
                double salario = Utils.validSalario(valor);

                if (salario < 0)
                    return;

                e.setSalario(salario);
            }
            case "sindicalizado" -> {
                if (Utils.validSindicalizado(valor)) {
                    e.setSindicalizado(null);
                }
            }
            case "comissao" -> {

                double comissao = Utils.validComissao(valor);

                if (comissao < 0)
                    return;

                if (!Utils.empregadoIsNotComissionado(e))
                    return;

                ((EmpregadoComissionado) e).setTaxaDeComissao(comissao);
            }
            case "tipo" -> {

                if (Utils.validAlterarTipo(e, valor)) {
                    return;
                }

                String nome = e.getNome();
                String endereco = e.getEndereco();
                double salario = e.getSalario();

                switch (valor) {
                    case "horista" -> session.setValue(emp, new EmpregadoHorista(nome, endereco, salario));
                    case "assalariado" -> session.setValue(emp, new EmpregadoAssalariado(nome, endereco, salario));
                    case "comissionado" -> session.setValue(emp, new EmpregadoComissionado(nome, endereco, salario, 0));
                }
            }

            case "metodoPagamento" -> {

                if (!Utils.validMetodoPagamento(valor))
                    return;

                if (valor.equals("correios")) e.setMetodoPagamento(new Correios());

                else if (valor.equals("emMaos")) e.setMetodoPagamento(new EmMaos());

            }
        }
    }

    // 4 variaveis
    public void update(String emp, String atributo, String valor, String sal) throws Exception {
        Empregado e = Utils.validEmpregado(emp);

        if (e == null)
            return;

        if (!Utils.validGetAtributo(emp, atributo))
            return;

        String nome = e.getNome();
        String endereco = e.getEndereco();

        if (valor.equals("comissionado")) {
            double salario = e.getSalario();
            double comissao = Utils.validComissao(sal);

            if (comissao < 0)
                return;

            DBmanager.setValue(emp, new EmpregadoComissionado(nome, endereco, salario, comissao));

        } else if (valor.equals("horista")) {
            double novoSalario = Utils.validSalario(sal);

            if (novoSalario < 0)
                return;

            session.setValue(emp, new EmpregadoHorista(nome, endereco, novoSalario));
        }
    }

    // 5 variaveis
    public void update(String emp, String atributo, String valor, String idSindicato, String taxaSindical) throws Exception {

        if (Utils.validIdSindical(idSindicato))
            return;

        double taxaSindicalNumber = Utils.validTaxaSindical(taxaSindical);

        if (taxaSindicalNumber <= 0.0)
            return;

        if (atributo.equals("sindicalizado") && valor.equals("true")) {

            if (Utils.sindicalizarEmpregado(idSindicato)) {
                Empregado e = Utils.validEmpregado(emp);

                if (e == null) return;

                e.setSindicalizado(new Sindicato(idSindicato, taxaSindicalNumber));
            }
        }
    }

    // 6 variaveis
    public void update(String emp, String atributo, String tipo, String banco, String agencia, String contaCorrente) throws Exception {
        Empregado e = Utils.validEmpregado(emp);

        if (e == null)
            return;

        if (!Utils.validGetAtributo(emp, atributo))
            return;

        if (Utils.validBanco(banco, agencia, contaCorrente))
            e.setMetodoPagamento(new Banco(banco, agencia, contaCorrente));
    }


    public String getByName(String nome, int indice) throws Exception {

        int count = 0;

        for (Map.Entry<String, Empregado> entry : DBmanager.empregados.entrySet()) {

            Empregado e = entry.getValue();

            if (nome.contains(e.getNome()))
                count++;

            if (count == indice)
                return entry.getKey();
        }

        ExceptionEmpregado ex = new ExceptionEmpregado();

        ex.msgEmpregadoNaoExistePorNome();

        return null;
    }

    public String getAtributo(String emp, String atributo) throws Exception {

        Empregado e = Utils.validEmpregado(emp);

        if (e == null)
            return null;

        if (!Utils.validGetAtributo(emp, atributo))
            return null;

        switch (atributo) {

            case "nome" -> {
                return e.getNome();
            }
            case "tipo" -> {
                return e.getTipo();
            }
            case "salario" -> {
                return Utils.convertDoubleToString(e.getSalario(), 2);
            }
            case "endereco" -> {
                return e.getEndereco();
            }
            case "comissao" -> {

                if (Utils.empregadoIsNotComissionado(e))
                    return Utils.convertDoubleToString(((EmpregadoComissionado) e).getTaxaDeComissao(), 2);

                return null;
            }

            case "metodoPagamento" -> {
                return e.getMetodoPagamento().getMetodoPagamento();
            }

            case "banco", "agencia", "contaCorrente" -> {
                MetodoPagamento metodoPagamento = e.getMetodoPagamento();

                if (!Utils.metodoPagamentoIsBanco(metodoPagamento))
                    return null;

                if (atributo.equals("banco")) return ((Banco) metodoPagamento).getBanco();
                if (atributo.equals("agencia")) return ((Banco) metodoPagamento).getAgencia();

                return ((Banco) metodoPagamento).getContaCorrente();
            }

            case "sindicalizado" -> {
                if (e.getSindicalizado() == null) return "false";
                else return "true";
            }

            case "idSindicato", "taxaSindical" -> {
                Sindicato ms = e.getSindicalizado();

                if (!Utils.validSindicato(ms))
                    return null;

                if (atributo.equals("idSindicato")) return ms.getIdMembro();

                return Utils.convertDoubleToString(e.getSindicalizado().getTaxaSindical(), 2);
            }

        }

        return null;
    }
}
