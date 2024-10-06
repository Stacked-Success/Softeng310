package com.stackedsuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.input.KeyCode;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameControlsTest {

    private GameControls gameControls;
    private String testFilePath = "test_controls.txt";

    @BeforeEach
    public void setUp() {
        gameControls = new GameControls();
        createTestControlsFile();
    }

    private void createTestControlsFile() {
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write("MOVE_LEFT,LEFT\n");
            writer.write("MOVE_RIGHT,RIGHT\n");
            writer.write("MOVE_DOWN,DOWN\n");
            writer.write("HARD_DROP,SPACE\n");
            writer.write("PAUSE,ESCAPE\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadControls() {
        gameControls.loadControls(testFilePath);
        assertEquals(KeyCode.LEFT, gameControls.getKeyFromAction(Action.MOVE_LEFT));
        assertEquals(KeyCode.RIGHT, gameControls.getKeyFromAction(Action.MOVE_RIGHT));
        assertEquals(KeyCode.DOWN, gameControls.getKeyFromAction(Action.MOVE_DOWN));
        assertEquals(KeyCode.SPACE, gameControls.getKeyFromAction(Action.HARD_DROP));
        assertEquals(KeyCode.ESCAPE, gameControls.getKeyFromAction(Action.PAUSE));
    }

    @Test
    public void testSaveControls() {
        gameControls.setControl(Action.MOVE_LEFT, KeyCode.A);
        gameControls.setControl(Action.MOVE_RIGHT, KeyCode.D);
        gameControls.saveControls(testFilePath);
        GameControls newGameControls = new GameControls();
        newGameControls.loadControls(testFilePath);
        assertEquals(KeyCode.A, newGameControls.getKeyFromAction(Action.MOVE_LEFT));
        assertEquals(KeyCode.D, newGameControls.getKeyFromAction(Action.MOVE_RIGHT));
    }

    @Test
    public void testLoadInvalidControls() {
        String invalidFilePath = "invalid_controls.txt";
        try (FileWriter writer = new FileWriter(invalidFilePath)) {
            writer.write("INVALID_ACTION,INVALID_KEY\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        gameControls.loadControls(invalidFilePath);
        assertEquals(KeyCode.LEFT, gameControls.getKeyFromAction(Action.MOVE_LEFT)); // Default control
    
    }
    

}
