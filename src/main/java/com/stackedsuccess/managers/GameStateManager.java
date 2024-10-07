package com.stackedsuccess.managers;

import com.stackedsuccess.tetriminos.Tetrimino;
import java.io.IOException;

public interface GameStateManager {

    /**
     * Updates the display of the game board.
     * 
     * @param board a 2D array representing the current state of the game board.
     */
    void updateDisplay(int[][] board);

    /**
     * Triggers the game over event.
     * 
     * @throws IOException if an error occurs while handling the game over process.
     */
    void gameOver() throws IOException;

    /**
     * Sets the view for the next Tetrimino piece to be displayed.
     * 
     * @param tetrimino the next Tetrimino piece.
     */
    void setNextPieceView(Tetrimino tetrimino);

    /**
     * Sets the view for the held Tetrimino piece.
     * 
     * @param tetrimino the Tetrimino piece that is held.
     */
    void setHoldPieceView(Tetrimino tetrimino);

    /**
     * Updates the displayed score.
     * 
     * @param score the current score of the player.
     */
    void updateScore(int score);

    /**
     * Updates the displayed line count.
     * 
     * @param line the number of lines cleared by the player.
     */
    void updateLine(int line);

    /**
     * Updates the displayed level.
     * 
     * @param level the current level of the game.
     */
    void updateLevel(int level);
}
