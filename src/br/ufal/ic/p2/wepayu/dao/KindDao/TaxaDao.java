package br.ufal.ic.p2.wepayu.dao.KindDao;

import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.Decorator.Service;
import br.ufal.ic.p2.wepayu.models.Sindicato;
import br.ufal.ic.p2.wepayu.services.Memento;
import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.utils.Utils;
import br.ufal.ic.p2.wepayu.utils.Validate;

import java.time.LocalDate;

/**
 * Data Access Object (DAO) para a Taxa de Servico do Empregado Sindicalziado
 * @author teteuryan faite100
 */

public class TaxaDao {

    private final DBmanager session; //Banco de dados
    private Memento backup;//Memento

    /**
     * Cria uma instância de {@link TaxaDao} com base em uma sessão
     * @param session {@link DBmanager} da sessão do banco de dados e
     * com base em um {@link Memento} para armazenar os estados anteriores
     */
    public TaxaDao(DBmanager session, Memento backup) {

        this.session = session;
        this.backup = backup;
    }

    /**
     * Adiciona uma raxa de servico ao vetor de Taxa de Servico presente na
     * classe {@link Sindicato}
     */
    public void addService(String membro, String data, String valor) throws Exception {

        Validate.validSyndicateMembro(membro);
        Validate.validEmploySyndicate(membro);

        double valorFormato = Utils.toDouble(valor);
        Validate.validValue(valorFormato);
        LocalDate dataFormato = Utils.validData(data, " ");

        String ans = DBmanager.getEmpregadoPorIdSindical(membro);
        Empregado e = session.getEmpregado(ans);

        backup.push();

        Service NovoServico = new Service(data, valorFormato, e) { //Decortaro de Nova Taxa de Servico
            @Override
            public double getSalario() {
                return 0;
            }

            @Override
            public void setSalario(double salario) {

            }

            @Override
            public String getTipo() {
                return null;
            }
        };

    }


    /**
     * Retorna o total de serviços acumulados no vetor de Taxa de Serviços presente
     * na classe {@link Sindicato}
     */
    public String getService(String emp, String dataInicial, String dataFinal) throws Exception {

        Empregado e = session.getEmpregado(emp);

        if (e == null) return "0,00";

        LocalDate dataInicialFormato = Utils.validData(dataInicial, " inicial ");
        LocalDate dataFinalFormato = Utils.validData(dataFinal, " final ");

        Validate.validMembroSindicalizado(e);
        Sindicato m = e.getSindicalizado();

        if (dataInicialFormato == null || dataFinalFormato == null || m == null) return "0,00";
        double taxa = m.getTaxaServicos(dataInicialFormato, dataFinalFormato);

        return Utils.convertDoubleToString(taxa, 2);
    }

}
