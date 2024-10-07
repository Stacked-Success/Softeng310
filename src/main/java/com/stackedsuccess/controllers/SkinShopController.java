package com.stackedsuccess.controllers;

import com.stackedsuccess.Main;
import com.stackedsuccess.ScoreRecorder;
import com.stackedsuccess.managers.SceneManager;
import com.stackedsuccess.managers.SkinDisplayMaker;
import com.stackedsuccess.managers.TetriminoImageManager;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SkinShopController {

  @FXML private VBox skinDisplayBox;
  @FXML private ImageView currentSkinImageView;
  @FXML private Label highScoreLabel;
  @FXML private Label skinNameLabel;

  private TetriminoImageManager imageManager;

  private final List<String> availableSkins =
      List.of("DefaultSkin", "BrickSkin", "EmojiSkin", "ChessSkin", "IceSkin", "TNTSkin");

  public SkinShopController() {
    imageManager = TetriminoImageManager.getInstance();
  }

  /**
   * Initialises the SkinShopController by populating the skin display and setting the current skin
   * preview.
   */
  @FXML
  public void initialize() {
    updateHighScoreLabel();
    populateSkinDisplay();
    setDefaultSkinPreview();
  }

  /** Updates the high score label with the current high score. */
  private void updateHighScoreLabel() {
    try {
        String highScore = ScoreRecorder.getHighScore();

        if (highScore.equals("No high score available.")) {
            highScoreLabel.setText("High Score: 0");
        } else {
            String[] parts = highScore.split("=");

            if (parts.length == 2) {
                try {
                    int parsedScore = Integer.parseInt(parts[1]);
                    highScoreLabel.setText("High Score: " + parsedScore);
                } catch (NumberFormatException e) {
                    highScoreLabel.setText("High Score: 0");
                }
            } else {
                highScoreLabel.setText("High Score: 0");
            }
        }
    } catch (IOException e) {
        highScoreLabel.setText("High Score: 0");
    }
}

  /** Populates the skin selection display with available skins. */
  private void populateSkinDisplay() {
    SkinDisplayMaker skinMaker = new SkinDisplayMaker();
    HBox currentRow = new HBox(20);
    skinDisplayBox.getChildren().add(currentRow);

    int skinsInRow = 0;

    for (String skin : availableSkins) {
      VBox skinPane = skinMaker.createSkinPane(skin, event -> onSelectSkin(skin));
      currentRow.getChildren().add(skinPane);

      skinsInRow++;

      if (skinsInRow == 4) {
        currentRow = new HBox(20);
        skinDisplayBox.getChildren().add(currentRow);
        skinsInRow = 0;
      }
    }

    for (int i = availableSkins.size(); i < 20; i++) {
      VBox comingSoonPane = skinMaker.createComingSoonPane();
      currentRow.getChildren().add(comingSoonPane);
      skinsInRow++;

      if (skinsInRow == 4) {
        currentRow = new HBox(20);
        skinDisplayBox.getChildren().add(currentRow);
        skinsInRow = 0;
      }
    }
  }

  /**
   * Handles the event when a skin is selected.
   *
   * @param skinName the name of the selected skin
   */
  private void onSelectSkin(String skinName) {
    imageManager.setSkinTheme(skinName);
    updateSkinPreview(skinName);
    String displayName = skinName.replace("Skin", "");
    skinNameLabel.setText(displayName);
    System.out.println("Switched to " + skinName);
  }

  /** Sets the default skin preview based on the currently applied skin. */
  private void setDefaultSkinPreview() {
    String currentSkin = imageManager.getCurrentSkin();
    updateSkinPreview(currentSkin);
    String displayName = currentSkin.replace("Skin", "");
    skinNameLabel.setText(displayName); 
  }

  /**
   * Updates the large preview image for the currently selected skin.
   *
   * @param skinName the name of the selected skin
   */
  private void updateSkinPreview(String skinName) {
    String imagePath = "file:src/main/resources/images/" + skinName + "/block.png";
    Image image = new Image(imagePath, 300, 300, true, true);
    currentSkinImageView.setImage(image);
  }

  /** Navigates back to the main menu. */
  @FXML
  public void onBack(MouseEvent event) throws IOException {
    Main.setUi(SceneManager.AppUI.MAIN_MENU);
  }
}
