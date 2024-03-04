package br.ufal.ic.p2.wepayu.models.TiposPagamento;

import br.ufal.ic.p2.wepayu.models.MetodoPagamento;

/**
 * Classe do {@link Banco} que herda de {@link MetodoPagamento}
 * @author teteuryan faite100
 */
public class Banco extends MetodoPagamento {
    private String banco;
    private String agencia;
    private String contaCorrente;

    public Banco () {//Construtor vazio para ser serializado pela Persistencia em XML

    }

    /**
     * Cria uma instancia de {@link Banco}, considerando o nome do Banco, a
     * Agencia e o valor da Conta Corrente
     */
    public Banco(String banco, String agencia, String contaCorrente) {
        this.banco = banco;
        this.agencia = agencia;
        this.contaCorrente = contaCorrente;
    }

    public String getBanco() {
        return banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getContaCorrente() {
        return contaCorrente;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public void setContaCorrente(String contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    @Override
    public String getMetodoPagamento() {
        return "banco";
    }

    /**
     * Método usado para retornar as informações usadas na FOlha de Pagamento
     */
    @Override
    public String getOutputFile() {
        return getBanco() + "," + " Ag. " + getAgencia() + " CC " + getContaCorrente();
    }
}
