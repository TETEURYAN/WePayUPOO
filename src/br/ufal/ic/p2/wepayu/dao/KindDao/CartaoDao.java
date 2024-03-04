package br.ufal.ic.p2.wepayu.dao.KindDao;

import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.Decorator.Horas;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.services.Memento;
import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.utils.Utils;
import br.ufal.ic.p2.wepayu.utils.Validate;

import java.time.LocalDate;

/**
 * Data Access Object (DAO) para gerenciamento de Cartão de Ponto dos Empregados Horistas
 * @author teteuryan faite100
 */

public class CartaoDao {

    private final DBmanager session; /* Variavel do banco de dados */
    private Memento backup;/* Variavel do memento */

    /**
     * Cria uma instância de {@link CartaoDao} com base em uma sessão
     * @param session {@link DBmanager} da sessão do banco de dados
     */
    public CartaoDao(DBmanager session, Memento backup) {

        this.session = session;
        this.backup = backup;
    }

    /**
     * Adiciona um Cartão de Ponto ao vetor de Cartão de Ponto
     * O @param session {@link DBmanager}  é o banco de dados com o qual é realiazda a consulta
     */
    public void addCard(String emp, String data, String horas) throws Exception {
        Empregado e = session.getEmpregado(emp);

        if (Utils.validTipoEmpregado(e, "horista")) {
            double horasFormato = Utils.validHoras(horas);
            LocalDate dataFormato = Utils.validData(data, " ");


            backup.push();

            EmpregadoHorista Empregado = (EmpregadoHorista) e;
            Horas novasHoras = new Horas(data, horasFormato, Empregado); /*Decorator de horas para adicionar novas horas ao Cartão de Ponto */
        }
    }

    /**
     * Método para calcular as horas normais de trablho de um Empregado Horista
     */
    public String getNormalJob(String emp, String dataInicial, String dataFinal) throws Exception {
        Empregado e = session.getEmpregado(emp);

        if (e == null) return "0";

        if (Utils.validTipoEmpregado(e, "horista")) {

            LocalDate dataFinalFormato = Utils.validData(dataFinal, " final ");
            LocalDate dataInicialFormato = Utils.validData(dataInicial, " inicial ");

            if (dataInicialFormato == null || dataFinalFormato == null ) return "0,00";
            Validate.validCorrectData(dataInicialFormato, dataFinalFormato);
            double horasTrabalhadas = ((EmpregadoHorista) e).getHorasNormaisTrabalhadas(dataInicialFormato, dataFinalFormato);

            return Utils.convertDoubleToString(horasTrabalhadas);
        }

        return "0";
    }

    /**
     * Método para calcular as horas extras de trablho de um Empregado Horista
     */
    public String getExtraJob(String emp, String dataInicial, String dataFinal) throws Exception {
        Empregado e = session.getEmpregado(emp);

        if (Utils.validTipoEmpregado(e, "horista")) {

            LocalDate dataInicialFormato = Utils.validData(dataInicial, " inicial ");
            LocalDate dataFinalFormato = Utils.validData(dataFinal, " final ");

            if (dataInicialFormato == null || dataFinalFormato == null ) return "0,00";
            Validate.validCorrectData(dataInicialFormato, dataFinalFormato);
            double horasAcumuladas = ((EmpregadoHorista) e).getHorasExtrasTrabalhadas(dataInicialFormato, dataFinalFormato);

            return Utils.convertDoubleToString(horasAcumuladas);
        }
        return "0,00";
    }
}
