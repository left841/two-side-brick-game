package client.model;

import client.view.IObserver;

public interface IModelClient {
    int getRoomNumber();
    void addObserver(IObserver o);
    void connect();
}
