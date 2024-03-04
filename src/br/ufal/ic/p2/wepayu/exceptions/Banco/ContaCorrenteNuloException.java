package br.ufal.ic.p2.wepayu.exceptions.Banco;

public class ContaCorrenteNuloException extends Exception{
    public ContaCorrenteNuloException() {
        super("Conta corrente nao pode ser nulo.");
    }
}
