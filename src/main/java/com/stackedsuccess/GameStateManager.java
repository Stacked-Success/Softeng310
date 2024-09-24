package com.stackedsuccess;

import com.stackedsuccess.tetriminos.Tetrimino;
import java.io.IOException;

public interface GameStateManager {

    void updateDisplay(int[][] board);
    void gameOver() throws IOException;
    void setNextPieceView(Tetrimino tetrimino);
    void setHoldPieceView(Tetrimino tetrimino);
    void updateScore(int score);
    void updateLine(int line);
    void updateLevel(int level);
}
