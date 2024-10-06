package com.stackedsuccess.controllers;

import com.stackedsuccess.Action;
import com.stackedsuccess.GameControls;
import com.stackedsuccess.Main;
import com.stackedsuccess.managers.SceneManager.AppUI;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class SettingsController {
  @FXML public Button Down;
  @FXML public Button Right;
  @FXML public Button Left;
  @FXML public Button rotateR;
  @FXML public Button rotateL;
  @FXML public Button Hold;
  @FXML public Button Drop;
  @FXML private ImageView exitBtn;
  @FXML public AnchorPane pane;
  @FXML public Button Pause;

  public GameControls gameControls;

  private Boolean change = false;
  public Button activeButton;

  @FXML
  public void initialize() {
    gameControls = new GameControls();

    String filePath = "controls.txt";
    gameControls.loadControls(filePath);

    updateButtonLabels();
  }

  public void updateButtonLabels() {
    setButtonLabel(Down, Action.MOVE_DOWN);
    setButtonLabel(Right, Action.MOVE_RIGHT);
    setButtonLabel(Left, Action.MOVE_LEFT);
    setButtonLabel(rotateR, Action.ROTATE_CLOCKWISE);
    setButtonLabel(rotateL, Action.ROTATE_COUNTERCLOCKWISE);
    setButtonLabel(Hold, Action.HOLD);
    setButtonLabel(Drop, Action.HARD_DROP);
    setButtonLabel(Pause, Action.PAUSE);
  }

  private void setButtonLabel(Button button, Action action) {
    KeyCode keyCode = gameControls.getKeyFromAction(action);
    if (keyCode != null) {
      button.setText(getKeyDisplayText(keyCode));
    } else {
      button.setText("N/A");
    }
  }

  @FXML
  public void onKeyBindClicked(MouseEvent event) {
    Button clickedButton = (Button) event.getSource();

    clickedButton.setStyle("-fx-background-color: #f44336;");
    activeButton = clickedButton;

    disableOtherButtons(clickedButton);

    pane.requestFocus();

    pane.setOnKeyPressed(e -> handleKeyPress(clickedButton, e));

    change = true;
  }

  private void disableOtherButtons(Button clickedButton) {
    for (Button button : new Button[] {Down, Right, Left, rotateR, rotateL, Hold, Drop, Pause}) {
      if (button != clickedButton) {
        button.setDisable(true);
      }
    }
  }

  private void enableAllButtons() {
    for (Button button : new Button[] {Down, Right, Left, rotateR, rotateL, Hold, Drop, Pause}) {
      button.setDisable(false);
    }
  }

  public void handleKeyPress(Button clickedButton, KeyEvent event) {
    KeyCode newKey = event.getCode();

    event.consume();

    if (newKey != null) {
      Action action = getActionForButton(clickedButton);
      if (action != null) {
        // check if bound already
        for (Action existingAction : Action.values()) {
          KeyCode boundKey = gameControls.getKeyFromAction(existingAction);

          if (boundKey != null && boundKey.equals(newKey) && !existingAction.equals(action)) {
            showConflictAlert("Key already bound to: " + existingAction.name());
            return;
          }
        }

        // bind
        gameControls.setControl(action, newKey);
        clickedButton.setStyle("-fx-background-color: #4CAF50");
        clickedButton.setText(getKeyDisplayText(newKey));
      }
      enableAllButtons();
      activeButton = null;
      pane.setOnKeyPressed(null);
    }

    pane.setOnKeyPressed(null);
  }

  /**
   * Returns a user-friendly display name for the specified KeyCode. Handles special keys like
   * SPACE, LEFT, RIGHT, UP, and DOWN.
   */
  private String getKeyDisplayText(KeyCode keyCode) {
    switch (keyCode) {
      case SPACE:
        return "Space";
      case LEFT:
        return "←";
      case RIGHT:
        return "→";
      case UP:
        return "↑";
      case DOWN:
        return "↓";
      case PAGE_UP:
        return "Page Up";
      case PAGE_DOWN:
        return "Page Down";
      default:
        return keyCode.getName();
    }
  }

  private Action getActionForButton(Button button) {
    if (button == Down) return Action.MOVE_DOWN;
    if (button == Right) return Action.MOVE_RIGHT;
    if (button == Left) return Action.MOVE_LEFT;
    if (button == rotateR) return Action.ROTATE_CLOCKWISE;
    if (button == rotateL) return Action.ROTATE_COUNTERCLOCKWISE;
    if (button == Hold) return Action.HOLD;
    if (button == Drop) return Action.HARD_DROP;
    if (button == Pause) return Action.PAUSE;
    return null;
  }

  /**
   * This method closes the tutorial screen and returns the user to their game when the user clicks
   * on the exit cross. If appearing before the first game, we have to create the game here to stop
   * it starting in the background
   *
   * @param event the action of clicking the exit xross
   */
  @FXML
  private void onClickExit(MouseEvent event) {
    if (change == false) {
      Main.setUi(AppUI.MAIN_MENU);
    } else {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Unsaved Changes");
      alert.setHeaderText(null);
      alert.setContentText("You have unsaved settings. Please save them before exiting.");
      alert.showAndWait();
    }
  }

  /**
   * This method expands the size of the exit cross when it is hovered over to help users recognise
   * that it is a clickable object
   *
   * @param event the action of the user hovering their mouse over the exit cross
   */
  @FXML
  private void onExitHoveredOver(MouseEvent event) {
    exitBtn.scaleXProperty().set(1.2);
    exitBtn.scaleYProperty().set(1.2);
  }

  /**
   * Retruns the size of the exit cross to its original size when the user is no longer hovering
   * over it
   *
   * @param event the users mouse being moved away from the exit cross
   */
  @FXML
  private void onExitNotHoveredOver(MouseEvent event) {
    exitBtn.scaleXProperty().set(1);
    exitBtn.scaleYProperty().set(1);
  }

  private void showConflictAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Key Conflict");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  @FXML
  private void onSave(MouseEvent event) {
    String filePath = "controls.txt";
    gameControls.saveControls(filePath);
    change = false;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Key Bindings Saved");
    alert.setHeaderText(null);
    alert.setContentText("Key bindings have been saved.");
    alert.showAndWait();
  }

  @FXML
  private void onRevert(MouseEvent event) {
    String filePath = "controls.txt";
    gameControls.loadControls(filePath);

    updateButtonLabels();

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Key Bindings Reverted");
    alert.setHeaderText(null);
    alert.setContentText("Key bindings have been reverted to previous settings.");
    alert.showAndWait();
  }

  @FXML
  private void onDefault(MouseEvent event) {
    String defaultFilePath = "factoryControls.txt";

    gameControls.loadControls(defaultFilePath);

    updateButtonLabels();

    String filePath = "controls.txt";
    gameControls.saveControls(filePath);

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Default Key Bindings Restored");
    alert.setHeaderText(null);
    alert.setContentText("Key bindings have been reset to their default settings.");
    alert.showAndWait();
  }
}
