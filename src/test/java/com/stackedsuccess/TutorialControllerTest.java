package com.stackedsuccess;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.stackedsuccess.controllers.TutorialController;
import com.stackedsuccess.managers.SceneManager.AppUI;

import javafx.scene.input.MouseEvent;

class TutorialControllerTest {
    
    private TutorialController tutorialController;
    private Runnable mockOnTutorialCompleted;

    @BeforeEach
    public void setup() {
        tutorialController = new TutorialController();

        // Mock the Runnable objects
        mockOnTutorialCompleted = Mockito.mock(Runnable.class);

        //inject mock dependencies into the controller
        tutorialController.onTutorialCompleted = mockOnTutorialCompleted;
        tutorialController.setDestinationAppUI(AppUI.MAIN_MENU);
    }


    @Test
    void testExitCreateGame() {
        tutorialController.setCreateGame(true);

        MouseEvent mockEvent = Mockito.mock(MouseEvent.class);
        tutorialController.onClickExit(mockEvent);

        Mockito.verify(mockOnTutorialCompleted).run();
        assertFalse(tutorialController.getCreateGame());
    }
}
