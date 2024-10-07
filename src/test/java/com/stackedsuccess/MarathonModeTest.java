package com.stackedsuccess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.stackedsuccess.controllers.GameBoardController;
import com.stackedsuccess.managers.GameStateManager;
import com.stackedsuccess.tetriminos.Tetrimino;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class MarathonModeTest {
     @Mock
    private GameBoardController mockController;

    private GameInstance gameInstance;

   private GameBoard gameBoard;
    private GameStateManager gameStateManagerMock;
    private GameInstance gameInstanceMock;

    @BeforeEach
    public void setup() {
        gameStateManagerMock = Mockito.mock(GameStateManager.class);
        gameInstanceMock = Mockito.mock(GameInstance.class);
        gameBoard = new GameBoard(gameStateManagerMock, gameInstanceMock);
        gameInstance = new GameInstance(gameStateManagerMock, true, 40);
        ScoreRecorder.createScoreFile();
        ScoreRecorder.createMarathonScoreFile();

    }

    @Test
void testSaveBasicScore() throws IOException {
    // Clear existing scores
    ScoreRecorder.writeScores(List.of());

    // Save a new score
    String score = "15";
    ScoreRecorder.saveScore(score);

    // Verify the score is saved correctly
    List<Integer> scores = ScoreRecorder.getAllScores();
    assertEquals(1, scores.size());
    assertEquals(15, scores.get(0));
}

@Test
void testSaveMarathonScore() throws IOException {
    // Clear existing scores
    ScoreRecorder.writeMarathonScores(List.of());

    // Save a new Marathon Mode score
    int linesCleared = 10;
    int targetLines = 20;
    int timeTaken = 300; // 5 minutes
    ScoreRecorder.saveMarathonScore(linesCleared, targetLines, timeTaken);

    // Verify the score is saved correctly
    List<String> marathonScores = ScoreRecorder.getAllMarathonScores();
    assertEquals(1, marathonScores.size());
    assertEquals("10|20|300", marathonScores.get(0));
}

@Test
    public void testInitialBoardSetup() {
        assertEquals(22, gameBoard.getHeight());
        assertEquals(10, gameBoard.getWidth());
        assertNotNull(gameBoard.getCurrentTetrimino());
        assertNotNull(gameBoard.getNextTetrimino());
    }

    @Test
    public void testCollisionDetection() {
        // Test collision with the board boundaries
        assertTrue(gameBoard.isOutOfBounds(-1, 0));
        assertTrue(gameBoard.isOutOfBounds(10, 0));
        assertTrue(gameBoard.isOutOfBounds(0, 22));
    }

    @Test
    public void testMarathonModeTargetLines() {
        gameBoard.setMarathonTarget(40);
        assertEquals(0, gameBoard.getTotalLinesCleared());
    }

   

     @Test
    public void testHoldTetrimino() {
        Tetrimino currentTetrimino = gameBoard.getCurrentTetrimino();
        gameBoard.holdTetrimino();
        Tetrimino holdTetrimino = gameBoard.getHoldTetrimino();

        assertEquals(currentTetrimino, holdTetrimino);
        assertTrue(gameBoard.getHoldTetrimino() != null);
        assertFalse(gameBoard.getHoldTetrimino() == gameBoard.getCurrentTetrimino()); // Confirm swap
    }

     @Test
    public void testPauseGame() {
        gameInstance.togglePause();
        assertTrue(gameInstance.isPaused());

        gameInstance.togglePause();
        assertFalse(gameInstance.isPaused());
    }

    @Test
    public void testGameOver() {
        gameInstance.setGameOver(true);
        assertTrue(gameInstance.isGameOver());
    }


    @Test
    public void testMarathonModeStatus() {
        assertTrue(gameInstance.isMarathonMode());
        assertEquals(40, gameInstance.getTargetLines());
    }

    @Test
    public void testStopGame() {
        gameInstance.start();
        gameInstance.stopGame();
        assertTrue(gameInstance.isGameOver());
    }

    @Test
    public void testSaveAndRetrieveScore() throws IOException {
        ScoreRecorder.saveScore("1500");
        List<Integer> scores = ScoreRecorder.getAllScores();

        assertFalse(scores.isEmpty());
        assertEquals(1500, (int) scores.get(0));
    }

    @Test
    public void testHighScore() throws IOException {
        ScoreRecorder.saveScore("1000");
        ScoreRecorder.saveScore("2000");

        String highScore = ScoreRecorder.getHighScore();
        assertEquals("2000", highScore);
    }

    


}
