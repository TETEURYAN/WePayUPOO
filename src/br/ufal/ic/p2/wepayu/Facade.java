package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Banking.Banking;
import br.ufal.ic.p2.wepayu.Exceptions.Employ.AtributoException;
import br.ufal.ic.p2.wepayu.Exceptions.Employ.EmpregadoException;
import br.ufal.ic.p2.wepayu.Exceptions.ExceptionErrorMessage;
import br.ufal.ic.p2.wepayu.Exceptions.PaymentWay.BankException;
import br.ufal.ic.p2.wepayu.Models.*;
import br.ufal.ic.p2.wepayu.Managment.Manage;
import br.ufal.ic.p2.wepayu.Models.KindCard.CardService;
import br.ufal.ic.p2.wepayu.Models.KindEmployee.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.Models.KindEmployee.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.Models.KindEmployee.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.Models.KindPayment.Bank;
import br.ufal.ic.p2.wepayu.Models.KindPayment.Fedex;
import br.ufal.ic.p2.wepayu.Models.KindPayment.Hands;
import br.ufal.ic.p2.wepayu.Utils.Utils;
import br.ufal.ic.p2.wepayu.Utils.Validate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Facade {
    public Facade(){
        Banking.updateEmployByXML();
    }
    public void zerarSistema() {
        Manage.employee = new HashMap<String, Empregado>();
        Banking.zerarSystem();
    }

    public void encerrarSistema(){
        Utils.saveXML(Manage.employee, Banking.getEnderecoListaEmpregados());
    }

    public void lancaVenda(String id, String data, String horas) throws Exception {
        Empregado e = Manage.getEmpregado(id);

        Validate.validIDEmploy(id);
        Validate.validEmploy(e);

        if (e instanceof EmpregadoComissionado) {
            ((EmpregadoComissionado) e).addVenda(data, horas);
        } else {
            throw new ExceptionErrorMessage("Empregado nao eh comissionado.");
        }
    }

    public void lancaCartao(String emp, String data, String horas) throws Exception {
        Empregado e = Manage.getEmpregado(emp);

        Validate.validIDEmploy(emp);
        Validate.validEmploy(e);

        if (e instanceof EmpregadoHorista) {
            ((EmpregadoHorista) e).addRegistro(data, horas);
        } else {
            throw new ExceptionErrorMessage("Empregado nao eh horista.");
        }
    }

    public void lancaTaxaServico(String membro, String data, String valor) throws Exception {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");

        String id = Manage.getEmpregadoByIDSind(membro);
        Empregado ex = Manage.getEmpregado(id);

        double value = Utils.quitValue(valor);

        Validate.validIDSyndicate(membro);
        Validate.validEmploySyndicate(ex);
        Validate.validValue(value);
        Validate.valiDate(data);

        LocalDate date = LocalDate.parse(data, formato);
        Manage.getEmpregado(id).addTaxaServico(new CardService(data, value));

    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws ExceptionErrorMessage, AtributoException {

        Validate.validEmployInfo(nome, endereco, tipo, salario);
        if (tipo.equals("assalariado")) return Manage.setEmpregado(new EmpregadoAssalariado(nome, endereco, salario));
        else if (tipo.equals("horista")) return Manage.setEmpregado(new EmpregadoHorista(nome, endereco, salario));

        return "0";
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws ExceptionErrorMessage, AtributoException {
        Validate.validEmpoySettings(nome, endereco, tipo, salario, comissao);
        return Manage.setEmpregado(new EmpregadoComissionado(nome, endereco, salario, comissao));
    }


    public void removerEmpregado(String id) throws Exception {
        Empregado e = Manage.getEmpregado(id);

        Validate.validIDEmploy(id);
        Validate.validEmploy(e);

        Manage.employee.remove(id);
    }

    public void alteraEmpregado(String emp, String atributo, String valor) throws Exception {
        Empregado e = Manage.getEmpregado(emp);
        Validate.validIDEmploy(emp);
        Validate.validEmploy(e);

        switch (atributo) {
            case "nome" -> {
                if (valor.isEmpty()) throw new AtributoException("Nome nao pode ser nulo.");
                e.setNome(valor);
            }
            case "endereco" -> {
                if (valor.isEmpty()) throw new AtributoException("Endereco nao pode ser nulo.");
                e.setEndereco(valor);
            }
            case "salario" -> {
                Validate.validSalario(valor);
                e.setSalario(valor);
            }
            case "sindicalizado" -> {
                if (!valor.equals("false") && !valor.equals("true"))throw new AtributoException("Valor deve ser true ou false.");
                if (valor.equals("false")) e.setSindicato(null);
            }
            case "comissao" -> {
                Validate.validComissao(valor, e);
                ((EmpregadoComissionado) e).setTaxa(valor);
            }
            case "tipo" -> {
                String nome = e.getNome();
                String endereco = e.getEndereco();
                String salario = e.getSalario();

                switch (valor) {
                    case "horista" -> Manage.setValue(emp, new EmpregadoHorista(nome, endereco, salario));
                    case "assalariado" -> Manage.setValue(emp, new EmpregadoAssalariado(nome, endereco, salario));
                    case "comissionado" -> Manage.setValue(emp, new EmpregadoComissionado(nome, endereco, salario, "0"));
                    default -> throw new AtributoException("Tipo invalido.");
                }
            }

            case "metodoPagamento" -> {
                if (valor.equals("correios")) e.setMetodoPagamento(new Fedex());
                else if (valor.equals("emMaos")) e.setMetodoPagamento(new Hands());
                else throw new AtributoException("Metodo de pagamento invalido.");
            }
            default -> throw new AtributoException("Atributo nao existe.");
        }
    }

    public void alteraEmpregado(String emp, String atributo, String valor, String sal) throws ExceptionErrorMessage {
        Empregado e = Manage.getEmpregado(emp);

        String nome = e.getNome();
        String endereco = e.getEndereco();
        String salario = e.getSalario();

        if (valor.equals("comissionado")) Manage.setValue(emp, new EmpregadoComissionado(nome, endereco, salario, sal));
        else if (valor.equals("horista")) Manage.setValue(emp, new EmpregadoHorista(nome, endereco, sal));
    }

    public void alteraEmpregado(String emp, String atributo, String valor, String idSindicato, String taxaSindical) throws Exception {
        Validate.syndicalWarm(idSindicato,taxaSindical);

        boolean ans = false;
        if (atributo.equals("sindicalizado") && valor.equals("true")) {
            ans = Validate.notInconsistency(idSindicato);
        }
        if (ans) throw new EmpregadoException("Ha outro empregado com esta identificacao de sindicato");

        Empregado e = Manage.getEmpregado(emp);
        e.setSindicato(new MembroSindicato(idSindicato, Double.parseDouble(taxaSindical.replace(",", "."))));
    }

    public void alteraEmpregado(String emp, String atributo, String tipo, String banco, String agencia, String contaCorrente) throws Exception {
        Empregado e = Manage.getEmpregado(emp);

        if (!tipo.equals("banco")) throw new BankException("Não implementado");
        if(banco.isEmpty()) throw new BankException("Banco nao pode ser nulo.");
        if(agencia.isEmpty()) throw new BankException("Agencia nao pode ser nulo.");
        if(contaCorrente.isEmpty()) throw new BankException("Conta corrente nao pode ser nulo.");

        e.setMetodoPagamento(new Bank(banco, agencia, contaCorrente));
    }

    public String getAtributoEmpregado(String emp, String atributo) throws Exception {
        Empregado e = Manage.getEmpregado(emp);
        Validate.validIDEmploy(emp);
        Validate.validEmploy(e);

        switch (atributo) {
            case "nome" -> {return e.getNome();}
            case "tipo" -> {return e.getTipo();}
            case "salario" -> {
                String salario = e.getSalario();
                if (salario.contains(",")) return salario;
                else return salario + ",00";
            }
            case "endereco" -> {return e.getEndereco();}
            case "comissao" -> {
                if (e instanceof EmpregadoComissionado)
                    return ((EmpregadoComissionado) e).getTaxa();
                throw new EmpregadoException("Empregado nao eh comissionado.");
            }

            case "metodoPagamento" -> {return e.getMetodoPagamento().getMetodoPagamento();}

            case "banco", "agencia", "contaCorrente" -> {
                MetodoPagamento metodoPagamento = e.getMetodoPagamento();

                if (!(metodoPagamento instanceof Bank)) throw new BankException("Empregado nao recebe em banco.");

                if (atributo.equals("banco")) return ((Bank) metodoPagamento).getBanco();
                if (atributo.equals("agencia")) return ((Bank) metodoPagamento).getAgencia();

                return ((Bank) metodoPagamento).getCorrente();
            }

            case "sindicalizado" -> {
                if (e.getSindicato() == null) return "false";
                else return "true";
            }

            case "idSindicato", "taxaSindical" -> {
                MembroSindicato sindicato = e.getSindicato();
                if (sindicato == null) throw new ExceptionErrorMessage("Empregado nao eh sindicalizado.");
                if (atributo.equals("idSindicato")) return sindicato.getIdMembro();
                return Utils.convertDoubleToString(e.getSindicato().getAdicionalSindicato(), 2);
            }
            default -> throw new AtributoException("Atributo nao existe.");
        }

    }
    public String getEmpregadoPorNome(String nome, int indice) throws ExceptionErrorMessage {
        return Manage.getEmpregadoIDByNome(nome, indice);
    }

    public String getHorasNormaisTrabalhadas(String emp, String dataIncial, String dataFinal) throws Exception {
        Empregado e = Manage.getEmpregado(emp);
        if (e instanceof EmpregadoHorista) {
            return ((EmpregadoHorista) e).getCartao() == null ? "0" : ((EmpregadoHorista) e).getHorasNormaisTrabalhadas(dataIncial, dataFinal);
        }
        throw new EmpregadoException("Empregado nao eh horista.");
    }

    public String getHorasExtrasTrabalhadas(String emp, String dataIncial, String dataFinal) throws Exception {
        Empregado e = Manage.getEmpregado(emp);
        if (e instanceof EmpregadoHorista ) {
            return ((EmpregadoHorista) e).getCartao() == null ? "0" : ((EmpregadoHorista) e).getHorasExtrasTrabalhadas(dataIncial, dataFinal);
        }
        throw new EmpregadoException("Empregado nao eh horista.");
    }

    public String getVendasRealizadas(String emp, String dataIncial, String dataFinal) throws Exception {
        Empregado e = Manage.getEmpregado(emp);
        if (e instanceof EmpregadoComissionado) {
            return  ((EmpregadoComissionado) e).getVendasRealizadas(dataIncial, dataFinal);
        }
        throw new EmpregadoException("Empregado nao eh comissionado.");
    }
    public String getTaxasServico(String id, String dataInicial, String dataFinal) throws Exception {
        Empregado e = Manage.getEmpregado(id);

        if(e.getSindicato() == null) throw new EmpregadoException("Empregado nao eh sindicalizado.");
        double ans = e.getSindicato().getTaxasServico(dataInicial, dataFinal);

        return Utils.DoubleString(ans);
    }

    public void Quit(){ //método de quit do sistema

    }

    public String totalFolha(String data) throws Exception{
        return SistemaFolha.totalSalario(data);
    }

    public void rodaFolha(String data, String saida) throws Exception{
//        Payroll.geraFolha(data, saida);
    }
}