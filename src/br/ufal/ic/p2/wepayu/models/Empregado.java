package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;

public class Empregado {
    private String nome;
    private String endereco;
    private String tipo;
    private String salario;

    private String comissao;

    private boolean sind;

    public Empregado(String nome, String endereco, String tipo, String salario, boolean sind) throws EmpregadoNaoExisteException {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.sind = sind;
//        this.comissao = comissao;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTipo() {
        return tipo;
    }

    public  String getSalario() {
        return salario;
    }
    public boolean getSind(){return sind;}

}
