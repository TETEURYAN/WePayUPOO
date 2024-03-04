package br.ufal.ic.p2.wepayu.exceptions.Memento;

public class MementoFazerException extends Exception{
    public void MementoDoComandExist() throws Exception{
        throw new Exception("Nao ha comando a fazer.");
    }
}
