package br.ufal.ic.p2.wepayu.utils;

import br.ufal.ic.p2.wepayu.exceptions.Agenda.ExceptionAgenda;
import br.ufal.ic.p2.wepayu.exceptions.Banco.AgenciaNuloException;
import br.ufal.ic.p2.wepayu.exceptions.Banco.BancoNuloException;
import br.ufal.ic.p2.wepayu.exceptions.Banco.ContaCorrenteNuloException;
import br.ufal.ic.p2.wepayu.exceptions.Banco.NotGetBancoException;
import br.ufal.ic.p2.wepayu.exceptions.Employ.AtributoException;
import br.ufal.ic.p2.wepayu.exceptions.Employ.ComissaoException;
import br.ufal.ic.p2.wepayu.exceptions.Employ.SalarioException;
import br.ufal.ic.p2.wepayu.exceptions.Employ.TypeEmpregadoException;
import br.ufal.ic.p2.wepayu.exceptions.Query.*;
import br.ufal.ic.p2.wepayu.Exceptions.Employ.EmpregadoException;
import br.ufal.ic.p2.wepayu.exceptions.Sindicato.*;
import br.ufal.ic.p2.wepayu.models.Agenda;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.MetodoPagamento;
import br.ufal.ic.p2.wepayu.models.Sindicato;
import br.ufal.ic.p2.wepayu.services.DBmanager;
import br.ufal.ic.p2.wepayu.services.Settings;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;


/**
 * Classe para a validação de entradas, metodos, e Exceptions
 * ordenação e entre outros
 */
public class Validate {

    private static DBmanager session = null;
    private Validate(){
        this.session = DBmanager.getDatabase();
    }

    //Método para converter uma data em string para data em LocalDate
    public static LocalDate toData(String data) throws
            Exception{
        LocalDate date;
        date = LocalDate.parse(data, Settings.formatter);
        return date;
    }

    //Seção de métodos para trablhar com comissionado
    public static String lastDayComissionado(String data) { // Método para puxar o dia de pagamento do comissionado
        LocalDate dataParse = LocalDate.parse(data, Settings.formatter);
        LocalDate dataInit = dataParse.minusDays(13);
        return dataInit.format(Settings.formatter);
    }
    public static boolean validPayComissionado(String data) { // Método para checar se é um dia válido para pagar o comissionado
        LocalDate dataParse = LocalDate.parse(data, Settings.formatter);
        DayOfWeek dayWeek = dataParse.getDayOfWeek();

        if (dayWeek != DayOfWeek.FRIDAY) return false;

        LocalDate initGeralDate = LocalDate.of(2005, 1, 1);
        long diferencaEmDias = ChronoUnit.DAYS.between(initGeralDate, dataParse);

        return (diferencaEmDias + 1) % 14 == 0;
    }


    public static void validDuplicateID(String idSindicato) throws DuplicateException {//Método para averiguar se há Empregados duplicados no Sindicato
        for (Map.Entry<String, Empregado> entry : DBmanager.getEmpregados().entrySet()) {
            Sindicato m = entry.getValue().getSindicalizado();
            if (m != null)
                if (m.getIdMembro().equals(idSindicato))
                    throw new DuplicateException();
        }
    }

    public static void validSyndicate(Sindicato ans) throws SindicatoException {//Método para abalizar se um sindicato existe
        if(ans == null){
            throw new SindicatoException();
        }
    }

    public static void validAtributo(String atributo) throws AtributoException {//Método para avaliar se um atributo é valido
        for(String ans : Settings.TIPO_ATRIBUTO){
            if(ans.equals(atributo)) return;
        }throw new AtributoException();
    }

    public static void validAtributoSyndicate(String valor) throws Exception{//Método para avaliar se o atributo do sindicato é valido
        if(valor.equals("abc")){
            throw new SyndicateAtributeException();
        }
    }

    public static void validIDEmploy(String id) throws Exception { //método para avaliar se o ID é válido
        if(id.equals("")){
            throw new EmpregadoException("Identificacao do empregado nao pode ser nula.");
        }
    }

    public static void validEndereco(String endereco) throws Exception {//Metodo para avaliar se um endereco é valido

        if (endereco.isEmpty()) {
            throw new HomeNullException();
        }
    }

    public static void validNome(String nome) throws Exception {//Metodo para avaliar se um nome é valido

        if (nome.isEmpty()) {
            throw new NameNullException();
        }
    }

    public static void validMembroSindicalizado(Empregado e) throws Exception {//método para avaliar se um Empregado está sindicalizado
        Sindicato m = e.getSindicalizado();
        if (m == null) {
            throw new SindicatoException();
        }
    }

    public static void validComissionado(Empregado e) throws Exception {// método para avaliar se o Empregado é comissionado
        if (!e.getTipo().equals("comissionado"))
            throw new TypeEmpregadoException("comissionado");
    }

    public static void validEmployInfo(String nome, String endereco, String tipo, String salario) throws Exception {// método para avaliar se os atributos são validos
        if (nome.isEmpty())
            throw new NameNullException();

        if (endereco.isEmpty())
            throw new HomeNullException();

        if (tipo.equals("abc"))
            throw new InvalidTypeException();

        if (tipo.equals("comissionado"))
            throw new NonAplicableTypeException();

        validSalario(salario);
    }

