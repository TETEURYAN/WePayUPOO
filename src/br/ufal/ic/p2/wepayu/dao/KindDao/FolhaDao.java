package br.ufal.ic.p2.wepayu.dao.KindDao;

import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.services.SistemaFolha;
import br.ufal.ic.p2.wepayu.utils.Utils;

import java.time.LocalDate;

public class FolhaDao {

    private final SistemaFolha folha;

    public FolhaDao(DBmanager session){
        this.folha = SistemaFolha.getFolha();
    }

    public String totalFolha(String data) throws Exception {

        LocalDate dataFormato = Utils.validData(data, "");
        double total = folha.totalFolha(dataFormato);

        return Utils.convertDoubleToString(total, 2);
    }

    public void rodaFolha(String data, String saida) throws Exception {
        LocalDate dataFormato = Utils.validData(data, "");

        folha.setArquivoSaida(saida);
        folha.geraFolha(dataFormato, saida);
    }

}
