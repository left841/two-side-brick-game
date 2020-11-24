package common;

import common.tetrominos.*;

import java.util.Arrays;
import java.util.Random;

public class GameField {
    private final int[][] field;
    private Tetromino player1, player2;
    private int p1_x, p2_x, p1_y, p2_y;
    private int width, height;

    public GameField() {
        width = 30;
        height = 30;
        field = new int[30][30];
        for (int[] i: field)
            Arrays.fill(i, 0);
        player1 = null;
        player2 = null;
    }

    public GameField(int height, int width) {
        this.width = width;
        this.height = height;
        field = new int[height][width];
        for (int[] i: field)
            Arrays.fill(i, 0);
        player1 = null;
        player2 = null;
    }

    public void init() {
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height / 2; ++j) {
                field[i][j] = 0;
            }
            for (int j = height / 2; j < height; ++j) {
                field[i][j] = 1;
            }
        }
    }

    public int get(int x, int y) {
        return field[x][y];
    }

    public int tryPlaceTetromino(Tetromino player, int new_x, int new_y, int color) {
        if (player == null)
            return 3;
        int[][] box = player.get();
        for (int i = 0; i < player.height(); ++i) {
            for (int j = 0; j < player.width(); ++j) {
                if (box[i][j] != 0) {
                    if ((new_x + i < 0) || (new_x + i > field.length) || (new_y + j < 0) || (new_y + j > field[0].length))
                        return 1;
                    else if (field[new_x + i][new_y + j] != color)
                        return 2;
                }
            }
        }
        return 0;
    }

    public void setPlayer1(TETROMINO_NAME tetromino) {
        switch (tetromino)
        {
            case I:
                player1 = new I_Tetromino();
                break;
            case J:
                player1 = new J_Tetromino();
                break;
            case L:
                player1 = new L_Tetromino();
                break;
            case O:
                player1 = new O_Tetromino();
                break;
            case S:
                player1 = new S_Tetromino();
                break;
            case T:
                player1 = new T_Tetromino();
                break;
            case Z:
                player1 = new Z_Tetromino();
                break;
        }
    }

    public void setPlayer2(TETROMINO_NAME tetromino) {
        switch (tetromino) {
            case I:
                player2 = new I_Tetromino();
                break;
            case J:
                player2 = new J_Tetromino();
                break;
            case L:
                player2 = new L_Tetromino();
                break;
            case O:
                player2 = new O_Tetromino();
                break;
            case S:
                player2 = new S_Tetromino();
                break;
            case T:
                player2 = new T_Tetromino();
                break;
            case Z:
                player2 = new Z_Tetromino();
                break;
        }
    }

    public int spawnPlayer1() {
        p1_x = 0;
        p1_y = field[0].length / 2 - player1.width() / 2;
        return tryPlaceTetromino(player1, p1_x, p1_y, 0);
    }

    public int spawnPlayer2() {
        p2_x = field.length - player2.height();
        p2_y = field[0].length / 2 - player2.width() / 2;
        return tryPlaceTetromino(player2, p2_x, p2_y, 1);
    }

    public int rotatePlayer1() {
        player1.rotateClockwise();
        int ret = tryPlaceTetromino(player1, p1_x, p1_y, 0);
        if (ret == 0)
            return 0;
        else {
            player1.rotateCounterClockwise();
            return 1;
        }
    }

    public int rotatePlayer2() {
        player1.rotateClockwise();
        int ret = tryPlaceTetromino(player2, p2_x, p2_y, 0);
        if (ret == 0)
            return 0;
        else {
            player2.rotateCounterClockwise();
            return 1;
        }
    }

    public int doStep() {
        return 0;
    }

}
