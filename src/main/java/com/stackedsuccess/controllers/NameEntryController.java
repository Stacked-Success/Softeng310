package com.stackedsuccess.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class NameEntryController {

  @FXML AnchorPane mainPane;
  @FXML private Button advanceButton;
  @FXML private TextField textEntryField;

  private Runnable onNameEntryCompleted;
  public static String name;

  /**
   * Initializes the controller after the FXML file has been loaded. Currently, this method does not 
   * perform any actions.
   */
  public void initialize() {}

  /**
   * Handles the event when the user clicks the advance button. Retrieves the entered name from the 
   * text field and runs the callback (onNameEntryCompleted) to proceed in the game.
   * 
   * @throws IOException if an I/O error occurs during name entry processing
   */
  @FXML
  private void onAdvance() throws IOException {
    name = textEntryField.getText();
    if (onNameEntryCompleted != null) {
      onNameEntryCompleted.run();
    }
  }

  /**
   * Sets the callback to be executed when the name entry is completed.
   * 
   * @param onNameEntryCompleted a Runnable to execute when the name is entered and submitted
   */
  public void setOnNameCompleted(Runnable onNameEntryCompleted) {
    this.onNameEntryCompleted = onNameEntryCompleted;
  }
}
