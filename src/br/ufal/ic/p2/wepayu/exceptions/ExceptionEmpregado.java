package br.ufal.ic.p2.wepayu.exceptions;

public class ExceptionEmpregado extends Exception {

    public void msgContaCorrenteNulo () throws Exception{
        throw new Exception("Conta corrente nao pode ser nulo.");
    }

    public void msgAgenciaNulo () throws Exception{
        throw new Exception("Agencia nao pode ser nulo.");
    }

    public void msgBancoNulo () throws Exception{
        throw new Exception("Banco nao pode ser nulo.");
    }

    public void msgIndentificaoSindicatoDuplicada () throws Exception {
        throw new Exception("Ha outro empregado com esta identificacao de sindicato");
    }

    public void msgIdSindicalNula () throws Exception{
        throw new Exception("Identificacao do sindicato nao pode ser nula.");
    }

    public void msgEmpregadoNaoSindicalizado() throws Exception {
        throw new Exception("Empregado nao eh sindicalizado.");
    }

    public void msgDataInicialPosteriorDataFinal() throws Exception {
        throw new Exception("Data inicial nao pode ser posterior aa data final.");
    }

    public void msgEmpregadoNaoExistePorNome() throws Exception {
        throw new Exception("Nao ha empregado com esse nome.");
    }

    public void msgEmpregadoNaoTipo(String tipo) throws Exception {
        throw new Exception("Empregado nao eh " + tipo + ".");
    }

    public void msgIdNaoPodeSerNulo() throws Exception {
        throw new Exception("Identificacao do membro nao pode ser nula.");
    }

    public void msgMembroNaoExiste() throws Exception {
        throw new Exception("Membro nao existe.");
    }

    public void msgNullIndex() throws Exception {
        throw new Exception("Identificacao do empregado nao pode ser nula.");
    }

    public void msgEmpregadoNotExist() throws Exception {
        throw new Exception("Empregado nao existe.");
    }

    public void msgAtributoNotExit() throws Exception {
        throw new Exception("Atributo nao existe.");
    }

    public void msgEmpregadoNotComissionado() throws Exception {
        throw new Exception("Empregado nao eh comissionado.");
    }

    public void msgEmpregadoNotSindicalizado() throws Exception {
        throw new Exception("Empregado nao eh sindicalizado.");
    }

    public void msgNomeNulo() throws Exception {
        throw new Exception("Nome nao pode ser nulo.");
    }

    public void msgEnderecoNulo() throws Exception {
        throw new Exception("Endereco nao pode ser nulo.");
    }

    public void msgSalarioNulo() throws Exception {
        throw new Exception("Salario nao pode ser nulo.");
    }

    public void msgComissaoNula() throws Exception {
        throw new Exception("Comissao nao pode ser nula.");
    }

    public void msgTipoNaoAplicavel() throws Exception {
        throw new Exception("Tipo nao aplicavel.");
    }

    public void msgTipoInvalido() throws Exception {
        throw new Exception("Tipo invalido.");
    }

    public void msgMetodoPagInvalido() throws Exception {
        throw new Exception("Metodo de pagamento invalido.");
    }

    public void msgTaxaSindicalNula () throws Exception{
        throw new Exception("Taxa sindical nao pode ser nula.");
    }

}
