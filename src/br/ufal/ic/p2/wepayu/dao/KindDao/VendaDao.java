package br.ufal.ic.p2.wepayu.dao.KindDao;

import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.Decorator.Venda;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.services.Memento;
import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.utils.Utils;

import java.time.LocalDate;

public class VendaDao {

    private final DBmanager session;
    private Memento backup;
    public VendaDao(DBmanager session, Memento backup) {

        this.session = session;
        this.backup = backup;
    }

    public void addVenda(String emp, String data, String valor) throws Exception {
        Empregado e = Utils.validEmpregado(emp);

        if (Utils.validTipoEmpregado(e, "comissionado") && e != null) {
            double valorFormato = Utils.validValor(valor);
            LocalDate dataFormato = Utils.validData(data, " ");

            if (valorFormato <= 0 || dataFormato == null) return;

            backup.pushUndo();
            EmpregadoComissionado empregado = (EmpregadoComissionado) e;
            Venda venda = new Venda(data, valorFormato, empregado);
        }
    }

    public String getVendas(String emp, String dataInicial, String dataFinal) throws Exception {
        Empregado e = Utils.validEmpregado(emp);

        if (e == null) return null;

        if (Utils.validTipoEmpregado(e, "comissionado")) {

            LocalDate dataInicialFormato = Utils.validData(dataInicial, " inicial ");

            if (dataInicialFormato == null)
                return "0,00";

            LocalDate dataFinalFormato = Utils.validData(dataFinal, " final ");

            if (dataFinalFormato == null)
                return "0,00";

            double vendas = ((EmpregadoComissionado) e).getVendasRealizadas(dataInicialFormato, dataFinalFormato);

            return Utils.convertDoubleToString(vendas, 2);
        }

        return "0,00";
    }
}
