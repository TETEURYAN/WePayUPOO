package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.models.TiposPagamento.EmMaos;

/**
 * Classe abstrata {@link Empregado}
 * @author teteuryan faite100
 */
public abstract class Empregado implements Cloneable{
    private String nome;
    private String endereco;
    private String ID;
    private Sindicato sindicalizado;
    private MetodoPagamento metodoPagamento;
    private Agenda agenda;

    public Empregado () {//Construtor vazio para ser serializado na Persistencia em XML

    }

    /**
     * Cria uma instancia de {@link Empregado}, alocando corretamente o nome, endereço, o sindicalizado
     * o método de pagamento, que é inicializado como Em Maos, e a agenda de pagamento
     */
    public Empregado(String nome, String endereco, Agenda agenda) throws Exception {
        this.nome = nome;
        this.endereco = endereco;
        this.sindicalizado = null;
        this.metodoPagamento = new EmMaos();
        this.agenda = agenda;
    }

    public void setSindicalizado(Sindicato sindicalizado) {
        this.sindicalizado = sindicalizado;
    }

    public Sindicato getSindicalizado() {
        return this.sindicalizado;
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

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public abstract double getSalario();

    public abstract void setSalario(double salario);

    public abstract String getTipo();

    public String getId() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    /**
     * Método para clonar a classe Empregado por meio do Cloneable
     */
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
