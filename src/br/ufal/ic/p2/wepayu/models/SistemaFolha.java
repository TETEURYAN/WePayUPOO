package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.models.TiposEmpregados.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.services.Settings;
import br.ufal.ic.p2.wepayu.utils.Utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.io.FileWriter;

/**
 * Cuilder da Classe {@link SistemaFolha}, ao qual se refere a Folha de Pagamento
 * @author teteuryan faite100
 */
public class SistemaFolha {

    private String arquivoSaida;//Arquivo de saida da folha
    private static SistemaFolha folha;//Instancia unica da folha
    private DBmanager session;//Banco de dados

    private SistemaFolha() {
        this.session = DBmanager.getDatabase();
    }//Construtor provado da folha, para garantir o Singleton

    public static SistemaFolha getFolha(){//Método para obtenção da instancia única da folha de pagamento. Usando o Singleton
        if(folha == null){
            folha = new SistemaFolha();
        }
        return folha;
    }

    public void setArquivoSaida(String arquivoSaida) {
        this.arquivoSaida = arquivoSaida;
    }

    public void geraFolha(LocalDate dataCriacao, String arquivoSaida) throws Exception {//Método para gerar a folha de pagamento
        if (this.arquivoSaida.isEmpty()) throw new Exception("Arquivo de saída não especificado");

        double total = 0;

        try (FileWriter escritor = new FileWriter(arquivoSaida)) {
            // Cabeçalho
            escritor.write("FOLHA DE PAGAMENTO DO DIA " + dataCriacao.toString() + '\n');
            escritor.write("====================================");
            escritor.write("\n");
            escritor.write("\n");

            // Dados dos empregados

            /**
             * Escrita do total de Empregado Horistas para escrita no writter
             */
            total += calculaHoristas(escritor, dataCriacao);
            escritor.write("\n");

            /**
             * Try para a tentativa de calcular o total de assalariados para escrever no writer
             */
            try {
                total += calculaAssalariados(escritor, dataCriacao);
                escritor.write("\n");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }

            /**
             * Escrita do total de Empregado Comissionado para escrita no writter
             */
            total += calculaComissionados(escritor, dataCriacao);
            escritor.write("\n");


            escritor.write("TOTAL FOLHA: " + Utils.convertDoubleToString(total, 2) + "\n");
            escritor.close();
        }
         catch (Exception e) {
        }

    }

