package server.presenter;

import server.model.IModelServer;
import server.view.IViewServer;

class Presenter implements IPresenter {
    IViewServer vs;
    IModelServer ms;

    public Presenter(IModelServer m, IViewServer v) {
        ms = m;
        vs = v;
    }

    @Override
    public void update() {

    }
}
