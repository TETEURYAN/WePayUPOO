package br.ufal.ic.p2.wepayu.services;
import br.ufal.ic.p2.wepayu.models.*;
import br.ufal.ic.p2.wepayu.services.FactoryEmpregados.FactoryEmpregados;
import br.ufal.ic.p2.wepayu.models.TiposCartao.CartaoDePonto;
import br.ufal.ic.p2.wepayu.models.TiposCartao.CartaoDeVenda;
import br.ufal.ic.p2.wepayu.models.TiposCartao.CartaoDeServico;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.TiposEmpregados.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.TiposPagamento.Banco;
import br.ufal.ic.p2.wepayu.models.TiposPagamento.Correios;
import br.ufal.ic.p2.wepayu.models.TiposPagamento.EmMaos;
import br.ufal.ic.p2.wepayu.services.XMLStrategy.XMLAgenda;
import br.ufal.ic.p2.wepayu.services.XMLStrategy.XMLEmpregado;
import br.ufal.ic.p2.wepayu.utils.Utils;
import br.ufal.ic.p2.wepayu.utils.Validate;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe para manipulação de dados da base de dados de instancia única {@link DBmanager}
 * @author teteuryan faite100
 */
public class DBmanager {

    private static DBmanager session;//Banco de dados
    public static HashMap<String, Empregado> empregados;// HashMap de Empregados
    private static FactoryEmpregados fabrica;//Fábrica de Emppregados
    public static ArrayList<Agenda> agendas;//Agenda de pagamento dos Empregados
    public static int key;

    /**
     * Construtor privado de {@link DBmanager}, carregandoo a persistencia
     * Criando iuma nova fábrica de Empregados e carregando a peristencia da
     * Agenda de pagamento
     */
    private DBmanager()  {
        this.empregados = carregarEmpregadosDeXML();
        this.fabrica = new FactoryEmpregados();
        this.agendas = readAgendas();
    }

    /**
     * Retorna a instancia única de {@link DBmanager}, aplicando o Singleton
     */
    public static DBmanager getDatabase() {
        if(session == null)
        {
            session = new DBmanager();
        }
        return session;
    }

    public static HashMap<String, Empregado> getEmpregados() {
        return empregados;
    }

    public void setEmpregados(HashMap<String, Empregado> empregados) {
        DBmanager.empregados = empregados;
    }

    public static FactoryEmpregados getFabrica(){
        return fabrica;
    }

    public ArrayList<Agenda> getAgendas() {
        return agendas;
    }

    public static String add(Empregado e) {//Adiciona novos Empregados a HashMap de empregados
        key++;
        String id = Integer.toString(key);
        e.setID(id);
        empregados.put(id, e);
        return id;
    }

    public void addAgenda(Agenda agenda){
        agendas.add(agenda);
    }

    public static Empregado getEmpregado(String key) throws Exception {//Retorna um empregado da hash a partir da chave
        Empregado e = empregados.get(key);
        Validate.validIDEmploy(key);
        Validate.validEmploy(e);
        return e;
    }

    public static HashMap<String, EmpregadoHorista> getEmpregadoHoristas() {//Metodo para puxar todos os Empretados Horistas da HashMap

        HashMap <String, EmpregadoHorista> empregadoHoristas = new HashMap<String,EmpregadoHorista>();

        for (Map.Entry<String, Empregado> e: empregados.entrySet()) {

            Empregado empregado = e.getValue();

            if (empregado.getTipo().equals("horista")) {
                empregadoHoristas.put(e.getKey(), (EmpregadoHorista) empregado);
            }
        }
        return empregadoHoristas;
    }

    public static HashMap<String, EmpregadoComissionado> getEmpregadoComissionado() {//Metodo para puxar todos os EMpregados Comissionados da Hash

        HashMap <String, EmpregadoComissionado> empregadoHoristas = new HashMap<String,EmpregadoComissionado>();

        for (Map.Entry<String, Empregado> e: empregados.entrySet()) {

            Empregado empregado = e.getValue();

            if (empregado.getTipo().equals("comissionado")) {
                empregadoHoristas.put(e.getKey(), (EmpregadoComissionado) empregado);
            }
        }

        return empregadoHoristas;
    }

