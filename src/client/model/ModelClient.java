package client.model;

import client.view.IObserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

class ModelClient implements IModelClient {
    InetAddress ip = null;
    int port = 3124;
    Socket cs;
    DataInputStream dis;
    DataOutputStream dos;

    ArrayList<IObserver> observers = new ArrayList<>();
    int room = -1;

    ModelClient() {
        connect();
    }

    @Override
    public int getRoomNumber() {
        return room;
    }

    @Override
    public void addObserver(IObserver o) {
        observers.add(o);
    }

    @Override
    public void join() {
        refresh();
    }

    public void connect() {
        if(cs != null) {
            return;
        }
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            cs = new Socket(ip, port);
            dis = new DataInputStream(cs.getInputStream());
            dos = new DataOutputStream(cs.getOutputStream());
            System.out.println("Client start");

            new Thread(){
                @Override
                public void run() {
                    try {
                        while (true) {
                            int operation = dis.readInt();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } catch (IOException e) {
            System.out.println("Connection failed!");
        }
        refresh();
    }

    void refresh() {
        for (IObserver observer : observers) {
            observer.refresh();
        }
    }
}
