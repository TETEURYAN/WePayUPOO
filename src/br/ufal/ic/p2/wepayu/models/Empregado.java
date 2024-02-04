package br.ufal.ic.p2.wepayu.models;
import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.Utils.Validate;
import br.ufal.ic.p2.wepayu.models.Types.EmpregadoComissionado;

public class Empregado {
    private String nome;
    private String endereco;
    private String tipo;
    private String salario;


    private boolean sind;

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

    public String getTipo() {
        return tipo;
    }

    public  String getSalario() {
        return salario;
    }
    public boolean getSind(){return sind;}


}
