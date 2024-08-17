package com.stackedsuccess.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeScreenController {

    @FXML
    private Button startButton;

    @FXML
    private void startGame(ActionEvent event) {
        // Handle the logic to start the game based on selected difficulty
        System.out.println("Game started");
    }

}