package common.tetraminos;

import common.Tetramino;

public class O_Tetramino implements Tetramino {

    public O_Tetramino() {
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
