package common;

// players commands to server
public enum COMMAND {
    CONNECT(0),
    DISCONNECT(1),
    READY(2),
    LEFT(3),
    UP(4),
    RIGHT(5),
    DOWN(6);

    private final int value;

    COMMAND(int name) {
        value = name;
    }

    int getValue() {
        return value;
    }
}
