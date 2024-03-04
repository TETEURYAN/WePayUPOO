package br.ufal.ic.p2.wepayu.exceptions.Banco;

public class AgenciaNuloException extends Exception {
    public AgenciaNuloException() {
        super("Agencia nao pode ser nulo.");
    }
}
