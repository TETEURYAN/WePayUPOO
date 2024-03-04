package br.ufal.ic.p2.wepayu.models;

/**
 * Classe abstrata {@link MetodoPagamento}, referente ao meio de pagamento do Empregado
 * @author teteuryan faite100
 */
public abstract class MetodoPagamento implements Cloneable{

    public MetodoPagamento () {//Construtor vazio para ser serializado na Persistencia em XML

    }

    public abstract String getMetodoPagamento();

    public abstract String getOutputFile();

    /**
     * Método para clonar a classe MetodoPagamento por meio do Cloneable
     */
    @Override
    public MetodoPagamento clone() {
        try {
            return (MetodoPagamento) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