    public static void validEmployInfo(String nome, String endereco, String tipo, String salario, String comissao) throws Exception {// Segundo método para avaliar se os atributos sao validos
        ;
        if (nome.isEmpty())
            throw new NameNullException();

        if (endereco.isEmpty())
            throw new HomeNullException();

        if (tipo.equals("abc"))
            throw new InvalidTypeException();

        if (!tipo.equals("comissionado"))
            throw new NonAplicableTypeException();
        validSalario(salario);
        validComissao(comissao,tipo);

    }


    public static void validEmploy(Empregado e) throws Exception {//Método para avaliar a existência de um empregado
        if(e == null){
            throw new EmpregadoException("Empregado nao existe.");
        }
    }

    public static void validSyndicateMembro(String ans) throws Exception {// metodo para avaliar se um Empregado faz parte de um sindicato
        String id = session.getEmpregadoPorIdSindical(ans);
        if(ans.equals("")){
            throw new EmpregadoException("Identificacao do membro nao pode ser nula.");
        }if(id == null){
            throw new EmpregadoException("Membro nao existe.");
        }
    }

    public static void validEmploySyndicate(String id) throws Exception {//Método para avaliar a existêcia de um empregado no sindicato
        String ans = session.getEmpregadoPorIdSindical(id);
        if(id.equals("")){
            throw new EmpregadoException("Membro nao existe.");
        }
    }

    public static void validCorrectData(LocalDate dataInicial, LocalDate dataFinal) throws Exception {// método para avaliar a existencia de incosistencia na data
        if (dataInicial.isAfter(dataFinal) ) {
            throw new CronoDataException("Data inicial nao pode ser posterior aa data final.");
        }
    }

    public static void validWayBanco(String banco, String agencia, String contaCorrente) throws Exception {//Método para avaliar se um método de pagamento é valido
        if(banco.isEmpty()) {
            throw new BancoNuloException();
        } else if (agencia.isEmpty()){
            throw new AgenciaNuloException();
        }else if(contaCorrente.isEmpty()){
            throw new ContaCorrenteNuloException();
        }
    }

    public static void validBankPaymentWay(MetodoPagamento m) throws Exception {// Método para avaliar se o método de pagamento é banco

        if (!m.getMetodoPagamento().equals("banco")){
            throw new NotGetBancoException();
        }
    }

    public static void validValue(double value) throws Exception {//Método para avaliar se um valor é válido
        if (value <= 0) {
            throw new EmpregadoException("Valor deve ser positivo.");
        }
    }

    public static void validAlterarTipo(Empregado e, String tipo) throws Exception {// método para saber se foi inseridop um tipo valido
        if (!(tipo.equals("comissionado") || tipo.equals("assalariado") || tipo.equals("horista"))) {
            throw new InvalidTypeException();
        }
    }

    public static void validSalario(String valor) throws Exception{//Método para avaliar se o salário é válido
        if (valor.isEmpty()) throw new SalarioException("Salario nao pode ser nulo.");
        try {
            double salario = Double.parseDouble(valor.replace(',', '.'));
            if (salario <= 0.0) throw new SalarioException("Salario deve ser nao-negativo.");

        } catch (NumberFormatException ex) {
            throw new SalarioException("Salario deve ser numerico.");
        }
    }

    public static void validAgenda(String valor, DBmanager session) throws Exception {// metodo para saber se a Agenda é valida
        for(Agenda e : session.getAgendas()){
            if(e.getDescricao().equals(valor)) return;
        }throw new ExceptionAgenda();

    }

    public static void validComissao(String valor, String tipo) throws Exception {//Método para avaliar se a comissão é válida
        if (valor.isEmpty()) throw new ComissaoException("Comissao nao pode ser nula.");
        try {
            double comissao = Double.parseDouble(valor.replace(',', '.'));
            if (comissao <= 0.0) throw new ComissaoException("Comissao deve ser nao-negativa.");
        } catch (NumberFormatException ex) {
            throw new ComissaoException("Comissao deve ser numerica.");
        }
        if (!(tipo.equals(Settings.COMISSIONADO)))
            throw new ComissaoException("Empregado nao eh comissionado.");
    }


    public static void validsyndicalWarm(String idSindicato, String taxaSindical) throws Exception { //Método para avaliar os atributos do sindicato
        if (idSindicato.isEmpty()) throw new AtributeSyndicateException("Identificacao do sindicato nao pode ser nula.");
        if (taxaSindical.isEmpty()) throw new AtributeSyndicateException("Taxa sindical nao pode ser nula.");

        try {
            double taxaSindicalNumber = Double.parseDouble(taxaSindical.replace(",", "."));

            if (taxaSindicalNumber <= 0.0) throw new AtributeSyndicateException("Taxa sindical deve ser nao-negativa.");

        } catch (NumberFormatException ex) {
            throw new AtributeSyndicateException("Taxa sindical deve ser numerica.");
        }
    }
}
