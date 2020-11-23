package server;

import common.Instruction;
import server.model.BModelServer;
import server.model.IModelServer;
import server.presenter.BPresenter;
import server.presenter.IPresenter;
import server.view.BViewServer;
import server.view.IViewServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
    private int port = 3124;
    private InetAddress ip = null;

    public void start() {
        IModelServer m = BModelServer.model();

        ServerSocket ss;
        Socket cs;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            ss = new ServerSocket(port, 0, ip);
            System.out.println("Server start");
            while (true)
            {
                cs = ss.accept();
                ObjectInputStream ois = new ObjectInputStream(cs.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(cs.getOutputStream());
                System.out.println("Has connect");
                IViewServer v= BViewServer.build(cs);
                IPresenter p = BPresenter.build(m, v);
                Instruction instruction = new Instruction();
                instruction.recv(ois);
                instruction.clear();
                instruction.addConnect();
                instruction.send(oos);
                System.out.println(instruction.getInstruction());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().start();
    }
}
