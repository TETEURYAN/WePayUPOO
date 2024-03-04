package br.ufal.ic.p2.wepayu.exceptions.Sindicato;

public class NotSyndicateException extends Exception{
    public NotSyndicateException(){
        super("Empregado nao eh sindicalizado.");
    }

}
