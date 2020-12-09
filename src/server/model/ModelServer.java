package server.model;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import common.*;
import common.tetrominos.*;
import server.presenter.IPresenter;
import server.view.IViewServer;

import static java.lang.Thread.sleep;

class ModelServer implements IModelServer {



    public class Session {

        private Queue<Instruction> player1q;
        private Queue<Instruction> player2q;
        private Thread sessionThread;
        private IViewServer player1;
        private IViewServer player2;
        boolean game_running = false;
        boolean player1_ready = false;
        boolean player2_ready = false;

        private void mainLoop() {
            GameField game = new GameField();
            int frame_cycle_num = 0;
            Instruction out_instruction = new Instruction();
            boolean player1spawned = false, player2spawned = false;
            Random rand = new Random();

            while (true) {
                if (!game_running) {
                    if (player1_ready && player2_ready) {
                        System.out.println("Starting the game!");
                        game_running = true;
                        out_instruction.clear();
                        out_instruction.addStart();
                        sendInstruction(out_instruction);
                        out_instruction.clear();
                        game.init();
                    } else {
                        while (!player1q.isEmpty())
                        {
                            Instruction instr = player1q.poll();
                            COMMAND cmd = COMMAND.values()[instr.getInstruction().get(0)];
                            if (cmd == COMMAND.CONNECT) {
                                System.out.println("Player 1 is ready!");
                                player1_ready = true;
                            }
                        }
                        while (!player2q.isEmpty())
                        {
                            Instruction instr = player2q.poll();
                            COMMAND cmd = COMMAND.values()[instr.getInstruction().get(0)];
                            if (cmd == COMMAND.CONNECT) {
                                player2_ready = true;
                                System.out.println("Player 2 is ready!");
                            }
                        }
                    }
                } else {
                    if (!player1spawned)
                    {
                        TETROMINO_NAME tet = generate_tetromino(Math.abs(rand.nextInt()) % 7);
                        game.setPlayer1(tet);
                        int ret = game.spawnPlayer1();
                        out_instruction.addSpawn(1, game.getPlayer1x(), game.getPlayer1y(), game.getPlayer1rot(), tet);
                        sendInstruction(out_instruction);
                        out_instruction.clear();
                        if (ret != 0)
                        {
                            out_instruction.addEnd(2);
                            sendInstruction(out_instruction);
                            out_instruction.clear();
                            game_running = false;
                            System.out.println("Player 2 win!");
                        }
                        player1spawned = true;
                    }
                    if (!player2spawned)
                    {
                        TETROMINO_NAME tet = generate_tetromino(Math.abs(rand.nextInt()) % 7);
                        game.setPlayer2(tet);
                        int ret = game.spawnPlayer2();
                        out_instruction.addSpawn(2, game.getPlayer2x(), game.getPlayer2y(), game.getPlayer2rot(), tet);
                        sendInstruction(out_instruction);
                        out_instruction.clear();
                        if (ret != 0)
                        {
                            out_instruction.addEnd(1);
                            sendInstruction(out_instruction);
                            out_instruction.clear();
                            game_running = false;
                            System.out.println("Player 1 win!");
                        }
                        player2spawned = true;
                    }
                    if (game_running) {

                        boolean connection = true;
                        while (!player1q.isEmpty() && connection)
                        {
                            Instruction inst = player1q.poll();
                            COMMAND cmd = COMMAND.values()[inst.getInstruction().get(0)];
                            switch (cmd)
                            {
                                case DISCONNECT:
                                    out_instruction.addEnd(2);
                                    sendInstruction(out_instruction);
                                    out_instruction.clear();
                                    game_running = false;
                                    System.out.println("Player 2 win!");
                                    player1q.clear();
                                    player1_ready = false;
                                    connection = false;
                                    break;
                                case UP:
                                    if (game.rotatePlayer1() == 0) {
                                        out_instruction.addMove(1, game.getPlayer1x(), game.getPlayer1y(), game.getPlayer1rot());
                                        sendInstruction(out_instruction);
                                        out_instruction.clear();
                                    }
                                    break;
                                case DOWN:
                                    if (game.shiftPlayer1Down() == 0) {
                                        out_instruction.addMove(1, game.getPlayer1x(), game.getPlayer1y(), game.getPlayer1rot());
                                        sendInstruction(out_instruction);
                                        out_instruction.clear();
                                    }
                                    break;
                                case LEFT:
                                    if (game.shiftPlayer1Left() == 0) {
                                        out_instruction.addMove(1, game.getPlayer1x(), game.getPlayer1y(), game.getPlayer1rot());
                                        sendInstruction(out_instruction);
                                        out_instruction.clear();
                                    }
                                    break;
                                case RIGHT:
                                    if (game.shiftPlayer1Right() == 0) {
                                        out_instruction.addMove(1, game.getPlayer1x(), game.getPlayer1y(), game.getPlayer1rot());
                                        sendInstruction(out_instruction);
                                        out_instruction.clear();
                                    }
                                    break;
                                default:
                                    throw new RuntimeException("Invalid Command player 1");
                            }
                        }
                        while (!player2q.isEmpty() && connection)
                        {
                            Instruction inst = player2q.poll();
                            COMMAND cmd = COMMAND.values()[inst.getInstruction().get(0)];
                            switch (cmd)
                            {
                                case DISCONNECT:
                                    out_instruction.addEnd(1);
                                    sendInstruction(out_instruction);
                                    out_instruction.clear();
                                    game_running = false;
                                    System.out.println("Player 1 win!");
                                    player2q.clear();
                                    player2_ready = false;
                                    connection = false;
                                    break;
                                case UP:
                                    if (game.rotatePlayer2() == 0) {
                                        out_instruction.addMove(2, game.getPlayer2x(), game.getPlayer2y(), game.getPlayer2rot());
                                        sendInstruction(out_instruction);
                                        out_instruction.clear();
                                    }
                                    break;
                                case DOWN:
                                    if (game.shiftPlayer2Down() == 0) {
                                        out_instruction.addMove(2, game.getPlayer2x(), game.getPlayer2y(), game.getPlayer2rot());
                                        sendInstruction(out_instruction);
                                        out_instruction.clear();
                                    }
                                    break;
                                case LEFT:
                                    if (game.shiftPlayer2Left() == 0) {
                                        out_instruction.addMove(2, game.getPlayer2x(), game.getPlayer2y(), game.getPlayer2rot());
                                        sendInstruction(out_instruction);
                                        out_instruction.clear();
                                    }
                                    break;
                                case RIGHT:
                                    if (game.shiftPlayer2Right() == 0) {
                                        out_instruction.addMove(2, game.getPlayer2x(), game.getPlayer2y(), game.getPlayer2rot());
                                        sendInstruction(out_instruction);
                                        out_instruction.clear();
                                    }
                                    break;
                                default:
                                    throw new RuntimeException("Invalid Command player 2");
                            }
                        }
                        if (!game_running) {
                            frame_cycle_num = 0;
                            player1spawned = false;
                            player2spawned = false;
                        }

                        if (frame_cycle_num == 3)
                        {
                            int ret = game.doStep();
                            if ((ret & 1) != 0) {
                                out_instruction.addMerge(1, game.getPlayer1x(), game.getPlayer1y(), game.getPlayer1rot(), game.player1type());
                                sendInstruction(out_instruction);
                                out_instruction.clear();
                            }
                            else {
                                out_instruction.addMove(1, game.getPlayer1x(), game.getPlayer1y(), game.getPlayer1rot());
                                sendInstruction(out_instruction);
                                out_instruction.clear();
                            }
                            if ((ret & 2) != 0) {
                                out_instruction.addMerge(2, game.getPlayer2x(), game.getPlayer2y(), game.getPlayer2rot(), game.player2type());
                                sendInstruction(out_instruction);
                                out_instruction.clear();
                            }
                            else {
                                out_instruction.addMove(2, game.getPlayer2x(), game.getPlayer2y(), game.getPlayer2rot());
                                sendInstruction(out_instruction);
                                out_instruction.clear();
                            }
                            System.out.println("Step!");
                        }
                        frame_cycle_num = (frame_cycle_num + 1) % 4;

                        if (!game_running) {
                            frame_cycle_num = 0;
                            player1spawned = false;
                            player2spawned = false;
                        }
                    }
                    else {
                        frame_cycle_num = 0;
                        player1spawned = false;
                        player2spawned = false;
                    }

                }

                try {
                    sleep(590);
                    System.out.println("iteration!!!");
                }
                catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        private TETROMINO_NAME generate_tetromino(int gen) {
            switch (gen)
            {
                case 0:
                    return TETROMINO_NAME.I;
                case 1:
                    return TETROMINO_NAME.J;
                case 2:
                    return TETROMINO_NAME.L;
                case 3:
                    return TETROMINO_NAME.O;
                case 4:
                    return TETROMINO_NAME.S;
                case 5:
                    return TETROMINO_NAME.T;
                case 6:
                    return TETROMINO_NAME.Z;
                default:
                    throw new RuntimeException("invalid tetromino generated");
            }
        }

        public Session() {
            player1q = new ConcurrentLinkedQueue<Instruction>();
            player2q = new ConcurrentLinkedQueue<Instruction>();

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
    private ArrayList<Session> sessions;
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
        sessions = new ArrayList<Session>(1);
        sessions.add(new Session());
        sessions.set(0, new Session());
    }

    void sendInstruction(Instruction instruction) {
        for (IPresenter p : presenters) {
            p.update(instruction);
        }
    }
}
