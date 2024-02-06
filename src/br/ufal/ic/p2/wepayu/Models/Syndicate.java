package br.ufal.ic.p2.wepayu.Models;

import br.ufal.ic.p2.wepayu.Models.KindCard.CardService;
import br.ufal.ic.p2.wepayu.utils.Validate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Syndicate {
    private String idMembro;
    private double adicionalSindicato;
    private ArrayList<CardService> services;

    public Syndicate(String idMembro, double taxaSindical) {
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

    public double getTaxasServico(String dataInicial, String dataFinal) throws Exception{
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");

        LocalDate inicialDate = null;
        LocalDate finalDate = null;

        try {
            inicialDate = LocalDate.parse(Validate.valiDate(dataInicial), formato);
        } catch (Exception e) {throw new Exception("Data inicial invalida.");}

        try{
            finalDate = LocalDate.parse(Validate.valiDate(dataFinal), formato);
        } catch (Exception e) {throw new Exception("Data final invalida.");}

        if(inicialDate.isAfter(finalDate)) throw new Exception("Data inicial nao pode ser posterior aa data final.");
        if(inicialDate.isEqual(finalDate)) return 0d;

        Double countTaxa = 0d;
        for(CardService taxa: services){
            LocalDate data = taxa.getData();
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
    }
}
