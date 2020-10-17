package server.presenter;

import server.model.IModelServer;
import server.view.IViewServer;

public class BPresenter {
    static public IPresenter build(IModelServer m, IViewServer v) {
        return new Presenter(m, v);
    }
}
