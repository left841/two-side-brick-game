package common.tetrominos;

import common.Tetromino;

public class Z_Tetromino implements Tetromino {
    private int state;

    public Z_Tetromino() {
        state = 0;
    }

    public Z_Tetromino(int start_state) {
        state = Math.abs(start_state) % 4;
    }

    @Override
    public int[][] get() {
        switch (state) {
            case 0:
                return new int[][] {
                        {0, 0, 1},
                        {0, 1, 1},
                        {0, 1, 0}
                };
            case 1:
                return new int[][] {
                        {0, 0, 0},
                        {1, 1, 0},
                        {0, 1, 1}
                };
            case 2:
                return new int[][] {
                        {0, 1, 0},
                        {1, 1, 0},
                        {1, 0, 0}
                };
            case 3:
                return new int[][] {
                        {1, 1, 0},
                        {0, 1, 1},
                        {0, 0, 0}
                };
            default:
                throw new RuntimeException("Invalid State");
        }
    }

    @Override
    public void rotateClockwise() {
        state = (state + 1) % 4;
    }

    @Override
    public void rotateCounterClockwise() {
        state = (state - 1 + 4) % 4;
    }

    @Override
    public int height() {
        return 3;
    }

    @Override
    public int width() {
        return 3;
    }
}
