package br.ufal.ic.p2.wepayu.models.TypesEmpregados;
import br.ufal.ic.p2.wepayu.models.PointCard;
import br.ufal.ic.p2.wepayu.models.TypesEmpregados.Sales.SaleCard;

import java.text.DecimalFormat;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import br.ufal.ic.p2.wepayu.Exception.SalarioException;
import br.ufal.ic.p2.wepayu.Exception.TipoNaoAplicavalExcpetion;
import br.ufal.ic.p2.wepayu.Utils.Validate;
import br.ufal.ic.p2.wepayu.models.Empregado;

public class EmpregadoComissionado extends Empregado {
    private static String comissao;
    private String salario;

    private ArrayList<SaleCard>  saleResults;
    public EmpregadoComissionado(String nome, String endereco, String tipo, String salario, boolean sind, String comissao) throws Exception {
        super(nome, endereco, tipo, salario, sind);

        if(!tipo.equalsIgnoreCase("comissionado")){
            throw new TipoNaoAplicavalExcpetion("Tipo nao aplicavel.");
        }
        if (Validate.isNull(comissao)){
            throw new SalarioException("Comissao nao pode ser nula.");
        }else if (Validate.isNegative(comissao)){
            throw new SalarioException("Comissao deve ser nao-negativa.");
        }else if (Validate.isNotSalary(comissao)){
            throw new SalarioException("Comissao deve ser numerica.");
        }

        this.saleResults = new ArrayList<SaleCard>();
        this.comissao = comissao;
        this.salario = salario;
    }

    public void addRegistroComissao(String dataString, float value) throws Exception {
        if(value <= 0.0){
            throw new Exception("Valor deve ser positivo.");
        }
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate dataFormato = LocalDate.parse(dataString, formato);

            this.saleResults.add(new SaleCard(dataFormato, value));
        } catch (DateTimeParseException e) {
            throw new Exception("Data invalida.");
        }
    }

    public String getSales(String dataIncial, String dataFinal) throws Exception {
        float salary = 0;
//        DecimalFormat formatter = new DecimalFormat("#,00");
        LocalDate dateInit;
        LocalDate dateEnd;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");
        dateEnd = LocalDate.parse(dataFinal, formato);

        try {
            dateInit = LocalDate.parse(dataIncial, formato);
        } catch (DateTimeParseException e) {
            throw new Exception("Data inicial invalida.");
        }

        if(dateEnd.isBefore(dateInit)){
            throw new Exception("Data inicial nao pode ser posterior aa data final.");
        }

        int d = 0, m = 0, y, i = 0;

        for (String s : dataFinal.split("/")) {
            if (i == 0) {
                d = Integer.parseInt(s);
                i++;
            } else if (i == 1) {
                m = Integer.parseInt(s);
                i++;
            } else {
                y = Integer.parseInt(s);
            }
        }

        if (m == 2 && d > 29) {
            throw new Exception("Data final invalida.");
        }

        if(dateInit.isEqual(dateEnd)){
            return "0,00";
        }

        for (SaleCard c : saleResults) {
            if (c.getSaleDate().isEqual(dateInit) ||
                    (c.getSaleDate().isAfter(dateInit) && c.getSaleDate().isBefore(dateEnd))){
                  salary += c.getValue();
//
            }
        }
        return String.format("%.2f",salary).replace(".", ",");
    }
    public static String getComissao(){
        return comissao;
    }


    public String getTipo() {
        return "comissionado";
    }

    public ArrayList<SaleCard> getSaleResults() {return saleResults;}

    @Override
    public String getSalario() {
        return salario;
    }
}