    public static HashMap<String, EmpregadoAssalariado> getEmpregadoAssalariado() {// metodo para puxar todos so Empregados Assalariados da Hash

        HashMap <String, EmpregadoAssalariado> empregadoHoristas = new HashMap<String,EmpregadoAssalariado>();

        for (Map.Entry<String, Empregado> e: empregados.entrySet()) {

            Empregado empregado = e.getValue();

            if (empregado.getTipo().equals("assalariado")) {
                empregadoHoristas.put(e.getKey(), (EmpregadoAssalariado) empregado);
            }
        }

        return empregadoHoristas;
    }

    public static String getEmpregadoPorIdSindical (String idSindical) {// metodo para puxar um empredao a partir do ID no Sincidato

        for (Map.Entry<String, Empregado> entry : empregados.entrySet()) {
            Empregado e = entry.getValue();

            Sindicato sindicalizado = e.getSindicalizado();

            if (sindicalizado != null) {
                if (idSindical.equals(sindicalizado.getIdMembro()))
                    return entry.getKey();
            }
        }

        return null;
    }

    public static void setValue(String key, Empregado e) { empregados.replace(key, e); }

    public void delete(String emp) throws Exception {//Remover um usuário da hash
        Empregado e = getEmpregado(emp);
        empregados.remove(emp);
    }

    public static HashMap<String, Empregado>  carregarEmpregadosDeXML() {//Método para carregar a persistencia
        HashMap<String, Empregado> empregados = new HashMap<>();

        try(BufferedInputStream file = new BufferedInputStream(new FileInputStream(Settings.PATH_PERSISTENCIA + ".xml"))){
            XMLDecoder decoder = new XMLDecoder(file);
            while(true){
                try{
                    String id = (String) decoder.readObject();
                    Empregado aux = (Empregado) decoder.readObject();
                    empregados.put(id, aux);
                }catch (Exception e) {
                    break;
                }
            }
            decoder.close();
        }catch (IOException e) {
            System.out.println("Arquivo nao encontrado");
        }
        return empregados;
    }


