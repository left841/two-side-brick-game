package server.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import common.*;

import static java.lang.Thread.sleep;

class ModelServer implements IModelServer {

    public class Session {

        private Queue<COMMAND> player1q;
        private Queue<COMMAND> player2q;

        private void mainLoop() {
            byte[][] gameField = new byte[30][30];
            byte frame_cycle_num = 0;

            while (true) {


                try {
                    sleep(200);
                }
                catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }

        }





        public Session() {
            player1q = new ConcurrentLinkedQueue<COMMAND>();
            player2q = new ConcurrentLinkedQueue<COMMAND>();

            Thread sessionThread = new Thread() {
                @Override
                public void run() {
                    mainLoop();
                }
            };
            sessionThread.start();
        }

        public void addPlayer1Cmd(COMMAND cmd) {
            player1q.add(cmd);
        }

        public void addPlayer2Cmd(COMMAND cmd) {
            player2q.add(cmd);
        }
    }

}
