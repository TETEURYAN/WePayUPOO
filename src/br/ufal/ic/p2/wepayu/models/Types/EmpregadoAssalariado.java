package br.ufal.ic.p2.wepayu.models.Types;

import br.ufal.ic.p2.wepayu.models.Empregado;

public class EmpregadoAssalariado extends Empregado {
    public EmpregadoAssalariado(String nome, String endereco, String tipo, String salario, String comissao) throws Exception{
        super(nome, endereco, tipo, salario, false);
    }
}
