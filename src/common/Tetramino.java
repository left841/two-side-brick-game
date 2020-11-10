package common;

public interface Tetramino {
    int[][] get();

    void rotateClockwise();
    void rotateCounterClockwise();

    int height();
    int width();
}
