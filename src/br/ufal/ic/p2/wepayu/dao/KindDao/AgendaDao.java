package br.ufal.ic.p2.wepayu.dao.KindDao;

import br.ufal.ic.p2.wepayu.exceptions.Agenda.ExceptionAgenda;
import br.ufal.ic.p2.wepayu.exceptions.Agenda.ExceptionAgendaExiste;
import br.ufal.ic.p2.wepayu.models.Agenda;
import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.utils.Validate;

public class AgendaDao {

    private final DBmanager session;

    /**
     * Cria uma instância de {@link AgendaDao} com base em uma sessão
     * @param session {@link DBmanager} da sessão do banco de dados
     */
    public AgendaDao(DBmanager session){
        this.session = session;
    }
    
    public void create(String descricao) throws Exception {

        if(find(descricao)) throw new ExceptionAgendaExiste();
        session.addAgenda(new Agenda(descricao));
    }

    public boolean find(String descricao){
        for(Agenda a: session.getAgendas()){
            if(a.toString().equals(descricao)) return true;
        }
        return false;
    }

}
