package br.ufal.ic.p2.wepayu.Exception;

public class NomeNaoExisteException extends Exception{
    @Override
    public String toString(){
        return "Empregado nao existe.";
    }
    public NomeNaoExisteException(String errorMsg) {
        super(errorMsg);
    }

}
