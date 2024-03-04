package br.ufal.ic.p2.wepayu.dao;

import br.ufal.ic.p2.wepayu.dao.KindDao.*;
import br.ufal.ic.p2.wepayu.services.Memento;
import br.ufal.ic.p2.wepayu.services.DBmanager;

/**
 * Data Access Object (DAO) para gerenciamento com as entidades
 * @author teteuryan faite100
 */

public class Manager {

    /**
     * Atributos do DAO para facilitar na consulta do banco SESSION
     */
    private final DBmanager session;
    private EmpregadoDao empregadoDao;
    private CartaoDao cartaoDao;
    private TaxaDao taxaDao;
    private VendaDao vendaDao;
    private FolhaDao folhaDao;
    private AgendaDao agendaDao;
    private Memento backup;

    /**
     * Retorna uma instância do {@link Manager} com base em uma sessão do banco de dados já inserida
     */

    public Manager(DBmanager session, Memento backup){

        this.session = session;
        this.backup = backup;
    }

    /**
     * Seção de DAO de cada entidade que herda do empregado: Taxa, Venda, Cartao de ponto
     * DAO também para Folha de Pagamento, Agenda de Pagamento
     */

    /**
     * Retorna uma instância do DAO de Empregados
     * @return instância de {@link EmpregadoDao}
     */
    public EmpregadoDao getEmpregadoDao() {
        if(empregadoDao == null){
            empregadoDao = new EmpregadoDao(session, backup);
        }
        return empregadoDao;
    }

    /**
     * Retorna uma instância do DAO do Cartão de Ponto, presente no Empregado Horista
     * @return instância de {@link CartaoDao}
     **/
    public CartaoDao getCartaoDao() {
        if(cartaoDao == null){
            cartaoDao = new CartaoDao(session, backup);
        }
        return cartaoDao;
    }

    /**
     * Retorna uma instância do DAO do Cartão de Venda, presente no Empregado Comissionado
     * @return instância de {@link VendaDao}
     **/
    public VendaDao getVendaDao() {
        if(vendaDao == null){
            vendaDao = new VendaDao(session, backup);
        }
        return vendaDao;
    }

    /**
     * Retorna uma instância do DAO do Cartão de Taxas de Servico, presente no MembroSindicato
     * @return instância de {@link TaxaDao}
     **/
    public TaxaDao getTaxaDao() {
        if(taxaDao == null){
            taxaDao = new TaxaDao(session, backup);
        }
        return taxaDao;
    }

    /**
     * Retorna uma instância do DAO da Folha de Pagamento
     * @return instância de {@link FolhaDao}
     **/
    public FolhaDao getFolhaDao(){
        if(folhaDao == null){
            folhaDao = new FolhaDao(session, backup);
        }
        return folhaDao;
    }

    /**
     * Retorna uma instância do DAO da Agenda de Pagamento
     * @return instância de {@link FolhaDao}
     **/
    public AgendaDao getAgendaDao() {
        if(agendaDao == null){
            agendaDao = new AgendaDao(session);
        }
        return agendaDao;
    }
}