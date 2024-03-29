package br.ufal.ic.p2.wepayu.dao.KindDao;

import br.ufal.ic.p2.wepayu.exceptions.Agenda.ExceptionAgenda;
import br.ufal.ic.p2.wepayu.exceptions.Agenda.ExceptionAgendaExiste;
import br.ufal.ic.p2.wepayu.models.Agenda;
import br.ufal.ic.p2.wepayu.models.MetodoPagamento;
import br.ufal.ic.p2.wepayu.models.TiposPagamento.Banco;
import br.ufal.ic.p2.wepayu.models.TiposPagamento.Correios;
import br.ufal.ic.p2.wepayu.models.TiposPagamento.EmMaos;
import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.utils.Validate;

/**
 * Data Access Object (DAO) para gerenciamento de Agendas de Pagamento
 * @author teteuryan faite100
 */

public class AgendaDao {

    private final DBmanager session;

    /**
     * Cria uma instância de {@link AgendaDao} com base em uma sessão
     * @param session {@link DBmanager} da sessão do banco de dados
     */
    public AgendaDao(DBmanager session){
        this.session = session;
    }

    /**
     * Cria uma descrição no vetor de Agendas presentes no Banco de Dados
     * Caso já exista uma agenda com aquele valor, é feita o Exception
     */
    public void create(String descricao) throws Exception {

        if(find(descricao)) throw new ExceptionAgendaExiste();
        session.addAgenda(new Agenda(descricao));
    }

    /**
     * Procura a descrição no vetor Agenda, caso não haja
     * o retorno volta para o método CREATE para enfim ser criada uma nova agenda
     */
    public boolean find(String descricao){
        for(Agenda a: session.getAgendas()){
            if(a.toString().equals(descricao)) return true;
        }
        return false;
    }

    public static MetodoPagamento copyMetodoPagamento(MetodoPagamento origin) {//Metodo para copiar o metodo de pagamento

        if (origin.getMetodoPagamento().equals("emMaos"))
            return new EmMaos();

        if (origin.getMetodoPagamento().equals("correios"))
            return new Correios();

        if (origin.getMetodoPagamento().equals("banco")) {
            Banco copy = new Banco();

            copy.setBanco(((Banco) origin).getBanco());
            copy.setAgencia(((Banco) origin).getAgencia());
            copy.setContaCorrente(((Banco) origin).getContaCorrente());

            return copy;
        }

        return null;
    }

}
