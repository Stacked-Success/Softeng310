package com.stackedsuccess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

import com.stackedsuccess.controllers.GameBoardController;
import com.stackedsuccess.tetriminos.Tetrimino;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class GameBoardControllerTest extends ApplicationTest {

    private GameBoardController gameBoardController;
    private GameInstance gameInstanceMock;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameBoard.fxml"));
        Parent root = loader.load();
        gameBoardController = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setup() {
        gameInstanceMock = Mockito.mock(GameInstance.class);
        gameBoardController.setGameInstance(gameInstanceMock);
    }

    @Test
    public void testInitialize() {
        // Ensure that initializing sets correct default labels
        gameBoardController.initialize();
        assertEquals("0", gameBoardController.scoreLabel.getText());
        assertEquals("1", gameBoardController.levelLabel.getText());
        assertEquals("0", gameBoardController.lineLabel.getText());
    }

    @Test
    public void testUpdateScore() {
        gameBoardController.updateScore(1500);
        assertEquals("1500", gameBoardController.scoreLabel.getText());
    }

    @Test
    public void testUpdateLineInBasicMode() {
        when(gameInstanceMock.isMarathonMode()).thenReturn(false);
        gameBoardController.updateLine(5);
        assertEquals("5", gameBoardController.lineLabel.getText());
    }

    @Test
    public void testUpdateLineInMarathonMode() {
        when(gameInstanceMock.isMarathonMode()).thenReturn(true);
        when(gameInstanceMock.getTargetLines()).thenReturn(40);
        gameBoardController.updateLine(10);
        assertEquals("10/40", gameBoardController.lineLabel.getText());
    }

    @Test
    public void testGameOver() throws IOException {
        gameBoardController.gameOver();
        verify(gameInstanceMock, times(1)).setGameOver(true);
        assertTrue(gameBoardController.gameOverBox.isVisible());
    }

    @Test
    public void testOnKeyPressedPause() {
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false);
        GameControls gameControlsMock = Mockito.mock(GameControls.class);
        when(gameControlsMock.getAction(keyEvent)).thenReturn(Action.PAUSE);

        gameBoardController.onKeyPressed(keyEvent);
        verify(gameInstanceMock, times(1)).togglePause();
    }

    @Test
    public void testOnClickPauseButton() {
        gameBoardController.onClickPauseButton();
        verify(gameInstanceMock, times(1)).togglePause();
    }

    @Test
    public void testOnClickRestart() throws IOException {
        gameBoardController.onClickRestart(new ActionEvent());
        assertEquals("0", gameBoardController.scoreLabel.getText());
        assertEquals("0", gameBoardController.lineLabel.getText());
        assertEquals("1", gameBoardController.levelLabel.getText());
    }

    @Test
    public void testTimerInMarathonMode() {
        when(gameInstanceMock.isMarathonMode()).thenReturn(true);
        gameBoardController.initialize();
        assertTrue(gameBoardController.timerVbox.isVisible());
    }

    @Test
    public void testSetNextPieceView() {
        Tetrimino tetriminoMock = Mockito.mock(Tetrimino.class);
        gameBoardController.setNextPieceView(tetriminoMock);
        assertNotNull(gameBoardController.nextPieceView.getImage());
    }

    @Test
    public void testSetHoldPieceView() {
        Tetrimino tetriminoMock = Mockito.mock(Tetrimino.class);
        gameBoardController.setHoldPieceView(tetriminoMock);
        assertNotNull(gameBoardController.holdPieceView.getImage());
    }

    @Test
    public void testTogglePauseScreen() {
        when(gameInstanceMock.isPaused()).thenReturn(false);
        gameBoardController.onClickPauseButton();
        assertTrue(gameInstanceMock.isPaused());
        assertEquals(0.5, gameBoardController.pauseBackground.getOpacity(), 0.01);
        assertEquals(1, gameBoardController.pauseLabelBackground.getOpacity(), 0.01);
    }
    
}
