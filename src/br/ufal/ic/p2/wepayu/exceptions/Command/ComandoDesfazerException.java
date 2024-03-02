package br.ufal.ic.p2.wepayu.exceptions.Command;

public class ComandoDesfazerException extends Exception{
    public ComandoDesfazerException() throws Exception{
        throw new Exception("Nao ha comando a desfazer.");
    }

}
