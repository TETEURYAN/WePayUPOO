package br.ufal.ic.p2.wepayu.models;
import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.Utils.Utils;
import br.ufal.ic.p2.wepayu.Utils.Validate;
import br.ufal.ic.p2.wepayu.models.ServiceCard;
import br.ufal.ic.p2.wepayu.models.Services.Syndicate;
import br.ufal.ic.p2.wepayu.models.TypesEmpregados.EmpregadoComissionado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public abstract class Empregado {
    private String nome;
    private String endereco;
    private String tipo;
    private String salario;

    private static String sindicateID;

    private String additionalSindicate;

    private boolean sind;
    private static Syndicate sindicato = null;


    public Empregado(String nome, String endereco, String tipo, String salario, boolean sind) throws Exception {
        if(Validate.isNull(nome)){
            throw new NomeNaoExisteException("Nome nao pode ser nulo.");
        }else if (Validate.isNull(endereco)){
            throw new EnderecoNaoExisteException("Endereco nao pode ser nulo.");
        }else if(Validate.isNotType(tipo)){
            throw new TipoInvalidoException("Tipo invalido.");
        }else if (Validate.isNull(salario)){
            throw new SalarioException("Salario nao pode ser nulo.");
        }else if (Validate.isNegative(salario)){
            throw new SalarioException("Salario deve ser nao-negativo.");
        }else if (Validate.isNotSalary(salario)){
            throw new SalarioException("Salario deve ser numerico.");
        }

        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.sind = sind;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public abstract String getTipo();

    public abstract  String getSalario();

    public boolean getSind(){return sind;}

    public void setSind(boolean value){
        this.sind = value;
    }

    public void setSindicateID(String id){
        this.sindicateID = id;
    }

    public void setAdditionalSindicate(String value){
        this.additionalSindicate = value;
    }

    public static String getSindicateID(){
        return sindicateID;
    }

    public static Syndicate getSindicato(){
        return sindicato;
    }

    public void setSindicato(String id, String value){
        this.sindicato = new Syndicate(id, Utils.toFloat(value));
    }

}
