package br.ufal.ic.p2.wepayu.exceptions.Memento;

public class MementoDesfazerException extends Exception{
    public MementoDesfazerException() throws Exception{
        throw new Exception("Nao ha comando a desfazer.");
    }

}