    /**
     * Metodo para calculo do valor total de EMpregados Assariados, dada
     * um interfvalo de tempo eum arquivo de saida
     */
    public double calculaAssalariados(FileWriter escritor, LocalDate dataCriacao) throws Exception {
        if (this.arquivoSaida.isEmpty()) throw new Exception("Arquivo de saída não especificado");

        double totalSalarioBruto = 0;
        double totalDescontos = 0;
        double totalSalarioLiquido = 0;


            Utils.writeEmpregadoHeader(escritor, "ASSALARIADOS");

            HashMap<String, String> empregados = Utils.ordenaEmpregadosByName(DBmanager.getEmpregadoAssalariado());//Metodo para puxar os empregados em ordem alfabetica
            LocalDate dataInicial = dataCriacao.minusDays(dataCriacao.lengthOfMonth() - 1);

            if(dataCriacao.getDayOfMonth() != dataCriacao.lengthOfMonth()) {//Considerando o dia de pagamento como o ultimo dia do mes
                escritor.write("\n");
                String footer = Utils.EspacosDireita("TOTAL ASSALARIADOS", 48);
                footer += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalSalarioBruto, 2), 14);
                footer += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalDescontos, 2), 10);
                footer += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalSalarioLiquido, 2), 16) + "\n";
                escritor.write(footer);

                return totalSalarioBruto;
            }

            for (Map.Entry<String, String> entry: empregados.entrySet()){
                EmpregadoAssalariado e = (EmpregadoAssalariado) DBmanager.getEmpregado(entry.getKey());

                String nome = e.getNome();
                double salarioBruto = e.getSalario();
                salarioBruto = ((int)(salarioBruto*100))/100f;
                double descontos = 0;

                Sindicato ms = e.getSindicalizado();

                if (ms != null) {
                    descontos = dataCriacao.lengthOfMonth() * ms.getTaxaSindical();

                    descontos += ms.getTaxaServicos(dataInicial, dataCriacao);;
                }

                double salarioLiquido = salarioBruto - descontos;

                if (salarioLiquido <= 0) salarioLiquido = 0;

                totalSalarioBruto += salarioBruto;
                totalSalarioLiquido += salarioLiquido;
                totalDescontos += descontos;

                String metodo = e.getMetodoPagamento().getOutputFile() + ", " + e.getEndereco() ;

                Utils.escreveAssalariado(escritor, nome, salarioBruto, descontos, salarioLiquido, metodo);
            }

        escritor.write("\n");

        /**
         * Escrita do total de Empregado Assalariados no writerr
         */
            String footer = Utils.EspacosDireita("TOTAL ASSALARIADOS", 48);
            footer += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalSalarioBruto, 2), 14);//Salario bruto
            footer += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalDescontos, 2), 10);//Salario com desconto
            footer += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalSalarioLiquido, 2), 16) + "\n";//Salario liquido
            escritor.write(footer);


        return totalSalarioBruto;

    }

    /**
     * Metodo para calculo do valor total de EMpregados Horistas, dada
     * um interfvalo de tempo eum arquivo de saida
     */
    public double calculaHoristas(FileWriter escritor, LocalDate dataCriacao) throws Exception {
        double totalHorasNormais = 0;
        double totalHorasExtras = 0;
        double totalSalarioBruto = 0;
        double totalDescontos = 0;
        double totalSalarioLiquido = 0;


        Utils.writeEmpregadoHeader(escritor, "HORISTAS");


        if (dataCriacao.getDayOfWeek() != DayOfWeek.FRIDAY) {//Considerando o dia de pagamento como uma sexta
            escritor.write("\n");
            String line = Utils.EspacosDireita("TOTAL HORISTAS", 36);
            line += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalHorasNormais), 6);
            line += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalHorasExtras), 6);
            line += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalSalarioBruto, 2), 14);
            line += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalDescontos, 2), 10);
            line += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalSalarioLiquido, 2), 16) + "\n";
            escritor.write(line);

            return totalSalarioBruto;
        };

        HashMap<String, String> empregados = Utils.ordenaEmpregadosByName(DBmanager.getEmpregadoHoristas());//Puixar os Empregagos Horitas por ordem alfabetica
        LocalDate dataInicial = dataCriacao.minusDays(6);

        for (Map.Entry<String, String> entry: empregados.entrySet()) {
            EmpregadoHorista e = (EmpregadoHorista) DBmanager.getEmpregado(entry.getKey());


            String nome = e.getNome();
            double horaNormais = e.getHorasNormaisTrabalhadas(dataInicial, dataCriacao);
            double horaExtras = e.getHorasExtrasTrabalhadas(dataInicial, dataCriacao);
            double salarioBruto = e.getSalarioBruto(dataInicial, dataCriacao);
            salarioBruto = ((int)(salarioBruto*100))/100f;
            double descontos = 0;

            Sindicato ms = e.getSindicalizado();

            if (ms != null) {
                descontos += 7 * ms.getTaxaSindical();
                descontos += ms.getTaxaServicos(dataInicial, dataCriacao);
                descontos += e.getDescontos();
            }


            double salarioLiquido = salarioBruto - descontos;

            if (salarioLiquido < 0) {
                e.setDescontos(descontos);
                salarioLiquido = 0;
                descontos = 0;
            }

            totalHorasNormais += horaNormais;
            totalHorasExtras += horaExtras;
            totalSalarioBruto += salarioBruto;
            totalDescontos += descontos;
            totalSalarioLiquido += salarioLiquido;

            session.setValue(entry.getKey(),e);//Atualiza no banco de dados
            Utils.escreveHorista(escritor, nome, horaNormais, horaExtras, salarioBruto, descontos, salarioLiquido, e.getMetodoPagamento().getOutputFile());
        }

        /**
         * Escrita do total de Empregado Horistas no writerr
         */
        escritor.write("\n");
        String line = Utils.EspacosDireita("TOTAL HORISTAS", 36);
        line += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalHorasNormais), 6);//Horas normais
        line += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalHorasExtras), 6);//Horas extras
        line += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalSalarioBruto, 2), 14);//Salario Bruto
        line += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalDescontos, 2), 10);//Descontos
        line += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalSalarioLiquido, 2), 16) + "\n";//Salario Liquido
        escritor.write(line);


        return totalSalarioBruto;


    }

    /**
     * Metodo para calculo do valor total de Empregados Comissionados, dada
     * um interfvalo de tempo eum arquivo de saida
     */
    public double calculaComissionados(FileWriter escritor, LocalDate dataCriacao) throws Exception {
        if (this.arquivoSaida.isEmpty()) throw new Exception("Arquivo de saída não especificado");

        double totalSalarioFixo = 0;
        double totalVendas = 0;
        double totalComissao = 0;
        double totalSalarioBruto = 0;
        double totalDescontos = 0;
        double totalSalarioLiquido = 0;

        Utils.writeEmpregadoHeader(escritor, "COMISSIONADOS");


        boolean multiplo =  (ChronoUnit.DAYS.between(LocalDate.of(2005, 1, 1), dataCriacao) + 1) % 14 == 0;

        boolean diaDePagarComissionado = dataCriacao.getDayOfWeek() == DayOfWeek.FRIDAY && multiplo;//Verifica se é dia de pagamento dos comissionados


        if (!diaDePagarComissionado) {
            escritor.write("\n");
            String footer = Utils.EspacosDireita("TOTAL COMISSIONADOS", 21);

            /**
             * Escrita do total de Empregado Comissionado na ocasiao de dia de pagamento
             */
            footer += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalSalarioFixo, 2), 9);//Salario fixo
            footer += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalVendas, 2), 9);//Total de vendas
            footer += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalComissao, 2), 9);//Total de Comissao
            footer += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalSalarioBruto, 2), 14);//TOtal Salario Bruto
            footer += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalDescontos, 2), 10);// Total Descontos
            footer += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalSalarioLiquido, 2), 16) + "\n";//Total Salario Liquido
            escritor.write(footer);

            return totalSalarioBruto;
        }

        HashMap<String, String> empregados = Utils.ordenaEmpregadosByName(DBmanager.getEmpregadoComissionado());//Puxa os empregados Comissionados pelo nome

        LocalDate dataInicial = dataCriacao.minusDays(13);

        try {


        for (Map.Entry<String, String> entry: empregados.entrySet()) {
            EmpregadoComissionado e = (EmpregadoComissionado) DBmanager.getEmpregado(entry.getKey());

            String nome = e.getNome();

            double salarioFixo = 0;
            double vendas = 0;
            double comissao = 0;
            double salarioBruto = 0;
            double descontos = 0;

            double salarioLiquido = 0;

            salarioFixo = e.getSalario();
            salarioFixo = Math.floor((salarioFixo*12D/52D)*2D * 100)/100F;

            salarioFixo = ((int)(salarioFixo * 100))/100.0f;
            vendas = e.getVendasRealizadas(dataInicial, dataCriacao);
            comissao = e.getTaxaDeComissao();

            comissao = vendas * comissao;

            comissao = Math.floor(comissao*100)/100F;

            salarioBruto = salarioFixo + comissao;

            Sindicato m = e.getSindicalizado();

            if (m != null)
            {
                double diasDiff = ChronoUnit.DAYS.between(dataInicial, dataCriacao) + 1;
                descontos = m.getTaxaServicos(dataInicial, dataCriacao) + diasDiff * m.getTaxaSindical();
            }

            if (salarioBruto >= descontos) {
                salarioLiquido = salarioBruto - descontos;
            }

            totalSalarioLiquido += salarioLiquido;
            totalComissao += comissao;
            totalSalarioBruto += salarioBruto;
            totalVendas += vendas;
            totalSalarioFixo += salarioFixo;
            totalDescontos += descontos;


            String metodo = e.getMetodoPagamento().getOutputFile() + ", " + e.getEndereco();

            Utils.escreveComissionado(escritor, nome, salarioFixo, vendas, comissao, salarioBruto, descontos, salarioLiquido, metodo);

        }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        /**
         * Escrita do total de Empregados Comissionados no writter
         */
        escritor.write("\n");
        String footer = Utils.EspacosDireita("TOTAL COMISSIONADOS", 21);

        footer += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalSalarioFixo, 2), 9);
        footer += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalVendas, 2), 9);
        footer += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalComissao, 2), 9);
        footer += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalSalarioBruto, 2), 14);
        footer += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalDescontos, 2), 10);
        footer += Utils.EspacosEsquerda(Utils.convertDoubleToString(totalSalarioLiquido, 2), 16);


        escritor.write(footer);

        escritor.write("\n");

        return totalSalarioBruto;
    }

    public double totalFolha(LocalDate dataCriacao) throws Exception {

        Double total = 0d;
        for(Map.Entry<String, Empregado> emp: session.getEmpregados().entrySet()){
            Empregado e = emp.getValue();
            Agenda agenda = e.getAgenda();
            switch (e.getTipo())
            {
                case "horista" -> {
                    // Verifica se é dia de pagamento dos Empregados Horistas, ou seja, se é sexta
                    if(Utils.diaDePagamento(dataCriacao, agenda)){
                        String dataInicial = Utils.getUltimoDiaDePagamento(dataCriacao, agenda);
                        LocalDate dataInit = LocalDate.parse(dataInicial, Settings.formatter);
                        total += ((EmpregadoHorista) e).getSalarioBruto(dataInit, dataCriacao);
                    }
                }
                case "assalariado" -> {
                    // Verifica se é dia de pagamento dos assalariados, ou seja, se é o ultimo dia útil do mes
                    if(Utils.diaDePagamento(dataCriacao, agenda)){
                        total += ((EmpregadoAssalariado)e).getSalarioBruto();
                    }
                }
                case "comissionado" -> {
                    // Verifica se há algum comissionado a ser pago no dia
                    if(Utils.diaDePagamento(dataCriacao, agenda)){
                        String dataInicial = Utils.getUltimoDiaDePagamento(dataCriacao, agenda);
                        LocalDate dataInit = LocalDate.parse(dataInicial, Settings.formatter);
                        total += ((EmpregadoComissionado) e).getSalarioBruto(dataInit, dataCriacao);
                    }
                }
            }
        }
        return total;
    }
}

