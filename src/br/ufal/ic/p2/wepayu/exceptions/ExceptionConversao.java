package br.ufal.ic.p2.wepayu.exceptions;

public class ExceptionConversao extends Exception {

    public void msgValorDeveSerNumerico () throws Exception {
        throw new Exception("Valor deve ser numerico.");
    }

    public void msgValorTrueOuFalse () throws Exception {
        throw new Exception("Valor deve ser true ou false.");
    }
    public void msgValorDeveSerPostivo () throws Exception {
        throw new Exception("Valor deve ser positivo.");
    }

    public void msgHorasNula() throws Exception {
        throw new Exception("Horas nao pode ser nula.");
    }
    public void msgDataInvalida (String tipo) throws Exception {
        throw new Exception("Data" + tipo + "invalida.");
    }

    public void msgSalarioDeveSerNumerico () throws Exception {
        throw new Exception("Salario deve ser numerico.");
    }

    public void msgSalarioNaoNegativo() throws Exception {
        throw new Exception("Salario deve ser nao-negativo.");
    }

    public void msgComissaoDeveSerNumerica () throws Exception {
        throw new Exception("Comissao deve ser numerica.");
    }

    public void msgComissaoNaoNegativa() throws Exception {
        throw new Exception("Comissao deve ser nao-negativa.");
    }

    public void msgHorasDeveSerNumerica () throws Exception {
        throw new Exception("Horas deve ser numerico.");
    }

    public void msgHorasDeveSerPositiva() throws Exception {
        throw new Exception("Horas devem ser positivas.");
    }

    public void msgTaxaSindicalNaoNegativa() throws Exception {
        throw new Exception("Taxa sindical deve ser nao-negativa.");
    }

    public void msgTaxaSindicalDeveSerNumerica () throws Exception {
        throw new Exception("Taxa sindical deve ser numerica.");
    }

}
