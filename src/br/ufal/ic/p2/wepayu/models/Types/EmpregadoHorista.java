package br.ufal.ic.p2.wepayu.models.Types;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.EnderecoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.NomeNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.TipoInvalidoException;
import br.ufal.ic.p2.wepayu.models.Empregado;

public class EmpregadoHorista extends Empregado {
    private String comissao;
    private String SalarioHora;

    public EmpregadoHorista(String nome, String endereco, String tipo, String salario, String comissao) throws Exception {
        super(nome, endereco, tipo, salario, false);
        this.comissao = comissao;
        this.SalarioHora = salario;

    }

    public String getComissao() {
        return comissao;
    }

    public String getSalarioHora(){
        return SalarioHora;
    }
}
