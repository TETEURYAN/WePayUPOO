package br.ufal.ic.p2.wepayu.exceptions.Sindicato;

public class DuplicateException extends Exception{
    public DuplicateException(){ super("Ha outro empregado com esta identificacao de sindicato"); }
}
