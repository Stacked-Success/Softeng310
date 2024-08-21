package com.stackedsuccess.controllers;

import com.stackedsuccess.Main;
import com.stackedsuccess.SceneManager;
import com.stackedsuccess.SceneManager.AppUI;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;

public class HomeScreenController {

  public void exitGame() {
    System.exit(0);
  }

  public void startGame() throws IOException {
    SceneManager.addScene(AppUI.GAME, loadFxml("GameBoard"));
    Main.setUi(AppUI.GAME);
  }

  public static Parent loadFxml(final String fxml) throws IOException {
    return new FXMLLoader(Main.class.getResource("/fxml/" + fxml + ".fxml")).load();
  }

  public void onKeyPressed(KeyEvent event) {
    // Your code here
  }
}
