package br.ufal.ic.p2.wepayu.exceptions.Sindicato;

public class SyndicateAtributeException extends Exception{
    public SyndicateAtributeException(){
        super("Valor deve ser true ou false.");
    }
}
