package client.model;

import client.view.IObserver;
import common.GameField;

import java.net.InetAddress;

public interface IModelClient {
    int getRoomNumber();
    void addObserver(IObserver o);
    void join();
    GameField getGameState();

    // Connection
    void connect();
    InetAddress getIp();
    void setIp(String ip);
    int getPort();
    void setPort(String port);
}
