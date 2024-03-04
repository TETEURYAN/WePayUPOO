package br.ufal.ic.p2.wepayu.exceptions.Employ;

public class NotExistEmpregadoException extends Exception{
    public NotExistEmpregadoException() {
        super("Empregado nao existe.");
    }
}
