package com.stackedsuccess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameControls {

  private final Map<Action, KeyCode> controls;

  public GameControls() {
    controls = new HashMap<>();
    initializeControls();
    loadControls("controls.txt");
  }

  /**
   * Initialises the default key bindings for various actions in the game.
   *
   * <p>This method assigns specific keys to actions such as moving left, right, down, performing a
   * hard drop, rotating pieces, pausing the game, and holding a piece
   */
  private void initializeControls() {
    controls.put(Action.MOVE_LEFT, KeyCode.LEFT);
    controls.put(Action.MOVE_RIGHT, KeyCode.RIGHT);
    controls.put(Action.MOVE_DOWN, KeyCode.DOWN);
    controls.put(Action.HARD_DROP, KeyCode.SPACE);
    controls.put(Action.ROTATE_CLOCKWISE, KeyCode.Z);
    controls.put(Action.ROTATE_COUNTERCLOCKWISE, KeyCode.X);
    controls.put(Action.PAUSE, KeyCode.ESCAPE);
    controls.put(Action.HOLD, KeyCode.C);
  }

  /**
   * Assigns action to key, allowing rebinding of controls.
   *
   * @param action the Action to bind the key to
   * @param key the KeyCode that will perform the bound action
   */
  public void setControl(Action action, KeyCode key) {
    controls.put(action, key);
  }

  /**
   * Get associated Action from keyboard input.
   *
   * @param event the keyboard event captured
   * @return the bound action or null if not key not bound.
   */
  public Action getAction(KeyEvent event) {
    KeyCode key = event.getCode();
    if (controls.containsValue(key)) {
      return getKeyFromValue(controls, key);
    } else {
      return null;
    }
  }

  /**
   * Gets Action associated to specified keybinding.
   *
   * @param map the controls hashmap containing actions and associated keybindings
   * @param value the KeyCode from keyboard event
   * @return the Action associated with the keybinding given
   */
  private Action getKeyFromValue(Map<Action, KeyCode> map, KeyCode value) {
    for (Map.Entry<Action, KeyCode> entry : map.entrySet()) {
      if (entry.getValue().equals(value)) {
        return entry.getKey();
      }
    }
    return null;
  }

  /**
   * Gets the KeyCode associated with the specified Action.
   *
   * @param action the Action for which to retrieve the key binding
   * @return the KeyCode associated with the action, or null if not found
   */
  public KeyCode getKeyFromAction(Action action) {
    return controls.get(action);
  }

  /**
 * Saves the current key bindings to a specified file.
 * Each control action is saved along with its corresponding key code, 
 * separated by a comma, in the format: Action,KeyCode.
 * 
 * @param filePath the path of the file where controls will be saved
 * @throws IOException if an I/O error occurs while writing to the file
 */
  public void saveControls(String filePath) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      for (Map.Entry<Action, KeyCode> entry : controls.entrySet()) {
        writer.write(entry.getKey() + "," + entry.getValue().name());
        writer.newLine();
      }
    } catch (IOException e) {
    }
  }

  /**
 * Loads key bindings from a specified file.
 * Each line in the file should be in the format: Action,KeyCode.
 * The method reads the file and sets the controls accordingly.
 * 
 * @param filePath the path of the file to load controls from
 * @throws IOException if an I/O error occurs while reading the file
 * @throws IllegalArgumentException if the action or key code is invalid
 */
  public void loadControls(String filePath) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length != 2) continue;
        try {
          Action action = Action.valueOf(parts[0].trim());
          KeyCode key = KeyCode.valueOf(parts[1].trim());
          setControl(action, key);
        } catch (IllegalArgumentException e) {
        }
      }
    } catch (IOException e) {
    }
  }
}
