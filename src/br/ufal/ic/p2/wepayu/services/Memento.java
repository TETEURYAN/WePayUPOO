package br.ufal.ic.p2.wepayu.services;

import br.ufal.ic.p2.wepayu.exceptions.Command.CannotComandoException;
import br.ufal.ic.p2.wepayu.exceptions.Command.ComandoDesfazerException;
import br.ufal.ic.p2.wepayu.exceptions.Command.ComandoFazerException;
import br.ufal.ic.p2.wepayu.models.Empregado;

import java.util.HashMap;
import java.util.Stack;

public class Memento {

    private static Stack<HashMap<String, Empregado>> undo;
    private  Stack<HashMap<String, Empregado>> redo;

    private static Memento backup = null;

    private  DBmanager session = null;

    private static boolean systemOn = true;

    private Memento(DBmanager session){
        this.session = session;
    }


    public void setsystemOff() {
        systemOn = false;
    }

    public void setsystemOnn() {
        systemOn = true;
    }

    public void deleteStacks(){
        this.redo = new Stack<>();
        this.undo = new Stack<>();
    }

    public static boolean getSystemOn () {
        return systemOn;
    }

    public static Memento getCommand(DBmanager session){
        if(backup == null){
            backup = new Memento(session);
        }
        backup.deleteStacks();
        return backup;
    }

//    public void pushUndo(){
//        undo.push(session.copyhash());
//    }
//
//
//    public HashMap<String, Empregado> getUndo() throws Exception {
//        if(undo.size() <= 1){
//            throw new ComandoDesfazerException();
//        }
//        else {
//            redo.push(undo.pop());
//            return undo.peek();
//        }
//    }
//
//    public HashMap<String, Empregado> getRedo() throws Exception {
//        if(!redo.isEmpty()){
//            undo.push(redo.pop());
//            return undo.peek();
//        }
//        else{
//            throw new ComandoFazerException();
//        }
//    }

    public void pushUndo() throws Exception {

        if (!systemOn) {
            throw new CannotComandoException();
        }

        HashMap<String, Empregado> novaHash = session.copyhash();

        if (undo == null)
            undo = new Stack<>();

        undo.push(novaHash);
    }

    public static void pushUndo(HashMap<String, Empregado> e) throws Exception {

        if (!systemOn) {
           throw new CannotComandoException();
        }

        if (undo == null)
            undo = new Stack<>();

        undo.push(e);
    }

    public HashMap<String, Empregado> popUndo() throws Exception {
        if (!systemOn) {
            throw new CannotComandoException();
        }

        if (undo.empty()) {
            throw new ComandoDesfazerException();
        }
//        System.out.println("Tamanho do undo: " + undo.size());

        HashMap<String, Empregado> e = undo.peek();

        if (undo.size() > 1)  pushRedo(e);

        undo.pop();

        return e;
    }

    public void pushRedo(HashMap<String, Empregado> e) throws Exception {
        if (!systemOn) {
            throw new ComandoFazerException();
        }

        if (redo == null) redo = new Stack<>();

        redo.push(e);
    }

    public HashMap<String, Empregado> popRedo() throws Exception {
        if (!systemOn) {
            throw new CannotComandoException();
        }

        if (redo.empty()) {
            throw new ComandoFazerException();
        }

        HashMap<String, Empregado> e = redo.peek();

        pushUndo(e);
        redo.pop();

        return e;
    }

}
