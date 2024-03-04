package br.ufal.ic.p2.wepayu.exceptions.Banco;

public class PaymentWayInvalidException extends Exception{
    public PaymentWayInvalidException(){
        super("Metodo de pagamento invalido.");
    }
}
