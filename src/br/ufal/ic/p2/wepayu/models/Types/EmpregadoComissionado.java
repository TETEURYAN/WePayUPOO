package br.ufal.ic.p2.wepayu.models.Types;

import br.ufal.ic.p2.wepayu.Exception.SalarioException;
import br.ufal.ic.p2.wepayu.Exception.TipoNaoAplicavalExcpetion;
import br.ufal.ic.p2.wepayu.Utils.Validate;
import br.ufal.ic.p2.wepayu.models.Empregado;

public class EmpregadoComissionado extends Empregado {
    private static String comissao;
    public EmpregadoComissionado(String nome, String endereco, String tipo, String salario, boolean sind, String comissao) throws Exception {
        super(nome, endereco, tipo, salario, sind);

        if(!tipo.equalsIgnoreCase("comissionado")){
            throw new TipoNaoAplicavalExcpetion("Tipo nao aplicavel.");
        }

        if (Validate.isNull(comissao)){
            throw new SalarioException("Comissao nao pode ser nula.");
        }else if (Validate.isNegative(comissao)){
            throw new SalarioException("Comissao deve ser nao-negativa.");
        }else if (Validate.isNotSalary(comissao)){
            throw new SalarioException("Comissao deve ser numerica.");
        }


        this.comissao = comissao;
    }
    public static String getComissao(){
        return comissao;
    }
}
