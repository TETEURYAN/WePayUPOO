package br.ufal.ic.p2.wepayu.exceptions.Command;

public class ComandoFazerException extends Exception{
    public void ExceptionDoComandExist() throws Exception{
        throw new Exception("Nao ha comando a fazer.");
    }
}
