package br.ufal.ic.p2.wepayu.dao;

import br.ufal.ic.p2.wepayu.dao.KindDao.*;
import br.ufal.ic.p2.wepayu.services.Memento;
import br.ufal.ic.p2.wepayu.services.DBmanager;

public class Manager {

    private final DBmanager session;
    private EmpregadoDao empregadoDao;
    private CartaoDao cartaoDao;
    private TaxaDao taxaDao;
    private VendaDao vendaDao;
    private FolhaDao folhaDao;
    private Memento backup;

    public Manager(DBmanager session, Memento backup){

        this.session = session;
        this.backup = backup;
    }

    public EmpregadoDao getEmpregadoDao() {
        if(empregadoDao == null){
            empregadoDao = new EmpregadoDao(session, backup);
        }
        return empregadoDao;
    }

    public CartaoDao getCartaoDao() {
        if(cartaoDao == null){
            cartaoDao = new CartaoDao(session, backup);
        }
        return cartaoDao;
    }

    public VendaDao getVendaDao() {
        if(vendaDao == null){
            vendaDao = new VendaDao(session, backup);
        }
        return vendaDao;
    }

    public TaxaDao getTaxaDao() {
        if(taxaDao == null){
            taxaDao = new TaxaDao(session, backup);
        }
        return taxaDao;
    }

    public FolhaDao getFolhaDao(){
        if(folhaDao == null){
            folhaDao = new FolhaDao(session, backup);
        }
        return folhaDao;
    }
}