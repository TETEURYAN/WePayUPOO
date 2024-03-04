package br.ufal.ic.p2.wepayu.exceptions.Employ;

public class TypeEmpregadoException extends Exception{
    public TypeEmpregadoException(String tipo){
        super("Empregado nao eh " + tipo +".");
    }
}
