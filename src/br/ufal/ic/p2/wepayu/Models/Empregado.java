package br.ufal.ic.p2.wepayu.Models;

import br.ufal.ic.p2.wepayu.Models.KindCard.CardService;
import br.ufal.ic.p2.wepayu.Models.KindPayment.Hands;

public abstract class Empregado {
    private String nome;
    private String endereco;
    private Syndicate sindicato;
    private PaymentWay metodoPagamento;

    public Empregado(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.sindicato = null;
        this.metodoPagamento = new Hands();
    }

    public void setSindicato(Syndicate sindicalizado) {
        this.sindicato = sindicalizado;
    }

    public Syndicate getSindicato() {
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

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public abstract String getSalario();

    public abstract void setSalario(String salario);

    public abstract String getTipo();

    public PaymentWay getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(PaymentWay metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }
}
