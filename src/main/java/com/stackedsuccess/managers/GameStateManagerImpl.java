package com.stackedsuccess.managers;

import com.stackedsuccess.tetriminos.Tetrimino;
import java.io.IOException;

public class GameStateManagerImpl implements GameStateManager {

    @Override
    public void updateDisplay(int[][] board) {
        // Update the UI to reflect the current game board
    }

    @Override
    public void gameOver() throws IOException {
        // Handle game over logic, such as displaying a game-over message
        System.out.println("Game Over!");
    }

    @Override
    public void setNextPieceView(Tetrimino tetrimino) {
        // Update the UI to show the next Tetrimino piece
    }

    @Override
    public void setHoldPieceView(Tetrimino tetrimino) {
        // Update the UI to show the held piece
    }

    @Override
    public void updateScore(int score) {
        // Update the displayed score in the game
        System.out.println("Score: " + score);
    }

    @Override
    public void updateLine(int line) {
        // Update the number of lines cleared on the UI
        System.out.println("Lines Cleared: " + line);
    }

    @Override
    public void updateLevel(int level) {
        // Update the level in the UI
        System.out.println("Level: " + level);
    }
}
