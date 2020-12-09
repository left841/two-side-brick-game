package common.tetrominos;

import common.TETROMINO_NAME;
import common.Tetromino;

public class O_Tetromino implements Tetromino {

    public O_Tetromino() {
    }

    public O_Tetromino(int rot) {
    }

    @Override
    public int[][] get() {
        return new int[][]{
                {1, 1},
                {1, 1}
        };
    }

    @Override
    public void rotateClockwise() {
    }

    @Override
    public void rotateCounterClockwise() {
    }

    @Override
    public int height() {
        return 2;
    }

    @Override
    public int width() {
        return 2;
    }

    @Override
    public int rotation() {
        return 0;
    }

    @Override
    public TETROMINO_NAME type() {
        return TETROMINO_NAME.O;
    }

    @Override
    public void setRotation(int rot) {
    }
}
