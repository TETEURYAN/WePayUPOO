package br.ufal.ic.p2.wepayu.Models;

import br.ufal.ic.p2.wepayu.Banking.Banking;
import br.ufal.ic.p2.wepayu.Managment.Manage;
import br.ufal.ic.p2.wepayu.Models.KindEmployee.*;
import br.ufal.ic.p2.wepayu.Utils.Utils;
import br.ufal.ic.p2.wepayu.Utils.Validate;

import java.io.*;
import java.util.*;

/*
    CLasse referente ao Sistema Folha do modelo relacional
 */
public class SistemaFolha implements Serializable {

    public static String saida;

    // headers dos tipos de empregados
    public static final String HEADER_HORISTAS = "templates/horistas.txt";
    public static final String HEADER_ASSALARIADOS = "templates/assalariados.txt";
    public static final String HEADER_COMISSIONADOS = "templates/comissionados.txt";

    public SistemaFolha() { //Contstrutor da folha de pagamento
        Banking.updateEmployByXML(); // Método para carregar a Hash de empregados a partir do XML
    }

    public static String totalSalario(String data) throws Exception{ // Método para calcular o salário de todos os empregados
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

    private static void geraDataHoristas(String data) throws Exception{ // Método para pegar os dados dos horistas e escrever na folha
        SortedSet<String> dataEmploy = new TreeSet<>();//Cria um set para empregados
        List<Double> somaTotal = Arrays.asList(0D, 0D, 0D, 0D, 0D);

        for(Map.Entry<String, Empregado> emp : Manage.employee.entrySet()){//For each na hashmap de empregados
            Empregado e = emp.getValue();
            if(e.getTipo().equals("horista"))
            {
                /*
                    Condicional que avergigua o atributo corretamente e gera um objeto com as informações em linha
                    de cada empregado

                 */
                String dataInicial;
                if(!Validate.validFriday(data)) break;
                else dataInicial = Utils.lastFriday(Validate.toData(data));
                Object[] dados = ((EmpregadoHorista) e).getDataLine(dataInicial, data);
                dataEmploy.add((String) dados[0]);
                somaTotal = Utils.concatenarListas(somaTotal, (List<Double>) dados[1]);
            }
        }

        try{
            BufferedReader reader = new BufferedReader(new FileReader(HEADER_HORISTAS));
            BufferedWriter writer = new BufferedWriter(new FileWriter(saida, true));

            String linha;
            while((linha = reader.readLine()) != null)
            {writer.write(linha);
                writer.newLine();}

            reader.close();

            for(String dado: dataEmploy){
                writer.write(dado);
                writer.newLine();}

            String total = String.format("\n%-36s %5s %5s %13s %9s %15s\n", "TOTAL HORISTAS",
                    Utils.doubleToString(somaTotal.get(0), true),
                    Utils.doubleToString(somaTotal.get(1), true),
                    Utils.doubleToString(somaTotal.get(2), false),
                    Utils.doubleToString(somaTotal.get(3), false),
                    Utils.doubleToString(somaTotal.get(4), false)
            );

            writer.write(total);
            writer.close();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    };

    private static void geraDataAssalariados(String data) throws Exception{// Função para gerar os datos de todos os assalariados
        SortedSet<String> dataEmploy = new TreeSet<>();
        List<Double> somaTotal = Arrays.asList(0D, 0D, 0D);

        for(Map.Entry<String, Empregado> emp : Manage.employee.entrySet()){ //FOr each na hashmap de empregados
            Empregado e = emp.getValue();
            if(e.getTipo().equals("assalariado"))
            {
                /*
                    Condicional que avergigua o atributo corretamente e gera um objeto com as informações em linha
                    de cada empregado
                 */
                String dataInicial;
                if(!Utils.lastDayOfMonth(data)) break;
                else dataInicial = Utils.getPrimeiroDiaMes(data);

                Object[] dados = ((EmpregadoAssalariado) e).getDataLine(dataInicial, data);
                dataEmploy.add((String) dados[0]);
                somaTotal = Utils.concatenarListas(somaTotal, (List<Double>) dados[1]);
            }
        }

        try{
            /*
                 parte do método que força a escrita no TXT
             */
            BufferedReader reader = new BufferedReader(new FileReader(HEADER_ASSALARIADOS));
            BufferedWriter writer = new BufferedWriter(new FileWriter(saida, true));

            String linha;
            while((linha = reader.readLine()) != null)
            {
                writer.write(linha);
                writer.newLine();
            }

            reader.close();

            for(String dado: dataEmploy){
                writer.write(dado);
                writer.newLine();
            }

            String total = String.format("\n%-48s %13s %9s %15s\n", "TOTAL ASSALARIADOS",
                    Utils.doubleToString(somaTotal.get(0), false),
                    Utils.doubleToString(somaTotal.get(1), false),
                    Utils.doubleToString(somaTotal.get(2), false)
            );

            writer.write(total);
            writer.close();

        }catch (IOException e) {System.out.println("Arquivos nao encontrados");}


    }

    private static void geraDataComissionados(String data) throws Exception{
        SortedSet<String> dataEmploy = new TreeSet<>();
        List<Double> somaTotal = Arrays.asList(0D,0D,0D,0D,0D,0D);

        for(Map.Entry<String, Empregado> emp : Manage.employee.entrySet()){
            Empregado e = emp.getValue();
            if(e.getTipo().equals("comissionado"))
            {
                /*
                    Condicional que avergigua o atributo corretamente e gera um objeto com as informações em linha
                    de cada empregado
                 */
                String dataInicial;
                if(!Validate.validPayComissionado(data)) break;
                else dataInicial = Validate.lastDayComissionado(data);
                Object[] dados = ((EmpregadoComissionado) e).getDataLine(dataInicial, data);
                dataEmploy.add((String) dados[0]);
                somaTotal = Utils.concatenarListas(somaTotal,
                        (List<Double>) dados[1]);
            }
        }

        try{
                /*
                    parte do método que força a escrita no TXT
                 */
            BufferedReader reader = new BufferedReader(new FileReader(HEADER_COMISSIONADOS));
            BufferedWriter writer = new BufferedWriter(new FileWriter(saida, true));

            String linha;
            while((linha = reader.readLine()) != null)
            {writer.write(linha);
                writer.newLine();}

            reader.close();

            for(String dado: dataEmploy){
                writer.write(dado);
                writer.newLine();
            }

            String total = String.format("\n%-21s %8s %8s %8s %13s %9s %15s\n", "TOTAL COMISSIONADOS",
                    Utils.doubleToString(somaTotal.get(0), false),
                    Utils.doubleToString(somaTotal.get(1), false),
                    Utils.doubleToString(somaTotal.get(2), false),
                    Utils.doubleToString(somaTotal.get(3), false),
                    Utils.doubleToString(somaTotal.get(4), false),
                    Utils.doubleToString(somaTotal.get(5), false)
            );

            writer.write(total);
            writer.write("\nTOTAL FOLHA: " + totalSalario(data));
            writer.close();

        }catch (IOException e) {System.out.println("Arquivos nao encontrados");}

    }

    public static void geraFolha(String data, String saida) throws Exception{

        setSaida(saida);
        String dia = saida.substring(6, 16);

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(saida));

            writer.write("FOLHA DE PAGAMENTO DO DIA " + dia);
            writer.newLine();

            writer.close();
        }catch (IOException e) {System.out.println("Arquivo nao encontrado");}
        geraDataHoristas(data);
        geraDataAssalariados(data);
        geraDataComissionados(data);
    }

    private static void setSaida(String saida) {
    }

    public static String totalFolha(String data) throws Exception {
        return "";
    }
}