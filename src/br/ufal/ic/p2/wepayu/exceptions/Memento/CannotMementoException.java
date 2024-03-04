package br.ufal.ic.p2.wepayu.exceptions.Memento;

public class CannotMementoException extends Exception {
    public CannotMementoException() throws Exception {
        throw new Exception("Nao pode dar comandos depois de encerrarSistema.");
    }
}
