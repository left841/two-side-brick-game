package client.model;

import client.view.IObserver;
import common.COMMAND;
import common.GameField;

import java.net.InetAddress;
import java.util.ArrayList;

public interface IModelClient {
    int getRoomNumber();
    void addObserver(IObserver o);
    void join();
    GameField getGameState();
    void sendInstruction(COMMAND command);

    // Connection
    void connect();
    InetAddress getIp();
    void setIp(String ip);
    int getPort();
    void setPort(String port);
    ArrayList<String> getLogQueue();
    void clearLogQueue();
}
