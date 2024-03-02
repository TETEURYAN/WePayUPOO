package br.ufal.ic.p2.wepayu.exceptions.Command;

public class CannotComandoException extends Exception {
    public CannotComandoException() throws Exception {
        throw new Exception("Nao pode dar comandos depois de encerrarSistema.");
    }
}
