package br.ufal.ic.p2.wepayu.dao.KindDao;

import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.TiposCartao.CartaoDeVenda;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.Decorator.Venda;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.services.Memento;
import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.utils.Utils;
import br.ufal.ic.p2.wepayu.utils.Validate;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Data Access Object (DAO) para o Cartão de Vendas do Empregado Comissionado
 * @author teteuryan faite100
 */

public class VendaDao {

    private final DBmanager session;//banco de dados
    private Memento backup;//Memento

    /**
     * Cria uma instância de {@link VendaDao} com base em uma sessão
     * @param session {@link DBmanager} da sessão do banco de dados e
     * com base em um {@link Memento} para armazenar os estados anteriores
     */
    public VendaDao(DBmanager session, Memento backup) {

        this.session = session;
        this.backup = backup;
    }

    /**
     * Adiciona vendas ao Caroa de Vendas do Empregado Comissionado
     */
    public void addVenda(String emp, String data, String valor) throws Exception {
        Empregado e = session.getEmpregado(emp);

        if (Utils.validTipoEmpregado(e, "comissionado") && e != null) {
            double valorFormato = Utils.toDouble(valor);
            Validate.validValue(valorFormato);
            LocalDate dataFormato = Utils.validData(data, " ");

            backup.push();

            EmpregadoComissionado empregado = (EmpregadoComissionado) e;
            Venda venda = new Venda(data, valorFormato, empregado);//Decorator que adiciona uma nova venda ao vetor do Empregado Cmissionado
        }
    }

    /**
     * Contabiliza as vendas do vetor de {@link CardSale} presentes em cada Empregado Comissionado
     */
    public String getVendas(String emp, String dataInicial, String dataFinal) throws Exception {
        Empregado e = session.getEmpregado(emp);

        if (e == null) return null;

        if (Utils.validTipoEmpregado(e, "comissionado")) {

            LocalDate dataInicialFormato = Utils.validData(dataInicial, " inicial ");
            LocalDate dataFinalFormato = Utils.validData(dataFinal, " final ");

            if (dataFinalFormato == null || dataInicialFormato == null) return "0,00";

            double vendas = ((EmpregadoComissionado) e).getVendasRealizadas(dataInicialFormato, dataFinalFormato);
            return Utils.convertDoubleToString(vendas, 2);
        }
        return "0,00";
    }


    public static ArrayList<CartaoDeVenda> copyArrayCardSale(ArrayList<CartaoDeVenda> origin) {//Metodo para copiar o cartão e venda
        ArrayList<CartaoDeVenda> copy = new ArrayList<>();

        for (CartaoDeVenda c : origin) {
            CartaoDeVenda copyCartaoDeVenda = new CartaoDeVenda();

            copyCartaoDeVenda.setData(c.getData());
            copyCartaoDeVenda.setHoras(c.getHoras());

            copy.add(copyCartaoDeVenda);
        }

        return  copy;
    }
}
