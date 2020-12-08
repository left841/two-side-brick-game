package common;

public interface Tetromino {
    int[][] get();

    void rotateClockwise();
    void rotateCounterClockwise();

    int height();
    int width();
    int rotation();
    TETROMINO_NAME type();
}