    private ArrayList<Agenda> readAgendas() {//Metodo para ler a persistencia da Agenda de Pagamentos
        File file = new File(Settings.PATH_AGENDA + ".xml");

        if (file.exists()) {
            try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(Settings.PATH_AGENDA + ".xml")))) {
                // Ler o arquivo XML e criar um objeto Java
                ArrayList<Agenda> folha = (ArrayList<Agenda>) decoder.readObject();

                return folha;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void initSystem () {
        empregados = new HashMap<>();
        key = 0;
    }
    public static void deleteFilesXML () {

        for (int i = 1; i <= 1000; i++) {
            File file = new File( i + ".xml" );

            if (file.exists()) {
                file.delete();
            } else {
                return;
            }
        }
    }

    public static void deleteFolhas () {
        File[] folhas = new File("./").listFiles();

        for (File f : folhas) {
            if (f.getName().endsWith(".txt")) {
                f.delete();
            }
        }
    }

    public void deleteSystem() throws Exception {//Metodo que deleta e zera a o sistema
        initSystem();
        deleteFilesXML();
        deleteFolhas();
        agendas = Utils.getPadraoAgenda();
    }

    public void finishSystem(){//Metodo que encerra o sistema, salvando na persistencia e desligando o Memento
        XMLEmpregado xml = new XMLEmpregado();
        XMLAgenda fileAgenda = new XMLAgenda();
        fileAgenda.save(agendas);
        xml.save(DBmanager.empregados);
    }

    public String getSize(){
        int tam = empregados.size();
        return Integer.toString(tam);
    }

    private Sindicato copySindicato(Sindicato origin) {// metodo para copiar o sindicato

        Sindicato copy = null;

        if (origin != null) {
            copy = new Sindicato();
            copy.setIdMembro(origin.getIdMembro());
            copy.setTaxaSindical(origin.getTaxaSindical());

            ArrayList<CartaoDeServico> copyArrayCartaoDeServico = new ArrayList<>();

            ArrayList<CartaoDeServico> originArrayCartaoDeServico = origin.getTaxaServicos();

            for (CartaoDeServico t : originArrayCartaoDeServico) {
                CartaoDeServico copyCartaoDeServico = new CartaoDeServico();

                copyCartaoDeServico.setData(t.getData());
                copyCartaoDeServico.setValor(t.getValor());

                copyArrayCartaoDeServico.add(copyCartaoDeServico);
            }

            copy.setTaxaServicos(copyArrayCartaoDeServico);
        }

        return copy;
    }

    private MetodoPagamento copyMetodoPagamento(MetodoPagamento origin) {//Metodo para copiar o metodo de pagamento

        if (origin.getMetodoPagamento().equals("emMaos"))
            return new EmMaos();

        if (origin.getMetodoPagamento().equals("correios"))
            return new Correios();

        if (origin.getMetodoPagamento().equals("banco")) {
            Banco copy = new Banco();

            copy.setBanco(((Banco) origin).getBanco());
            copy.setAgencia(((Banco) origin).getAgencia());
            copy.setContaCorrente(((Banco) origin).getContaCorrente());

            return copy;
        }

        return null;
    }

    private ArrayList<CartaoDeVenda> copyArrayCardSale (ArrayList<CartaoDeVenda> origin) {//Metodo para copiar o cartão e venda
        ArrayList<CartaoDeVenda> copy = new ArrayList<>();

        for (CartaoDeVenda c : origin) {
            CartaoDeVenda copyCartaoDeVenda = new CartaoDeVenda();

            copyCartaoDeVenda.setData(c.getData());
            copyCartaoDeVenda.setHoras(c.getHoras());

            copy.add(copyCartaoDeVenda);
        }

        return  copy;
    }

    private ArrayList<CartaoDePonto> copyArrayCardPoint (ArrayList<CartaoDePonto> origin) {//Metodo para copiar o cartão de ponton
        ArrayList<CartaoDePonto> copy = new ArrayList<>();

        for (CartaoDePonto c : origin) {
            CartaoDePonto copyCartaoDePonto = new CartaoDePonto();

            copyCartaoDePonto.setData(c.getData());
            copyCartaoDePonto.setHoras(c.getHoras());

            copy.add(copyCartaoDePonto);
        }

        return  copy;
    }

    private Empregado setAtributosEmpregado(Empregado copy, Empregado origin) {
        copy.setNome(origin.getNome());
        copy.setEndereco(origin.getEndereco());
        copy.setSalario(origin.getSalario());

        Sindicato mCopy = copySindicato(origin.getSindicalizado());

        copy.setSindicalizado(mCopy);

        MetodoPagamento pCopy = copyMetodoPagamento(origin.getMetodoPagamento());

        copy.setMetodoPagamento(pCopy);

        return copy;
    }

    public HashMap<String, Empregado> copyhash() {//metodo para copiar a hash de Empregados

        if (empregados == null)
            return null;

        HashMap<String, Empregado> hash = new HashMap<String, Empregado>();

        for (Map.Entry<String, Empregado> entry : empregados.entrySet()) {
            String id = entry.getKey();

            if (entry.getValue().getTipo().equals("comissionado")) {
                EmpregadoComissionado origin = (EmpregadoComissionado) entry.getValue();
                EmpregadoComissionado copy = new EmpregadoComissionado();

                copy = (EmpregadoComissionado) setAtributosEmpregado(copy, origin);

                //Comissionado
                copy.setTaxaDeComissao(origin.getTaxaDeComissao());

                ArrayList<CartaoDeVenda> cCopy = copyArrayCardSale(origin.getVendas());
                copy.setVendas(cCopy);

                hash.put(id, copy);
            }

            if (entry.getValue().getTipo().equals("assalariado")) {
                EmpregadoAssalariado origin = (EmpregadoAssalariado) entry.getValue();
                EmpregadoAssalariado copy = new EmpregadoAssalariado();

                copy = (EmpregadoAssalariado) setAtributosEmpregado(copy, origin);

                hash.put(id, copy);
            }

            if (entry.getValue().getTipo().equals("horista")) {
                EmpregadoHorista origin = (EmpregadoHorista) entry.getValue();
                EmpregadoHorista copy = new EmpregadoHorista();

                copy = (EmpregadoHorista) setAtributosEmpregado(copy, origin);

                //Horista

                ArrayList<CartaoDePonto> cCopy = copyArrayCardPoint(origin.getCartao());
                copy.setCartao(cCopy);

                hash.put(id, copy);
            }

        }

        return hash;
    }

}


