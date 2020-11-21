package common;

public enum TETROMINO_NAME {
    I(0),
    J(1),
    L(2),
    O(3),
    S(4),
    T(5),
    Z(6);

    private final int value;

    TETROMINO_NAME(int name) {
        value = name;
    }

    int get_value() {
        return value;
    }

}
