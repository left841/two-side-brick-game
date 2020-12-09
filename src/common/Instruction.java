package common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Instruction {
    private ArrayList<Integer> arr;

    public Instruction() {
        arr = new ArrayList<Integer>(0);
    }

    public Instruction(Instruction instruction) {
        arr = new ArrayList<>(instruction.arr);
    }

    public void send(ObjectOutputStream oos) throws IOException {
        oos.writeObject(arr);
    }

    public void recv(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        System.out.println("Start reading");
        arr = (ArrayList<Integer>)ois.readObject();
        System.out.println("Finish reading" + arr);
    }

    public ArrayList<Integer> getInstruction() {
        return arr;
    }

    public void clear() {
        arr.clear();
    }

    public void addStart() {
        arr.add(EVENT.START.getValue());
    }

    // EVENTS
    public void addSpawn(int player, int x, int y, int rot, TETROMINO_NAME type) {
        arr.add(EVENT.SPAWN.getValue());
        arr.add(player);
        arr.add(x);
        arr.add(y);
        arr.add(rot);
        arr.add(type.getValue());
    }

    public void addMove(int player, int x, int y, int rot) {
        arr.add(EVENT.MOVE.getValue());
        arr.add(player);
        arr.add(x);
        arr.add(y);
        arr.add(rot);
    }

    public void addMerge(int player, int x, int y, int rot, TETROMINO_NAME type) {
        arr.add(EVENT.MERGE.getValue());
        arr.add(player);
        arr.add(x);
        arr.add(y);
        arr.add(rot);
        arr.add(type.getValue());
    }

    public void addEnd(int winner) {
        arr.add(EVENT.END.getValue());
        arr.add(winner);
    }

    // COMMANDS
    public void addConnect() {
        arr.add(COMMAND.CONNECT.getValue());
    }

    public void addDisconnect() {
        arr.add(COMMAND.DISCONNECT.getValue());
    }

    public void addReady() {
        arr.add(COMMAND.READY.getValue());
    }

    public void addLeft() {
        arr.add(COMMAND.LEFT.getValue());
    }

    public void addUp() {
        arr.add(COMMAND.UP.getValue());
    }

    public void addRight() {
        arr.add(COMMAND.RIGHT.getValue());
    }

    public void addDown() {
        arr.add(COMMAND.DOWN.getValue());
    }

}
