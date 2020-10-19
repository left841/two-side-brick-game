package client.model;

import client.view.IObserver;

import java.util.ArrayList;

class ModelClient implements IModelClient {
    ArrayList<IObserver> observers = new ArrayList<>();
    int room = -1;

    @Override
    public int getRoomNumber() {
        return room;
    }

    @Override
    public void addObserver(IObserver o) {
        observers.add(o);
    }

    @Override
    public void connect() {
        refresh();
    }

    void refresh() {
        for (IObserver observer : observers) {
            observer.refresh();
        }
    }
}
