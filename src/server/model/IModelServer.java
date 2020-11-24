package server.model;

import common.Instruction;
import server.presenter.IPresenter;
import server.view.IViewServer;

public interface IModelServer {
    void addCommand(IViewServer v, Instruction instruction);
    void addPresenter(IPresenter p);
}
