package br.ufal.ic.p2.wepayu.Models;

import br.ufal.ic.p2.wepayu.Models.KindCard.CardService;
import br.ufal.ic.p2.wepayu.Models.KindPayment.Bank;
import br.ufal.ic.p2.wepayu.Models.KindPayment.Hands;

import java.io.Serializable;

/*
    Classe referente ao Empregado

 */
public abstract class Empregado implements Serializable {
    private String nome;
    private String IDEmploy;
    private String endereco;
    private MembroSindicato sindicato;
    private MetodoPagamento metodoPagamento;

    public Empregado(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.sindicato = null;
        this.metodoPagamento = new Hands();
    }

    public Empregado() {

    }

    public void setSindicato(MembroSindicato sindicalizado) {
        this.sindicato = sindicalizado;
    }

    public MembroSindicato getSindicato() {
        return this.sindicato;
    }

    public void addTaxaServico (CardService taxaServico) {
        this.sindicato.addCardService(taxaServico);
    }

    public String getNome() {
        return this.nome;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public abstract String getSalario();

    public abstract String getTipo();

    public String getIDEmploy(){
        return IDEmploy;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public String getDataPayment(){ //Método para gerar os dados de pagamento para serem escritos no TXT
        MetodoPagamento ans = getMetodoPagamento();
        return switch (getMetodoPagamento().getMetodoPagamento()){
            case "emMaos" -> "Em maos";
            case "banco" -> String.format("%s, Ag. %s CC %s", Bank.getBanco(),
                    Bank.getAgencia(), Bank.getCorrente());
            case "correios" -> String.format("Correios, %s", getEndereco());
            default -> "";
        };
    }

    public abstract void setSalario(String salario);

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIDEmploy(String IDEmploy){
        this.IDEmploy = IDEmploy;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }
}
