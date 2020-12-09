package client.model;

import client.view.IObserver;
import common.*;
import common.tetrominos.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

class ModelClient implements IModelClient {
    InetAddress ip = null;
    int port = 3124;
    Thread connection = null;
    Socket cs;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    Instruction instruction = new Instruction();
    GameField game = new GameField();
    ArrayList<IObserver> observers = new ArrayList<>();
    int room = -1;

    ModelClient() {
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRoomNumber() {
        return room;
    }

    @Override
    public void addObserver(IObserver o) {
        observers.add(o);
    }

    @Override
    public void join() {
        refresh();
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(String ipStr) {
        try {
            ip = InetAddress.getByName(ipStr);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public GameField getGameState() {
        return game;
    }

    public void setPort(String portStr) {
        port = Integer.parseInt(portStr);
    }

    public void connect() {
        if(cs != null) {
            connection.stop();
            cs = null;
        }
        try {
            cs = new Socket(ip, port);
            oos = new ObjectOutputStream(cs.getOutputStream());
            System.out.println("Client start");
            instruction.addConnect();
            instruction.send(oos);

            connection = new Thread() {
                @Override
                public void run() {
                    try {
                        System.out.println("Start");
                        ois = new ObjectInputStream(cs.getInputStream());
                        while (true) {
                            Instruction instruction2 = new Instruction();
                            instruction2.recv(ois);
                            processInstruction(instruction2);
                            System.out.println(instruction2.getInstruction());
                        }
                    } catch (EOFException e) {
                        System.out.println("Disconnected!");
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            };
            connection.start();
        } catch (IOException e) {
            System.out.println("Connection failed!");
        }
        refresh();
    }

    Tetromino tetromino1;
    int prev_x1, prev_y1;
    Tetromino tetromino2;
    int prev_x2, prev_y2;

    void processInstruction(Instruction instruction) {
        System.out.println("instruction: " + instruction.getInstruction().get(0));
        EVENT cmd = EVENT.values()[instruction.getInstruction().get(0)];
        System.out.println("Event: " + cmd);
        switch (cmd) {
            case START: {
                System.out.println("Game has started");
                game.init();
                refresh();
                break;
            }
            case SPAWN: {
                Tetromino tetromino;
                int color = 1 - (instruction.getInstruction().get(1) - 1);
                if (color == 0) {
                    tetromino = tetromino1;
                } else {
                    tetromino = tetromino2;
                }
                int x = instruction.getInstruction().get(2);
                int y = instruction.getInstruction().get(3);
                int rot = instruction.getInstruction().get(4);
                TETROMINO_NAME type = TETROMINO_NAME.values()[instruction.getInstruction().get(5)];
                switch (type)
                {
                    case I:
                        tetromino = new I_Tetromino(rot);
                        break;
                    case J:
                        tetromino = new J_Tetromino(rot);
                        break;
                    case L:
                        tetromino = new L_Tetromino(rot);
                        break;
                    case O:
                        tetromino = new O_Tetromino(rot);
                        break;
                    case S:
                        tetromino = new S_Tetromino(rot);
                        break;
                    case T:
                        tetromino = new T_Tetromino(rot);
                        break;
                    case Z:
                        tetromino = new Z_Tetromino(rot);
                        break;
                    default:
                        throw new RuntimeException("Wrong tetromino");
                }
                int[][] t = tetromino.get();
                for (int i = x; i < x + t.length; ++i) {
                    for (int j = y; j < y + t[0].length; ++j) {
                        if (t[i - x][j - y] > 0) {
                            game.set(i, j, color);
                        }
                    }
                }
                if (color == 0) {
                    tetromino1 = tetromino;
                    prev_x1 = x;
                    prev_y1 = y;
                } else {
                    tetromino2 = tetromino;
                    prev_x2 = x;
                    prev_y2 = y;
                }
                refresh();
                break;
            }
            case MOVE: {
                Tetromino tetromino;
                int color = 1 - (instruction.getInstruction().get(1) - 1);
                int x, y;
                if (color == 0) {
                    tetromino = tetromino1;
                    x = prev_x1;
                    y = prev_y1;
                } else {
                    tetromino = tetromino2;
                    x = prev_x2;
                    y = prev_y2;
                }
                int rot = instruction.getInstruction().get(4);
                int[][] t = tetromino.get();
                for (int i = x; i < x + t.length; ++i) {
                    for (int j = y; j < y + t[0].length; ++j) {
                        if (t[i - x][j - y] > 0) {
                            game.set(i, j, 1 - color);
                        }
                    }
                }
                x = instruction.getInstruction().get(2);
                y = instruction.getInstruction().get(3);
                for (int i = x; i < x + t.length; ++i) {
                    for (int j = y; j < y + t[0].length; ++j) {
                        if (t[i - x][j - y] > 0) {
                            game.set(i, j, color);
                        }
                    }
                }
                if (color == 0) {
                    tetromino1 = tetromino;
                    prev_x1 = x;
                    prev_y1 = y;
                } else {
                    tetromino2 = tetromino;
                    prev_x2 = x;
                    prev_y2 = y;
                }
                refresh();
                break;
            }
            default: {
                break;
            }
        }
    }

    public void sendInstruction(COMMAND command) {
        Instruction instruction = new Instruction();
        if (oos == null) {
            return;
        }
        try {
            switch (command) {
                case LEFT: {
                    System.out.println("LEFT");
                    instruction.addLeft();
                    instruction.send(oos);
                } break;
                case RIGHT: {
                    System.out.println("RIGHT");
                    instruction.addRight();
                    instruction.send(oos);
                } break;
                default: {
                    System.out.println("NOP");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void refresh() {
        for (IObserver observer : observers) {
            observer.refresh();
        }
    }
}
