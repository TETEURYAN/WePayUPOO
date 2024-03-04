package br.ufal.ic.p2.wepayu.exceptions.Banco;

public class NotGetBancoException extends Exception {
    public NotGetBancoException() {
        super("Empregado nao recebe em banco.");
    }
}
