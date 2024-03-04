package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.models.TiposCartao.CartaoDeServico;
import br.ufal.ic.p2.wepayu.utils.Utils;
import br.ufal.ic.p2.wepayu.utils.Validate;

import java.time.LocalDate;
import java.util.ArrayList;
/**
 * Classe {@link Sindicato}, ao qual se refere ao sindicato dos membros sindicalizados
 * @author teteuryan faite100
 */
public class Sindicato implements Cloneable {
    private String idMembro;//Id do membro no sindicato
    private double taxaSindical;//Taxa Sindical
    private ArrayList<CartaoDeServico> taxaServicos;// Vetor de Taxas de Servico

    public Sindicato() {//Construtor vazio para ser serializado na Persistencia em XML

    }

    /**
     * Cria uma instancia de {@link Sindicato}, alocando corretamente o ID do Membro, a taza sindical e a
     * o vetor de Taxas de Servico
     */
    public Sindicato(String idMembro, double taxaSindical) {
        this.idMembro = idMembro;
        this.taxaSindical = taxaSindical;
        this.taxaServicos = new ArrayList<>();
    }

    public double getTaxaServicos(LocalDate dataInicial, LocalDate dataFinal) throws Exception {// Método que calcula a taxa de serviço de um Empregado dado um intervalo de tempo

        double countTaxas = 0;

        Validate.validCorrectData(dataInicial, dataFinal);

        if (dataInicial.isEqual(dataFinal)) {
            return countTaxas;
        }

        for (CartaoDeServico t : this.taxaServicos) {

            LocalDate dataFormato = Utils.validData(t.getData(), "");

            if (dataFormato != null) {
                if (dataFormato.isEqual(dataInicial) ||
                        (dataFormato.isAfter(dataInicial) && dataFormato.isBefore(dataFinal))) {
                    countTaxas += t.getValor();
                }
            }
        }

        return countTaxas;
    }

    public void addTaxaServico (CartaoDeServico taxaServico) {
        this.taxaServicos.add(taxaServico);
    }

    public double getTaxaSindical() {
        return this.taxaSindical;
    }

    public String getIdMembro() {
        return this.idMembro;
    }

    public void setIdMembro(String idMembro) {
        this.idMembro = idMembro;
    }

    public void setTaxaServicos(ArrayList<CartaoDeServico> taxaServicos) {
        this.taxaServicos = taxaServicos;
    }

    public void setTaxaSindical(double taxaSindical) {
        this.taxaSindical = taxaSindical;
    }

    public ArrayList<CartaoDeServico> getTaxaServicos() {
        return taxaServicos;
    }

    /**
     * Método para clonar a classe Sindicato por meio do Cloneable
     */
    @Override
    public Sindicato clone() {
        try {
            Sindicato clone = (Sindicato) super.clone();

            clone.taxaServicos = new ArrayList<>();

            for(CartaoDeServico taxa: taxaServicos){
                clone.taxaServicos.add(taxa.clone());
            }


            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
