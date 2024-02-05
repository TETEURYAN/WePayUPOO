package br.ufal.ic.p2.wepayu.models.Services;
import br.ufal.ic.p2.wepayu.Managment.Manage;
import br.ufal.ic.p2.wepayu.Utils.Utils;
import br.ufal.ic.p2.wepayu.Utils.Validate;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.PointCard;
import br.ufal.ic.p2.wepayu.models.ServiceCard;
import br.ufal.ic.p2.wepayu.models.TypesEmpregados.Sales.SaleCard;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class Syndicate {
    private String idMembro;
    private float taxaSindical;

    ArrayList<ServiceCard> service = null;
    public Syndicate(String id, float value){
        this.idMembro = id;
        this.taxaSindical = value;
        this.service = new ArrayList<ServiceCard>();
    }


    public static void addTax(String idSindicate, String dataString, String value) throws Exception {
        float payd = Utils.toFloat(value);
        Empregado ex = Manage.getEmployBySindicate(idSindicate);
        ArrayList<ServiceCard> ans = ex.getSindicato().service;

        if(ans == null){
            ans = new ArrayList<ServiceCard>();
        }
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate dataFormato = LocalDate.parse(dataString, formato);

            ans.add(new ServiceCard(dataFormato, payd));
//            services.replace(idSindicate, services.get((idSindicate)), example);
            ex.getSindicato().service = ans;
        } catch (DateTimeParseException e) {
            throw new Exception("Data invalida.");
        }
        Manage.updateEmploy(ex);
    }

    public static String seeTax(String id, String dataInicial, String dataFinal) throws Exception {
        Empregado e = Manage.getEmpregado(id);
        Syndicate example = e.getSindicato();
        float Taxxing = 0;

        if(example == null){
            return "0,00";
        }
        LocalDate dateInit;
        LocalDate dateEnd;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");
        dateEnd = LocalDate.parse(dataFinal, formato);

        try {
            dateInit = LocalDate.parse(dataInicial, formato);
        } catch (DateTimeParseException e) {
            throw new Exception("Data inicial invalida.");
        }

        if(dateEnd.isBefore(dateInit)){
            throw new Exception("Data inicial nao pode ser posterior aa data final.");
        }

        for (ServiceCard c : example) {
            if (c.getData().isEqual(dateInit) ||
                    (c.getData().isAfter(dateInit) && c.getData().isBefore(dateEnd))){
                Taxxing += c.getTax();
//
            }
        }
        return String.format("%.2f",Taxxing).replace(".", ",");
    }

    public String getIdMembro(){
        return idMembro;
    }
    public float gettaxaSindical(){
        return taxaSindical;
    }

}