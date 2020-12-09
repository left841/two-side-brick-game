package server.view;

import common.Instruction;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ViewServer implements IViewServer {
    Socket cs;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public ViewServer(Socket s) {
        cs = s;
    }

    public Instruction getCommand() {
        Instruction instruction = new Instruction();
        try {
            if (ois == null) {
                ois = new ObjectInputStream(cs.getInputStream());
            }
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
            if (oos == null) {
                oos = new ObjectOutputStream(cs.getOutputStream());
            }
            Instruction instructionToSend = new Instruction(instruction);
            instructionToSend.send(oos);
            System.out.println("Sent: " + instructionToSend.getInstruction());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
