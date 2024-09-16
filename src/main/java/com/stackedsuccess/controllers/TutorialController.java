package com.stackedsuccess.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class TutorialController {

    private ImageView exitBtb;

    public void initialize() {
    }

    @FXML
    private void onExitHoveredOver(MouseEvent event) {
        exitBtb.scaleXProperty().set(1.2);
        exitBtb.scaleYProperty().set(1.2);
    }

    @FXML
    private void onExitNotHoveredOver(MouseEvent event) {
        exitBtb.scaleXProperty().set(1);
        exitBtb.scaleYProperty().set(1);
    }

    @FXML
    private void onExitClicked(MouseEvent event) {
        System.exit(0);
    }
    
}
