package br.ufal.ic.p2.wepayu.managment;

import br.ufal.ic.p2.wepayu.services.SistemaFolha;
import br.ufal.ic.p2.wepayu.services.XMLStrategy.XMLEmpregado;
import br.ufal.ic.p2.wepayu.exceptions.ExceptionEmpregado;
import br.ufal.ic.p2.wepayu.models.*;
import br.ufal.ic.p2.wepayu.models.FactoryEmployee.FactoryEmployee;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.KindPayment.Banco;
import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.utils.Utils;

import java.time.LocalDate;
import java.util.Map;

public class ParcialManagment {

    private static FactoryEmployee fabrica;
    DBmanager controle;
    public ParcialManagment() {
        XMLEmpregado xml = new XMLEmpregado();
        controle = DBmanager.getDatabase();
        fabrica = new FactoryEmployee();
    }
    public void zerarSistema() {
        Utils.initSystem();
        Utils.deleteFilesXML();
        Utils.deleteFolhas();
    }

    public void encerrarSistema () {

        XMLEmpregado xml = new XMLEmpregado();
        xml.save(DBmanager.empregados);
    }

    public static FactoryEmployee getFabrica() {
        return fabrica;
    }


    public String getEmpregadoPorNome(String nome, int indice) throws Exception {

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

    public String getAtributoEmpregado(String emp, String atributo) throws Exception {

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

    public String totalFolha(String data) throws Exception {

        LocalDate dataFormato = Utils.validData(data, "");

        SistemaFolha folha = new SistemaFolha(dataFormato);

        double total = folha.totalFolha();

        return Utils.convertDoubleToString(total, 2);
    }

    public void rodaFolha(String data, String saida) throws Exception {

        LocalDate dataFormato = Utils.validData(data, "");

        SistemaFolha folha = new SistemaFolha(dataFormato, saida);

        folha.geraFolha();
    }

    public static class FolhaDePagamentoController {
        private static int countDay = 1;

        public static void incrementCountDay() {
                countDay++;
        }


        public static boolean ehDiaDoPagamentoComissionado() {

            System.out.println(countDay);

            if (countDay % 15 == 0) return true;

            return false;
        }
    }
}
