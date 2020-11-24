package server.presenter;

import common.Instruction;
import server.model.IModelServer;
import server.view.IViewServer;

class Presenter implements IPresenter {
    IViewServer vs;
    IModelServer ms;

    public Presenter(IModelServer m, IViewServer v) {
        ms = m;
        vs = v;
        ms.addPresenter(this);
    }

    public void start() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    Instruction instruction = vs.getCommand();
                    if (instruction == null) {
                        break;
                    }
                    processInstruction(instruction);
                }
            }
        }.start();
    }

    public void processInstruction(Instruction instruction) {
        System.out.println("Got instruction: " + instruction.getInstruction());
        ms.addCommand(vs, instruction);
    }

    @Override
    public void update(Instruction instruction) {
        vs.setCommand(instruction);
    }
}
