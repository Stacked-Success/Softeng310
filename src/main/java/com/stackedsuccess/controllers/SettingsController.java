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

  /**
   * Updates the text labels of all key binding buttons to reflect the currently assigned keys. Each
   * button's label is set based on the action it corresponds to.
   */
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

  /**
   * Sets the label text of a button based on the key assigned to a specific action. If a key is
   * assigned to the action, its display name is shown; otherwise, "N/A" is displayed.
   *
   * @param button the button whose label is being updated
   * @param action the action associated with the button
   */
  private void setButtonLabel(Button button, Action action) {
    KeyCode keyCode = gameControls.getKeyFromAction(action);
    if (keyCode != null) {
      button.setText(getKeyDisplayText(keyCode));
    } else {
      button.setText("N/A");
    }
  }

  /**
   * Handles the event when a key binding button is clicked. Changes the style of the clicked
   * button, sets it as the active button, disables other buttons, and listens for a key press to
   * rebind the action.
   *
   * @param event the mouse event triggered by clicking the button
   */
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

  /**
   * Disables all key binding buttons except the one that was clicked.
   *
   * @param clickedButton the button that was clicked
   */
  private void disableOtherButtons(Button clickedButton) {
    for (Button button : new Button[] {Down, Right, Left, rotateR, rotateL, Hold, Drop, Pause}) {
      if (button != clickedButton) {
        button.setDisable(true);
      }
    }
  }

  /** Enables all key binding buttons after a key has been successfully bound. */
  private void enableAllButtons() {
    for (Button button : new Button[] {Down, Right, Left, rotateR, rotateL, Hold, Drop, Pause}) {
      button.setDisable(false);
    }
  }

  /**
   * Handles the key press event when rebinding an action to a new key. If the new key is not
   * already bound to another action, it binds the key to the action and updates the button's label.
   * If the key is already bound, it shows a conflict alert.
   *
   * @param clickedButton the button representing the action to be rebound
   * @param event the key event triggered by pressing a key
   */
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

  /**
   * Displays a warning alert when there is a conflict in key bindings.
   *
   * @param message the conflict message to be displayed in the alert
   */
  private void showConflictAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Key Conflict");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Saves the current key bindings to a file when the save button is clicked. Displays a
   * confirmation alert once the key bindings have been saved.
   *
   * @param event the mouse event triggered by clicking the save button
   */
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

  /**
   * Reverts the key bindings to the last saved state when the revert button is clicked. Loads the
   * key bindings from the file and updates the button labels to reflect the changes. Displays a
   * confirmation alert after the key bindings have been reverted.
   *
   * @param event the mouse event triggered by clicking the revert button
   */
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

  /**
   * Resets the key bindings to their default settings when the default button is clicked. Loads the
   * default key bindings from a file, updates the button labels, and saves the default bindings.
   * Displays a confirmation alert after the key bindings have been reset to default.
   *
   * @param event the mouse event triggered by clicking the default button
   */
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
