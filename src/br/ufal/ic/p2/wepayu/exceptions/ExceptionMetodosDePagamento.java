package br.ufal.ic.p2.wepayu.exceptions;

public class ExceptionMetodosDePagamento extends Exception{
    public void msgNaoRecebeEmBanco() throws Exception {
        throw new Exception("Empregado nao recebe em banco.");
    }
}
