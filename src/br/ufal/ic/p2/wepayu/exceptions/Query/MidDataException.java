package br.ufal.ic.p2.wepayu.exceptions.Query;

public class MidDataException extends Exception{
    public MidDataException(String msg){
        super("Data" + msg + "invalida.");
    }
}
