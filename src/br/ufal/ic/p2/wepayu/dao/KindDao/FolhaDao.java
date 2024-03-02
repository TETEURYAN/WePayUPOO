package br.ufal.ic.p2.wepayu.dao.KindDao;

import br.ufal.ic.p2.wepayu.services.Memento;
import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.models.SistemaFolha;
import br.ufal.ic.p2.wepayu.utils.Utils;

import java.time.LocalDate;

public class FolhaDao {

    private final SistemaFolha folha;
    private Memento backup;

    public FolhaDao(DBmanager session, Memento backup){

        this.folha = SistemaFolha.getFolha();
        this.backup = backup;
    }

    public String totalFolha(String data) throws Exception {

        LocalDate dataFormato = Utils.validData(data, "");
        double total = folha.totalFolha(dataFormato);

        return Utils.convertDoubleToString(total, 2);
    }

    public void rodaFolha(String data, String saida) throws Exception {
        LocalDate dataFormato = Utils.validData(data, "");

        backup.pushUndo();

        folha.setArquivoSaida(saida);
        folha.geraFolha(dataFormato, saida);
    }

}
