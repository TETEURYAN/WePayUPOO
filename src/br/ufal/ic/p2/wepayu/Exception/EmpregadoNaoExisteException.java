package br.ufal.ic.p2.wepayu.Exception;

public class EmpregadoNaoExisteException extends Exception{
    @Override
    public String toString(){
        return "Empregado nao existe.";
    }

    public EmpregadoNaoExisteException(){
        super("Empregado nao existe.");
    }
}
