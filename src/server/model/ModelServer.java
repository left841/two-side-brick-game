package server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import common.*;
import server.presenter.IPresenter;
import server.view.IViewServer;

import static java.lang.Thread.sleep;

class ModelServer implements IModelServer {



    public class Session {

        public class Event {

        }

        private Queue<Instruction> player1q;
        private Queue<Instruction> player2q;
        private Queue<Event> event_queue;
        private Thread sessionThread;
        private IViewServer player1;
        private IViewServer player2;
        boolean game_running = false;
        boolean player1_ready = false;
        boolean player2_ready = false;

        private void mainLoop() {
            GameField game = new GameField();
            byte frame_cycle_num = 0;
            Instruction instruction = new Instruction();

            while (true) {
                if (!game_running) {
                    if (player1_ready && player2_ready) {
                        System.out.println("Starting the game!");
                        game_running = true;
                        instruction.clear();
                        instruction.addStart();
                        sendInstruction(instruction);
                    } else {
                        for (int i = 0; (i < 3) && !player1q.isEmpty(); ++i)
                        {
                            instruction = player1q.poll();
                            COMMAND cmd = COMMAND.values()[instruction.getInstruction().get(0)];
                            if (cmd == COMMAND.CONNECT) {
                                System.out.println("Player 1 is ready!");
                                player1_ready = true;
                            }
                        }
                        for (int i = 0; (i < 3) && !player2q.isEmpty(); ++i)
                        {
                            instruction = player2q.poll();
                            COMMAND cmd = COMMAND.values()[instruction.getInstruction().get(0)];
                            if (cmd == COMMAND.CONNECT) {
                                player2_ready = true;
                                System.out.println("Player 2 is ready!");
                            }
                        }
                    }
                } else {
                    // TODO
                }

                try {
                    sleep(200);
                    System.out.println("iteration!!!");
                }
                catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        public Session() {
            player1q = new ConcurrentLinkedQueue<Instruction>();
            player2q = new ConcurrentLinkedQueue<Instruction>();
            event_queue = new ConcurrentLinkedQueue<Event>();

            sessionThread = new Thread() {
                @Override
                public void run() {
                    mainLoop();
                }
            };
            sessionThread.start();
        }

        public void restart() {
            try {
                sessionThread.join();
            }
            catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            sessionThread = new Thread() {
                @Override
                public void run() {
                    mainLoop();
                }
            };
        }

        public void addPlayer1Cmd(Instruction cmd) {
            player1q.add(cmd);
        }

        public void addPlayer2Cmd(Instruction cmd) {
            player2q.add(cmd);
        }

        public boolean isPlayer1Ready() {
            return player1_ready;
        }

        public boolean isPlayer2Ready() {
            return player2_ready;
        }
    }

    private ArrayList<IPresenter> presenters = new ArrayList<>();
    private Vector<Session> sessions;
    private HashMap<IViewServer, Integer> players = new HashMap<>();
    private int playerId = 0;

    public void addPresenter(IPresenter p) {
        presenters.add(p);
    }

    private void checkPlayer(IViewServer player) {
        int size = players.size();
        players.putIfAbsent(player, playerId);
        if (size != players.size()) {
            System.out.println("Put player. id: " + playerId);
            playerId += 1;
        }
    }

    public void addCommand(IViewServer v, Instruction instruction) {
        checkPlayer(v);
        int id = players.get(v);
        if (!sessions.get(id / 2).isPlayer1Ready()) {
            sessions.get(id / 2).addPlayer1Cmd(instruction);
        } else {
            sessions.get(id / 2).addPlayer2Cmd(instruction);
        }
    }

    public ModelServer() {
        System.out.println("ModelServer");
        sessions = new Vector<Session>(1);
        sessions.setSize(1);
        sessions.set(0, new Session());
    }

    void sendInstruction(Instruction instruction) {
        for (IPresenter p : presenters) {
            p.update(instruction);
        }
    }
}
