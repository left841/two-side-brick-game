package server;

import server.model.BModelServer;
import server.model.IModelServer;
import server.presenter.BPresenter;
import server.presenter.IPresenter;
import server.view.BViewServer;
import server.view.IViewServer;

import java.io.IOException;
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
                System.out.println("Has connect");
                IViewServer v = BViewServer.build(cs);
                IPresenter p = BPresenter.build(m, v);
                p.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().start();
    }
}
