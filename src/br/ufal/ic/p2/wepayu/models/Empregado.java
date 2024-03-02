package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.models.KindPayment.EmMaos;

public abstract class Empregado implements Cloneable{
    private String nome;
    private String endereco;
    private Sindicato sindicalizado;
    private MetodoPagamento metodoPagamento;

    public Empregado () {

    }

    public Empregado(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.sindicalizado = null;
        this.metodoPagamento = new EmMaos();
    }

    public void setSindicalizado(Sindicato sindicalizado) {
        this.sindicalizado = sindicalizado;
    }

    public Sindicato getSindicalizado() {
        return this.sindicalizado;
    }

//    public void addTaxaServico (TaxaServico taxaServico) {
//        this.sindicalizado.addTaxaServico(taxaServico);
//    }

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

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public abstract double getSalario();

    public abstract void setSalario(double salario);

    public abstract String getTipo();

    @Override
    public Empregado clone() {
        try {
            Empregado clone = (Empregado) super.clone();

            if (this.sindicalizado != null)
                clone.sindicalizado = this.sindicalizado.clone();
            if(this.metodoPagamento != null)
                clone.metodoPagamento = this.metodoPagamento.clone();


            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
