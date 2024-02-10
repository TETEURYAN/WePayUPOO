package br.ufal.ic.p2.wepayu.Models;

import br.ufal.ic.p2.wepayu.Models.KindCard.CardService;
import br.ufal.ic.p2.wepayu.Utils.Validate;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/*
    Classe referente ao MembroSindicato do Modelo Relacional
 */
public class MembroSindicato implements Serializable {

    public MembroSindicato(){

    }
    private String idMembro;
    private double adicionalSindicato;
    private ArrayList<CardService> services; //Vetor de cartões de serviço

    public MembroSindicato(String idMembro, double taxaSindical) {
        this.idMembro = idMembro;
        this.adicionalSindicato = taxaSindical;
        this.services = new ArrayList<CardService>();
    }

    public ArrayList<CardService> getServices() {
        return this.services;
    }

    public double getAdicionalSindicato() {
        return this.adicionalSindicato;
    }

    public String getIdMembro() {
        return this.idMembro;
    }

    public double getTaxasServico(String dataInicial, String dataFinal) throws Exception{ // Método para calcular as taxas de serviço totais
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");

        LocalDate inicialDate = null;
        LocalDate finalDate = null;

        try {//Validar data de inicio
            inicialDate = LocalDate.parse(Validate.valiDate(dataInicial), formato);
        } catch (Exception e) {throw new Exception("Data inicial invalida.");}

        try{// Valdiar data de término
            finalDate = LocalDate.parse(Validate.valiDate(dataFinal), formato);
        } catch (Exception e) {throw new Exception("Data final invalida.");}

        if(inicialDate.isAfter(finalDate)) throw new Exception("Data inicial nao pode ser posterior aa data final.");//Averigua inconsistÊncia
        if(inicialDate.isEqual(finalDate)) return 0d;

        Double countTaxa = 0d;
        for(CardService taxa: services){ // Um laço for para contar cada cartão de serviço registrado
            LocalDate data = LocalDate.parse(Validate.valiDate(taxa.getData()), formato);
            if(data.isEqual(inicialDate)) {
                countTaxa += taxa.getValor();
            } else{
                if(data.isAfter(inicialDate) && data.isBefore(finalDate)) {
                    countTaxa += taxa.getValor();
                }
            }
        }
        return countTaxa;
    }

    public void addCardService (CardService taxaServico) {
        this.services.add(taxaServico);
    }// Adicionar cartõa de serviço ao vetor de cartão de serviço

    public void setServices(ArrayList<CardService> services) {
        this.services = services;
    }

    public void setIdMembro(String idMembro) {
        this.idMembro = idMembro;
    }

    public void setAdicionalSindicato(double adicionalSindicato) {
        this.adicionalSindicato = adicionalSindicato;
    }

    public Double getClearExtra() { //Método para limpar a taxa extra do sindicato
        Double TotalValue = 0d;
        for(CardService taxa: services){
            TotalValue += taxa.getValor();
        }
        services.clear();
        return TotalValue;
    }

}
