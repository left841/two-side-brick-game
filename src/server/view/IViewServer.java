package server.view;

import common.Instruction;

public interface IViewServer {
    Instruction getCommand();
    void setCommand(Instruction instruction);
}
