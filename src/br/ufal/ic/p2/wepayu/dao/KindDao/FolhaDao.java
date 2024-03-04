package br.ufal.ic.p2.wepayu.dao.KindDao;

import br.ufal.ic.p2.wepayu.services.Memento;
import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.models.SistemaFolha;
import br.ufal.ic.p2.wepayu.utils.Utils;

import java.time.LocalDate;

/**
 * Data Access Object (DAO) para a Folha de Pagamentos
 * @author teteuryan faite100
 */

public class FolhaDao {

    private final SistemaFolha folha; //Folha de pagamento
    private Memento backup;//Memento
    private final DBmanager session;//Banco de daados

    /**
     * Cria uma instância de {@link FolhaDao} com base em uma sessão
     * @param session {@link DBmanager} da sessão do banco de dados e
     * com base em um {@link Memento} para armazenar os estados anteriores
     */
    public FolhaDao(DBmanager session, Memento backup){

        this.folha = SistemaFolha.getFolha();
        this.backup = backup;
        this.session = session;
    }

    /**
     * Retorna o total acumulao da folha de pagamento
     * para uma data inserida
     */
    public String totalFolha(String data) throws Exception {

        LocalDate dataFormato = Utils.validData(data, "");
        double total = folha.totalFolha(dataFormato);

        return Utils.convertDoubleToString(total, 2);
    }

    /**
     * Roda a  da folha de pagamento para uma data inserida e
     * um arquivo de saida inserido
     */
    public void rodaFolha(String data, String saida) throws Exception {
        LocalDate dataFormato = Utils.validData(data, "");

        backup.push();

        folha.setArquivoSaida(saida);
        folha.geraFolha(dataFormato, saida);
    }

}
