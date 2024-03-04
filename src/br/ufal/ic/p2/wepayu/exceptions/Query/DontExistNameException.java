package br.ufal.ic.p2.wepayu.exceptions.Query;

public class DontExistNameException extends Exception{
    public DontExistNameException(){
        super("Nao ha empregado com esse nome.");
    }
}
