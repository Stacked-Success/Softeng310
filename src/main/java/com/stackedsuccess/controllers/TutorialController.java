package com.stackedsuccess.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class TutorialController {

    private ImageView exitBtb;

    public void initialize() {
        //Utilize the deafault constructor build into javafx
    }

    /**
     * This method expands the size of the exit cross when it is hovered
     * over to help users recognise that it is a clickable object
     * 
     * @param event the action of the user hovering their mouse over the exit cross
     */
    @FXML
    private void onExitHoveredOver(MouseEvent event) {
        exitBtb.scaleXProperty().set(1.2);
        exitBtb.scaleYProperty().set(1.2);
    }

    /**
     * Retruns the size of the exit cross to its original size when the user
     * is no longer hovering over it
     * 
     * @param event the users mouse being moved away from the exit cross
     */
    @FXML
    private void onExitNotHoveredOver(MouseEvent event) {
        exitBtb.scaleXProperty().set(1);
        exitBtb.scaleYProperty().set(1);
    }

    /**
     * This method closes the tutorial screen and returns the user to their game
     * when the user clicks on the exit cross
     * 
     * @param event the action of clicking the exit xross
     */
    @FXML
    private void onExitClicked(MouseEvent event) {
        //TODO: Add code to close the tutorial screen
    }
    
}
