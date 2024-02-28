package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.exceptions.ExceptionEmpregado;
import br.ufal.ic.p2.wepayu.models.KindCard.CardService;
import br.ufal.ic.p2.wepayu.utils.Utils;

import java.time.LocalDate;
import java.util.ArrayList;

public class Sindicato {
    private String idMembro;
    private double taxaSindical;
    private ArrayList<CardService> taxaServicos;

    public Sindicato() {

    }

    public Sindicato(String idMembro, double taxaSindical) {
        this.idMembro = idMembro;
        this.taxaSindical = taxaSindical;
        this.taxaServicos = new ArrayList<>();
    }

    public double getTaxaServicos(LocalDate dataInicial, LocalDate dataFinal) throws Exception {

        double countTaxas = 0;

        if (dataInicial.isAfter(dataFinal)) {
            ExceptionEmpregado e = new ExceptionEmpregado();
            e.msgDataInicialPosteriorDataFinal();

            return countTaxas;
        }

        if (dataInicial.isEqual(dataFinal)) {
            return countTaxas;
        }

        for (CardService t : this.taxaServicos) {

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

    public void addTaxaServico (CardService taxaServico) {
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

    public void setTaxaServicos(ArrayList<CardService> taxaServicos) {
        this.taxaServicos = taxaServicos;
    }

    public void setTaxaSindical(double taxaSindical) {
        this.taxaSindical = taxaSindical;
    }

    public ArrayList<CardService> getTaxaServicos() {
        return taxaServicos;
    }
}
