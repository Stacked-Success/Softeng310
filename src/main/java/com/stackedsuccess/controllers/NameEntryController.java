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

  public void initialize() {}

  @FXML
  private void onAdvance() throws IOException {
    name = textEntryField.getText();
    if (onNameEntryCompleted != null) {
      onNameEntryCompleted.run();
    }
  }

  public void setOnNameCompleted(Runnable onNameEntryCompleted) {
    this.onNameEntryCompleted = onNameEntryCompleted;
  }
}
