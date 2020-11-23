package client.model;

import client.view.IObserver;
import common.Instruction;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

class ModelClient implements IModelClient {
    InetAddress ip = null;
    int port = 3124;
    Thread connection = null;
    Socket cs;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    Instruction instruction = new Instruction();

    ArrayList<IObserver> observers = new ArrayList<>();
    int room = -1;

    ModelClient() {
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
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

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(String ipStr) {
        try {
            ip = InetAddress.getByName(ipStr);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(String portStr) {
        port = Integer.parseInt(portStr);
    }

    public void connect() {
        if(cs != null) {
            connection.stop();
            cs = null;
        }
        try {
            cs = new Socket(ip, port);
            oos = new ObjectOutputStream(cs.getOutputStream());
            System.out.println("Client start");
            instruction.addConnect();
            instruction.send(oos);

            connection = new Thread() {
                @Override
                public void run() {
                    try {
                        System.out.println("Start");
                        ois = new ObjectInputStream(cs.getInputStream());
                        while (true) {
                            instruction.recv(ois);
                            System.out.println(instruction.getInstruction());
                        }
                    } catch (EOFException e) {
                        System.out.println("Disconnected!");
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            };
            connection.start();
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
