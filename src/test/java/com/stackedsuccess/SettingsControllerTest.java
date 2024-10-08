package com.stackedsuccess;

import com.stackedsuccess.controllers.SettingsController;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SettingsControllerTest {

    private SettingsController settingsController;
    private GameControls gameControlsMock;

    @BeforeEach
    public void setUp() {
        settingsController = new SettingsController();
        gameControlsMock = mock(GameControls.class);
        settingsController.gameControls = gameControlsMock;

        settingsController.Down = new Button();
        settingsController.Right = new Button();
        settingsController.Left = new Button();
        settingsController.rotateR = new Button();
        settingsController.rotateL = new Button();
        settingsController.Hold = new Button();
        settingsController.Drop = new Button();
        settingsController.Pause = new Button();
        settingsController.pane = new AnchorPane();
    }

    @Test
    public void testOnKeyBindClicked() {
        MouseEvent mockEvent = mock(MouseEvent.class);
        when(mockEvent.getSource()).thenReturn(settingsController.Down);
        settingsController.onKeyBindClicked(mockEvent);
        assertEquals(settingsController.Down, settingsController.activeButton);
        for (Button b : new Button[] { settingsController.Right, settingsController.Left, settingsController.rotateR,
                settingsController.rotateL, settingsController.Hold, settingsController.Drop,
                settingsController.Pause }) {
            assertTrue(b.isDisabled(), "Button " + b.getText() + " should be disabled");
        }
    }

    @Test
    public void testHandleKeyPress() {
        Button button = settingsController.Down;
        settingsController.activeButton = button;
        KeyCode newKey = KeyCode.A;
        when(gameControlsMock.getAction(any())).thenReturn(Action.MOVE_DOWN);
        when(gameControlsMock.getKeyFromAction(Action.MOVE_DOWN)).thenReturn(KeyCode.DOWN);
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, newKey.getName(), newKey.getName(), newKey, false, false,
                false, false);
        settingsController.handleKeyPress(button, keyEvent);
        verify(gameControlsMock).setControl(Action.MOVE_DOWN, newKey);
        assertEquals(newKey.getName(), button.getText());
        assertEquals("#4CAF50", button.getStyle().split(":")[1].trim());
    }

    @Test
    public void testUpdateButtonLabels() {
        when(gameControlsMock.getKeyFromAction(Action.MOVE_DOWN)).thenReturn(KeyCode.DOWN);
        when(gameControlsMock.getKeyFromAction(Action.MOVE_RIGHT)).thenReturn(KeyCode.RIGHT);
        settingsController.updateButtonLabels();
        assertEquals("↓", settingsController.Down.getText());
        assertEquals("→", settingsController.Right.getText());
    }
}
