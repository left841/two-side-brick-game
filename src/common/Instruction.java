package common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class Instruction {
    private Vector<Integer> arr;

    public Instruction() {
        arr = new Vector<Integer>();
    }

    public void send(ObjectOutputStream oos) throws IOException {
        oos.writeObject(arr);
    }

    public void recv(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        arr = (Vector<Integer>)ois.readObject();
    }

}
