package com.stackedsuccess.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public class HomeScreenController {

    @FXML
    private Button easyButton;

    @FXML
    private Button mediumButton;

    @FXML
    private Button hardButton;

    @FXML
    private Button startButton;

    @FXML
    private Text difficultyText;

    private String selectedDifficulty = "Easy"; // Default difficulty

    @FXML
    private void initialize() {
        // Initialization logic if needed
        updateDifficultyText();
    }

    @FXML
    private void onKeyPressed(KeyEvent event) {
        // Handle key press events if necessary
    }

    @FXML
    private void handleEasyButtonAction(ActionEvent event) {
        selectedDifficulty = "Easy";
        updateDifficultyText();
    }

    @FXML
    private void handleMediumButtonAction(ActionEvent event) {
        selectedDifficulty = "Medium";
        updateDifficultyText();
    }

    @FXML
    private void handleHardButtonAction(ActionEvent event) {
        selectedDifficulty = "Hard";
        updateDifficultyText();
    }

    @FXML
    private void handleStartButtonAction(ActionEvent event) {
        // Handle the logic to start the game based on selected difficulty
        System.out.println("Game started with difficulty: " + selectedDifficulty);
    }

    private void updateDifficultyText() {
        difficultyText.setText("Difficulty: " + selectedDifficulty);
    }
}