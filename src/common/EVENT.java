package common;

// server commands for players
public enum EVENT {
    START(0),
    END(1),
    SPAWN(2),
    MOVE(3),
    MERGE(4);

    private final int value;

    EVENT(int name) {
        value = name;
    }

    int getValue() {
        return value;
    }
}
