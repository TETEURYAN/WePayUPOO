package br.ufal.ic.p2.wepayu.Models;

import br.ufal.ic.p2.wepayu.Banking.Banking;
import br.ufal.ic.p2.wepayu.Managment.Manage;
import br.ufal.ic.p2.wepayu.Models.KindEmployee.*;
import br.ufal.ic.p2.wepayu.Utils.Utils;
import br.ufal.ic.p2.wepayu.Utils.Validate;


import java.io.Serializable;
import java.util.Map;

public class Payroll implements Serializable {

    public Payroll() {
        Banking.updateEmployByXML();
    }

//    public static void rodaFolha(String data, String saida) throws Exception {
//
//    }

    public static String totalSalario(String data) throws Exception{
        double totalPayment = 0d;
        for(Map.Entry<String, Empregado> emp: Manage.employee.entrySet()){
            Empregado e = emp.getValue();
            switch (e.getTipo())
            {
                case "horista" -> {
                    if(Validate.validFriday(data) && e instanceof EmpregadoHorista){
                        String dataInicial = Utils.lastFriday(Validate.toData(data));
                        String generalHoras = ((EmpregadoHorista) e).getHorasNormaisTrabalhadas(dataInicial, data);
                        String extraHoras = ((EmpregadoHorista) e).getHorasExtrasTrabalhadas(dataInicial, data);
                        String salario = e.getSalario();
                        totalPayment += Utils.quitValue(generalHoras) * Utils.quitValue(salario) + 1.5 * Utils.quitValue((extraHoras)) * Utils.quitValue(salario);
                    }
                }
                case "assalariado" -> {
                    if(Validate.validLastDay(data)){
                        totalPayment += Utils.quitValue(e.getSalario());
                    }
                }
                case "comissionado" -> {
                    if(Validate.validPayComissionado(data)){
                        String dataInicial = Validate.lastDayComissionado(data);
                        totalPayment += ((EmpregadoComissionado) e).getBruto(dataInicial, data);
                    }
                }
            }
        }
        return Utils.DoubleString(totalPayment);
    }

    public static String totalFolha(String data) throws Exception {
        return "beleza";
    }
}