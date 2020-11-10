package common.tetrominos;

import common.Tetromino;

public class O_Tetromino implements Tetromino {

    public O_Tetromino() {
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
}
