package br.ufal.ic.p2.wepayu.services;

import java.time.format.DateTimeFormatter;

/**
 * Classe organizar melhor os caminhos do sistema
 * @author teteuryan faite100
 */
public class Settings {

    public static final String COMISSIONADO = "comissionado";
    public static final String HORISTA = "horista";
    public static final String ASSALARIADO = "assalariado";
    public static final String[] PADRAO_AGENDA = {"semanal 5", "semanal 2 5", "mensal $"};
    public static final String[] TIPO_AGENDAS = {"semanal", "mensal"};
    public static final String[] TIPO_ATRIBUTO = {"nome", "tipo", "salario", "endereco", "comissao", "metodoPagamento", "banco", "agencia", "contaCorrente", "sindicalizado", "idSindicato", "taxaSindical", "agendaPagamento"};
    public static String PADRAO_COMISSIONADO =  "semanal 2 5";
    public static String PADRAO_ASSALARIADO = "mensal $";
    public static String PADRAO_HORISTA = "semanal 5";
    public static String PATH_AGENDA = "AgendaPagamento";
    public static String PATH_PERSISTENCIA = "ListaEmpregados";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
}
