package br.ufal.ic.p2.wepayu.services;

import br.ufal.ic.p2.wepayu.exceptions.Memento.*;
import br.ufal.ic.p2.wepayu.models.Empregado;

import java.util.HashMap;
import java.util.Stack;

/**
 * Classe {@link Memento}, referente ao padrão de projeto Memento
 * para armazenar os estados do sistemas nas stacks Undo e Redo
 * @author teteuryan faite100
 */
public class Memento {

    private static Stack<HashMap<String, Empregado>> undo;//Pilha de Undo
    private  Stack<HashMap<String, Empregado>> redo;//Pilha de Redo

    private static Memento backup = null;//Atributo global de memento com Singleton

    private  DBmanager session = null;//Banco de dados

    private static boolean systemOn = true;//Atributo que diz respeito ao sistema estar ligado ou nao

    private Memento(DBmanager session){
        this.session = session;
    }//Construtor privado do memento

    public void setsystemOff() {
        systemOn = false;
    }

    public void setsystemOnn() {
        systemOn = true;
    }

    public void deleteStacks(){//método para reiniciar as pilhas de Undo e Redo
        this.redo = new Stack<>();
        this.undo = new Stack<>();
    }

    public static boolean getSystemOn () {
        return systemOn;
    }

    /**
     * Retorna uma instância única e global de Memento
     * @return instância de {@link Memento}
     */
    public static Memento getCommand(DBmanager session){
        if(backup == null){
            backup = new Memento(session);
        }
        backup.deleteStacks();
        return backup;
    }

    /**
     * Método que armazena na stack Undo de {@link Memento}
     */
    public void push() throws Exception {

        if (!systemOn) {
            throw new CannotMementoException();
        }

        HashMap<String, Empregado> novaHash = session.copyhash();

        if (undo == null)
            undo = new Stack<>();

        undo.push(novaHash);
    }

    /**
     * Método que dá push stack Undo de {@link Memento}
     */
    public static void pushUndo(HashMap<String, Empregado> e) throws Exception {

        if (!systemOn) {
           throw new CannotMementoException();
        }

        if (undo == null)
            undo = new Stack<>();

        undo.push(e);
    }

    /**
     * Método que dá pop stack Undo de {@link Memento}
     */
    public HashMap<String, Empregado> popUndo() throws Exception {
        if (systemOn) {
            if (undo.empty()) throw new MementoDesfazerException();

            HashMap<String, Empregado> e = undo.peek();
            if (undo.size() > 1)  pushRedo(e);//Se o pop não zerou, então o estado anterior vai para Redo
            undo.pop();

            return e;
        }throw new CannotMementoException();
    }

    /**
     * Método que dá push stack Redo de {@link Memento}
     */
    public void pushRedo(HashMap<String, Empregado> e) throws Exception {
        if (!systemOn) {
            throw new MementoFazerException();
        }

        if (redo == null) redo = new Stack<>();

        redo.push(e);
    }

    /**
     * Método que dá pop na stack Redo de {@link Memento}
     */
    public HashMap<String, Empregado> popRedo() throws Exception {
        if (!systemOn) {
            throw new CannotMementoException();
        }

        if (redo.empty()) {
            throw new MementoFazerException();
        }

        HashMap<String, Empregado> e = redo.peek();

        pushUndo(e);
        redo.pop();

        return e;
    }

}
