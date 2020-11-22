package server.model;

import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import common.*;

import static java.lang.Thread.sleep;

class ModelServer implements IModelServer {

    public class Session {

        public class Event {

        }

        private Queue<COMMAND> player1q;
        private Queue<COMMAND> player2q;
        private Queue<Event> event_queue;
        private Thread sessionThread;

        private void mainLoop() {
            GameField game = new GameField();
            byte frame_cycle_num = 0;
            boolean game_running = false;
            boolean player1_ready = false;
            boolean player2_ready = false;
            Instruction instruction;

            while (true) {
                if (!game_running) {
                    if (player1_ready && player2_ready) {
                        game_running = true;
                        // TODO add event
                    } else {
                        for (int i = 0; (i < 3) && !player1q.isEmpty(); ++i)
                        {
                            COMMAND cmd = player1q.poll();
                            if (cmd == COMMAND.CONNECT) {
                                player1_ready = true;
                            }
                        }
                        for (int i = 0; (i < 3) && !player2q.isEmpty(); ++i)
                        {
                            COMMAND cmd = player2q.poll();
                            if (cmd == COMMAND.CONNECT) {
                                player2_ready = true;
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
            player1q = new ConcurrentLinkedQueue<COMMAND>();
            player2q = new ConcurrentLinkedQueue<COMMAND>();
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

        public void addPlayer1Cmd(COMMAND cmd) {
            player1q.add(cmd);
        }

        public void addPlayer2Cmd(COMMAND cmd) {
            player2q.add(cmd);
        }
    }

    private Vector<Session> sessions;

    public ModelServer() {
        System.out.println("ModelServer");
        sessions = new Vector<Session>(1);
        sessions.setSize(1);
        sessions.set(0, new Session());
    }

}
