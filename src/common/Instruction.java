package common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class Instruction {
    private Vector<Integer> arr;

    public Instruction() {
        arr = new Vector<Integer>(0);
    }

    public void send(ObjectOutputStream oos) throws IOException {
        oos.writeObject(arr);
    }

    public void recv(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        arr = (Vector<Integer>)ois.readObject();
    }

    public Vector<Integer> getInstruction() {
        return arr;
    }

    public void clear() {
        arr.clear();
    }

    public void addStart() {
        arr.addElement(EVENT.START.getValue());
    }

    // EVENTS
    public void addSpawn(int player, int x, int y, int rot, TETROMINO_NAME type) {
        arr.addElement(EVENT.SPAWN.getValue());
        arr.addElement(player);
        arr.addElement(x);
        arr.addElement(y);
        arr.addElement(rot);
        arr.addElement(type.getValue());
    }

    public void addMove(int player, int x, int y, int rot) {
        arr.addElement(EVENT.MOVE.getValue());
        arr.addElement(player);
        arr.addElement(x);
        arr.addElement(y);
        arr.addElement(rot);
    }

    public void addMerge(int player, int x, int y, int rot, TETROMINO_NAME type) {
        arr.addElement(EVENT.MERGE.getValue());
        arr.addElement(player);
        arr.addElement(x);
        arr.addElement(y);
        arr.addElement(rot);
        arr.addElement(type.getValue());
    }

    public void addEnd(int winner) {
        arr.addElement(EVENT.END.getValue());
        arr.addElement(winner);
    }

    // COMMANDS
    public void addConnect() {
        arr.addElement(COMMAND.CONNECT.getValue());
    }

    public void addDisconnect() {
        arr.addElement(COMMAND.DISCONNECT.getValue());
    }

    public void addReady() {
        arr.addElement(COMMAND.READY.getValue());
    }

    public void addLeft() {
        arr.addElement(COMMAND.LEFT.getValue());
    }

    public void addUp() {
        arr.addElement(COMMAND.UP.getValue());
    }

    public void addRight() {
        arr.addElement(COMMAND.RIGHT.getValue());
    }

    public void addDown() {
        arr.addElement(COMMAND.DOWN.getValue());
    }

}
