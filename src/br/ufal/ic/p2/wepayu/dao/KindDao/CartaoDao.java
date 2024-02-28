package br.ufal.ic.p2.wepayu.dao.KindDao;

import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.Decorator.Horas;
import br.ufal.ic.p2.wepayu.models.KindEmployee.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.utils.Utils;

import java.time.LocalDate;

public class CartaoDao {

    private final DBmanager session;
    public CartaoDao(DBmanager session) {
        this.session = session;
    }
    public void addCard(String emp, String data, String horas) throws Exception {
        Empregado e = Utils.validEmpregado(emp);

        if (e == null)
            return;

        if (Utils.validTipoEmpregado(e, "horista")) {
            double horasFormato = Utils.validHoras(horas);
            LocalDate dataFormato = Utils.validData(data, " ");

            if (horasFormato <= 0 || dataFormato == null)
                return;

//            ((EmpregadoHorista) e).addRegistro(data, horasFormato);
            EmpregadoHorista Empregado = (EmpregadoHorista) e;
            Horas novasHoras = new Horas(data, horasFormato, Empregado);
        }
    }

    public String getNormalJob(String emp, String dataInicial, String dataFinal) throws Exception {
        Empregado e = Utils.validEmpregado(emp);

        if (e == null) return "0";

        if (Utils.validTipoEmpregado(e, "horista")) {

            LocalDate dataFinalFormato = Utils.validData(dataFinal, " final ");

            if (dataFinalFormato == null)
                return "0";

            LocalDate dataInicialFormato = Utils.validData(dataInicial, " inicial ");

            if (dataInicialFormato == null)
                return "0";

            double horasTrabalhadas = ((EmpregadoHorista) e).getHorasNormaisTrabalhadas(dataInicialFormato, dataFinalFormato);

            return Utils.convertDoubleToString(horasTrabalhadas);
        }

        return "0";
    }

    public String getExtraJob(String emp, String dataInicial, String dataFinal) throws Exception {
        Empregado e = Utils.validEmpregado(emp);

        if (e == null) return null;

        if (Utils.validTipoEmpregado(e, "horista")) {

            LocalDate dataInicialFormato = Utils.validData(dataInicial, " inicial ");

            if (dataInicialFormato == null)
                return "0,00";

            LocalDate dataFinalFormato = Utils.validData(dataFinal, " final ");

            if (dataFinalFormato == null)
                return "0,00";

            double horasAcumuladas = ((EmpregadoHorista) e).getHorasExtrasTrabalhadas(dataInicialFormato, dataFinalFormato);

            return Utils.convertDoubleToString(horasAcumuladas);
        }

        return "0,00";
    }
}
