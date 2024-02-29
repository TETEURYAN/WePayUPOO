package br.ufal.ic.p2.wepayu.dao;

import br.ufal.ic.p2.wepayu.dao.KindDao.*;
import br.ufal.ic.p2.wepayu.services.DBmanager;

public class Manager {

    private final DBmanager session;
    private EmpregadoDao empregadoDao;
    private CartaoDao cartaoDao;
    private TaxaDao taxaDao;
    private VendaDao vendaDao;

    private FolhaDao folhaDao;

    public Manager(DBmanager session){
        this.session = session;
    }

    public EmpregadoDao getEmpregadoDao() {
        if(empregadoDao == null){
            empregadoDao = new EmpregadoDao(session);
        }
        return empregadoDao;
    }

    public CartaoDao getCartaoDao() {
        if(cartaoDao == null){
            cartaoDao = new CartaoDao(session);
        }
        return cartaoDao;
    }

    public VendaDao getVendaDao() {
        if(vendaDao == null){
            vendaDao = new VendaDao(session);
        }
        return vendaDao;
    }

    public TaxaDao getTaxaDao() {
        if(taxaDao == null){
            taxaDao = new TaxaDao(session);
        }
        return taxaDao;
    }

    public FolhaDao getFolhaDao(){
        if(folhaDao == null){
            folhaDao = new FolhaDao(session);
        }
        return folhaDao;
    }
}