package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.exceptions.Agenda.DescricaoException;
import br.ufal.ic.p2.wepayu.services.Settings;

import java.util.Arrays;

/**
 * Classe da {@link Agenda}, entidade que servirá para armazenar a Agenda de Pagamento
 * dos funcionários armazenados em {@link DBmanager}
 * @author teteuryan faite100
 */
public class Agenda {

    private String descricao;
    private String tipo;
    private int semana;
    private int dia;

    public Agenda(){//Construtor vazio para ser serializado para a Persistencia em XML

    }

    /**
     * Segundo construtor de {@link Agenda}, ao qual divide a descrição do pagamento em
     * tipo, semana e dia
     */
    public Agenda(String descricao) throws Exception {
        setDescricao(descricao);

        String[] split = descricao.split("\\s+");

        if(split.length >= 1){
            String tipo = split[0];
            if(!Arrays.asList(Settings.TIPO_AGENDAS).contains(tipo))
                throw new DescricaoException();
            setTipo(tipo);
        }

        if(split.length >= 3){//Se o split da descrição for 3, então será semanal
            int semana = Integer.parseInt(split[1]);
            if(semana > 52 || semana < 1)
                throw new DescricaoException();
            setSemana(semana);
        }

        if(split.length >= 3 || split.length == 2){//Se o split da descrição for 2, então será mensal
            if(split[split.length - 1].equals("$")){
                setDia(-1);
            }
            else {
                int dia = Integer.parseInt(split[split.length - 1]);
                if(split[0].equals("mensal")){
                    if(dia > 28 || dia < 1) throw new DescricaoException();//Validação de dias
                }
                else if(dia > 7 || dia < 1)
                    throw new DescricaoException();//Validação de dias
                setDia(dia);
            }
        }
    }

    /**
     * Terceiro construtor de {@link Agenda}, ao qual já separa as variáveis corretamentes
     * sem a necessidade de tratar a descrição de pagamento
     */
    public Agenda(String tipo, int semana, int dia){
        this.tipo =  tipo;
        this.semana = semana;
        this.dia = dia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getSemana() {
        return semana;
    }

    public void setSemana(int semana) {
        this.semana = semana;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    @Override
    public String toString() {
        return descricao;
    }

    /**
     * Método para a clonagem da classe {@link Agenda} por
     * meio do Cloneable, gerando uma mesma Agenda para diferentes
     * endereços de memória
     */
    @Override
    public Agenda clone() {
        try {
            return (Agenda) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
