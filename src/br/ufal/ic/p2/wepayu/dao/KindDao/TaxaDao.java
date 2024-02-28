package br.ufal.ic.p2.wepayu.dao.KindDao;

import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.KindEmployee.Decorator.Service;
import br.ufal.ic.p2.wepayu.models.Sindicato;
import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.utils.Utils;

import java.time.LocalDate;

public class TaxaDao {

    private final DBmanager session;
    public TaxaDao(DBmanager session) {
        this.session = session;
    }

    public void addService(String membro, String data, String valor) throws Exception {

        String id = Utils.validMembroSindicalizadoPeloID(membro);

        if (id == null)
            return;

        double valorFormato = Utils.validValor(valor);
        LocalDate dataFormato = Utils.validData(data, " ");

        if (valorFormato <= 0 || dataFormato == null)
            return;

//        EmpregadoController.getEmpregado(id).addTaxaServico(new TaxaServico(data, valorFormato));
        Empregado e = DBmanager.getEmpregado(id);
        Service NovoServico = new Service(data, valorFormato, e) {
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


    public String getService(String emp, String dataInicial, String dataFinal) throws Exception {

        Empregado e = Utils.validEmpregado(emp);

        if (e == null)
            return "0,00";

        LocalDate dataInicialFormato = Utils.validData(dataInicial, " inicial ");

        if (dataInicialFormato == null)
            return "0,00";

        LocalDate dataFinalFormato = Utils.validData(dataFinal, " final ");

        if (dataFinalFormato == null)
            return "0,00";

        Sindicato m = Utils.validMembroSindicalizado(e);

        if (m == null) {
            return "0,00";
        }

        double taxa = m.getTaxaServicos(dataInicialFormato, dataFinalFormato);

        return Utils.convertDoubleToString(taxa, 2);
    }

}
