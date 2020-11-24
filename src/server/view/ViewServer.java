package server.view;

import common.COMMAND;
import common.Instruction;

import javax.swing.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

class ViewServer implements IViewServer {
    Socket cs;

    public ViewServer(Socket s) {
        cs = s;
    }

    public Instruction getCommand() {
        Instruction instruction = new Instruction();
        try {
            ObjectInputStream ois = new ObjectInputStream(cs.getInputStream());
            instruction.recv(ois);
            System.out.println("Got: " + instruction.getInstruction());
        } catch (EOFException e) {
            return null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return instruction;
    }

    public void setCommand(Instruction instruction) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(cs.getOutputStream());
            instruction.send(oos);
            System.out.println("Sent: " + instruction.getInstruction());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
