package server.presenter;

import common.Instruction;

public interface IPresenter {
    void update(Instruction instruction);
    void start();
    void processInstruction(Instruction instruction);
}
