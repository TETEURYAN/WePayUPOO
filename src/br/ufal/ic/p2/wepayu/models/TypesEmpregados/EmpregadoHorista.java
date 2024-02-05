package br.ufal.ic.p2.wepayu.models.TypesEmpregados;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.PointCard;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

public class EmpregadoHorista extends Empregado {
    private String SalarioHora;

    private ArrayList<PointCard> cartao;


    public EmpregadoHorista(String nome, String endereco, String tipo, String salario) throws Exception {
        super(nome, endereco, tipo, salario, false);
        if(!tipo.equalsIgnoreCase("horista")){
            throw new TipoNaoAplicavalExcpetion("Tipo nao aplicavel.");
        }
        this.SalarioHora = salario;
        this.cartao = new ArrayList<PointCard>();
    }


    public void addRegistroHorista(String dataString, String horas) throws Exception {

        if (Double.parseDouble(horas.replace(",", ".")) <= 0) {
            throw new Exception("Horas devem ser positivas.");
        }

        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate dataFormato = LocalDate.parse(dataString, formato);

            this.cartao.add(new PointCard(dataFormato, Double.parseDouble(horas.replace(",", "."))));
        } catch (DateTimeParseException e) {
            throw new Exception("Data invalida.");
        }

    }

    public String getHorasNormaisTrabalhadas(String dataIncial, String dataFinal) throws Exception {

        double horasAcumuladas = 0;
        LocalDate dateInit;
        LocalDate dateEnd;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");

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

        dateEnd = LocalDate.parse(dataFinal, formato);

        try {
            dateInit = LocalDate.parse(dataIncial, formato);
        } catch (DateTimeParseException e) {
            throw new Exception("Data inicial invalida.");
        }

        if (dateInit.isAfter(dateEnd)) {
            throw new Exception("Data inicial nao pode ser posterior aa data final.");
        }

        if (dateInit.isEqual(dateEnd)) {
            return "0";
        }

        for (PointCard c : cartao) {
            if (c.getData().isEqual(dateInit) ||
                    (c.getData().isAfter(dateInit) && c.getData().isBefore(dateEnd))) {

//                System.out.println(c.getData() + " " + c.getHoras());

                if (c.getHoras() > 8) {
                    horasAcumuladas += 8.0;
                } else {
                    horasAcumuladas += c.getHoras();
                }
            }
        }

        if (horasAcumuladas != (int) horasAcumuladas)
            return Double.toString(horasAcumuladas).replace(".", ",");

        return Integer.toString((int) horasAcumuladas);
    }

    public String getHorasExtrasTrabalhadas(String dataIncial, String dataFinal) {
        double horasAcumuladas = 0;

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");

        LocalDate dateInit = LocalDate.parse(dataIncial, formato);
        LocalDate dateEnd = LocalDate.parse(dataFinal, formato);

        for (PointCard c : cartao) {
            if (c.getData().isEqual(dateInit) ||
                    (c.getData().isAfter(dateInit) && c.getData().isBefore(dateEnd))) {

                if (c.getHoras() > 8) {
                    horasAcumuladas += (c.getHoras() - 8.0);
                }
            }
        }

        if (horasAcumuladas != (int) horasAcumuladas)
            return Double.toString(horasAcumuladas).replace(".", ",");

        return Integer.toString((int) horasAcumuladas);
    }

    public String getSalarioHora(){
        return SalarioHora;
    }

    @Override
    public String getTipo() {
        return "horista";
    }

    @Override
    public String getSalario() {
        return SalarioHora;
    }
}
