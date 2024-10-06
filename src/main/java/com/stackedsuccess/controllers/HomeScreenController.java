package com.stackedsuccess.controllers;

import com.stackedsuccess.GameInstance;
import com.stackedsuccess.Main;
import com.stackedsuccess.ScoreRecorder;
import com.stackedsuccess.managers.GameStateManager;
import com.stackedsuccess.managers.SceneManager;
import com.stackedsuccess.managers.SceneManager.AppUI;
import com.stackedsuccess.managers.sound.SoundManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class HomeScreenController {
  @FXML Slider difficultySlider;

  @FXML private Button pastScoresButton;
  @FXML private ListView<String> MarathonPastScores;
  @FXML private ListView<String> basicPastScores;
  @FXML private Button tutorialBtn;
  @FXML AnchorPane settingsPane;
  @FXML AnchorPane difficultyPane;
  @FXML AnchorPane mainPane;
  @FXML AnchorPane pastScorePane;
  @FXML RadioButton soundBtn;
  @FXML RadioButton musicBtn;
  @FXML RadioButton marathonBtn;
  @FXML RadioButton basicBtn;
  @FXML ImageView marathonScoreImageView;
  @FXML ImageView basicScoreImageView;
 

  @FXML Rectangle clickMarathon;
  @FXML Rectangle clickBasic;


  private TutorialController tutorialController;
  private SkinShopController skinShopController;
  private GameInstance gameInstance;

  /**
   * Initialises the Home Screen controller by setting up the home screen.
   *
   * <p>This method sets the initial focus on the difficulty slider (1) and loads past scores from a
   * file into the list view component that displays previous game scores if the players clicks on
   * the past scores button
   */
  @FXML
  public void initialize() {
    difficultySlider.requestFocus();
    // Optionally load scores during initialization
    loadBasicModeScores();    // Load and set scores for Basic Mode
    loadMarathonModeScores(); // Load and set scores for Marathon Mode
   
    pastScoresButton.setFocusTraversable(false);
    tutorialController = new TutorialController();
    tutorialController.setDestinationAppUI(AppUI.MAIN_MENU);
    updateSoundButtonState();
    updateMusicButtonState();
    Platform.runLater(() -> SoundManager.getInstance().playBackgroundMusic("mainmenu"));
    basicBtn.setSelected(true);
    marathonBtn.setSelected(false);

    // Ensure files exist
    ScoreRecorder.createMarathonScoreFile();
    ScoreRecorder.createScoreFile();
  }

  // Method to set the GameInstance
  public void setGameInstance(GameInstance gameInstance) {
    this.gameInstance = gameInstance;
  }

  /**
   * Updates the state of the sound effects toggle button.
   *
   * <p>This method sets the toggle button text and selection state based on whether the sound
   * effects are muted or not.
   */
  private void updateSoundButtonState() {
    if (SoundManager.getInstance().isSoundEffectsMuted()) {
      soundBtn.setSelected(false);
      soundBtn.setText("OFF");
    } else {
      soundBtn.setSelected(true);
      soundBtn.setText("ON");
    }
  }

  /**
   * Updates the state of the background music toggle button.
   *
   * <p>This method sets the toggle button text and selection state based on whether the background
   * music is muted or not.
   */
  private void updateMusicButtonState() {
    if (SoundManager.getInstance().isBackgroundMusicMuted()) {
      musicBtn.setSelected(false);
      musicBtn.setText("OFF");
    } else {
      musicBtn.setSelected(true);
      musicBtn.setText("ON");
    }
  }

  /**
   * Toggles the sound effects on or off based on the current state.
   *
   * <p>If the sound effects are currently muted, this method unmutes them and updates the button
   * text to "ON". If they are not muted, it mutes them and updates the button text to "OFF".
   */
  @FXML
  public void toggleSound() {
    SoundManager soundManager = SoundManager.getInstance();
    if (soundManager.isSoundEffectsMuted()) {
      soundManager.unmuteSoundEffects();
      soundBtn.setText("ON");
    } else {
      soundManager.muteSoundEffects();
      soundBtn.setText("OFF");
    }
  }

  /**
   * Toggles the background music on or off based on the current state.
   *
   * <p>If the background music is currently muted, this method unmutes it and updates the button
   * text to "ON". If it is not muted, it mutes the music and updates the button text to "OFF".
   */
  @FXML
  public void toggleMusic() {
    SoundManager soundManager = SoundManager.getInstance();
    if (soundManager.isBackgroundMusicMuted()) {
      soundManager.unmuteBackgroundMusic();
      musicBtn.setText("ON");
    } else {
      soundManager.muteBackgroundMusic();
      musicBtn.setText("OFF");
    }
  }

  @FXML
  public void toggleMarathon() {
    marathonBtn.setSelected(true);
    basicBtn.setSelected(false);
  }

  @FXML
  void toggleBasic() {
    basicBtn.setSelected(true);
    marathonBtn.setSelected(false);
  }

  /**
   * Reads scores from a file and returns them as a list of strings.
   *
   * @param filePath The path to the score file that will be read.
   * @return A list of scores as strings, where each string represents one line from the file.
   */
  private List<String> loadScoresFromFile(String filePath) {
    List<String> scores = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      // reads each line from the file and adds to the score list
      while ((line = reader.readLine()) != null) {
        scores.add(line);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Issue regarding Score file", e);
    }
    return scores;
  }

  /**
   * Closes the game when the close button is clicked
   *
   * <p>This method is linked to the close button. When the button is clicked, the application will
   * terminate
   */
  public void exitGame() {
    System.exit(0);
  }

  public void onSettings() {
    settingsPane.setVisible(true);
    mainPane.setDisable(true);
  }

  public void onDifficulty() {
    difficultyPane.setVisible(true);
    mainPane.setDisable(true);
  }

  public void onSettingsBack() {
    settingsPane.setVisible(false);
    mainPane.setDisable(false);
  }

  public void onDifficultyBack() {
    difficultyPane.setVisible(false);
    mainPane.setDisable(false);
  }

  public void onPastScoreBack(){
    pastScorePane.setVisible(false);
    mainPane.setDisable(false);
  }

  /**
   * Loads and displays the skin shop screen.
   *
   * <p>This method is triggered when the user selects the option to open the shop. It loads the
   * FXML file for the skin shop layout, sets the controller to the `skinShopController`, and then
   * updates the scene to display the skin shop.
   *
   * @throws IOException if there is an issue loading the FXML file for the skin shop.
   */
  @FXML
  public void onShop() throws IOException {
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/skinshop.fxml"));
    loader.setController(skinShopController);
    Parent root = loader.load();
    SceneManager.addScene(AppUI.SKINSHOP, root);
    Main.setUi(AppUI.SKINSHOP);
  }

  /**
   * Starts a new game by loading the game board UI and initialising the game at the selected
   * difficulty level.
   *
   * <p>This method checks if the tutorial has been viewed. If it has, it loads a new game. If not,
   * it loads the tutorial. The game is then started when the tutorial is completed.
   *
   * @throws IOException
   */
  public void startGame() throws IOException {
    if (tutorialController.getHasTutorialBeenViewed()) {
      // Start a new game
      loadGame();
    } else {

      // Tell the turorial that it needs to create a new game when the user is
      // finished
      tutorialController.setCreateGame(true);

      // Load the tutorial
      FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/Tutorial.fxml"));
      loader.setController(tutorialController);
      Parent root = loader.load();
      SceneManager.addScene(AppUI.HOME_TUTORIAL, root);
      Main.setUi(AppUI.HOME_TUTORIAL);

      // Creates the game when the tutorial is completed
      Runnable onTutorialCompleted =
          () -> {
            try {
              loadGame();
            } catch (IOException e) {
              // Do nothing for now
            }
          };
      tutorialController.setOnTutorialCompleted(onTutorialCompleted);
    }
  }

  /**
   * loads a new game by loading the game board UI and initialising the game at the selected
   * difficulty level.
   *
   * <p>This method retrieves the initial difficulty level from the slider of 1-20, loads the game
   * board from the corresponding FXML file, and updates the game board controller with the selected
   * level. It then switches the UI to the game screen.
   *
   * @throws IOException If there is an issue loading the FXML file for the game board.
   */
  @FXML
  public void loadGame() throws IOException {
    // Get the level from the slider
    int initialLevel = (int) difficultySlider.getValue();

    // Calculate target lines based on difficulty level (for Marathon Mode)
    int targetLines =
        calculateTargetLines(initialLevel); // Higher difficulty means more lines to clear

    // loads the game board fxml file
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/GameBoard.fxml"));
    Parent root = loader.load();
    GameBoardController controller = loader.getController();

    // Determine which mode the player selected
    boolean isMarathonMode = marathonBtn.isSelected();

    // Create a GameStateManager implementation to handle the state updates
    GameStateManager gameStateManager = controller;

    // Create a new GameInstance with the state manager
    this.gameInstance = new GameInstance(gameStateManager, isMarathonMode, targetLines);

    // Pass the GameInstance to the controller for reference
    controller.setGameInstance(gameInstance);

    // Set the initial level
    controller.updateLevel(initialLevel);
    SceneManager.addScene(AppUI.GAME, root);
    Main.setUi(AppUI.GAME);
    SoundManager.getInstance().stopBackgroundMusic("mainmenu");
    SoundManager.getInstance().playBackgroundMusic("ingame");

    // Start the game instance
    gameInstance.start();
  }

  /**
   * Handles key press events, focusing the difficulty slider and starting the game if the spacebar
   * is pressed.
   *
   * <p>This method is triggered whenever a key is pressed. It first sets the focus to the
   * difficulty slider. If the spacebar is pressed, it attempts to start the game
   *
   * @param event The key event triggered by the key press.
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    difficultySlider.requestFocus();
    if (event.getCode() == KeyCode.SPACE) {
      try {
        startGame();
      } catch (IOException e) {
        // Do nothing for now
      }
    }
  }

  /**
   * Shows the players past scores in a list view
   *
   * <p>When the button is pressed, a list of the players previous scores that are saved are shown
   * so that the player can check their high scores in the game
   */
  @FXML
public void showPastScores() { 
        mainPane.setDisable(true);
        pastScorePane.setVisible(true);
        MarathonPastScores.setVisible(true);
        basicPastScores.setVisible(true); 
    
}


  /**
   * Opens the tutorial screen when the tutorial button is clicked
   *
   * <p>This method is linked to the tutorial button. When the button is clicked, the application
   * will switch to the tutorial screen
   *
   * @throws IOException
   */
  @FXML
  public void goToTutorial() throws IOException {
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/Tutorial.fxml"));
    loader.setController(tutorialController);
    Parent root = loader.load();
    SceneManager.addScene(AppUI.HOME_TUTORIAL, root);
    Main.setUi(AppUI.HOME_TUTORIAL);
  }

  /**
   * Calculates the target lines for Marathon Mode based on the selected difficulty.
   *
   * @param difficultyLevel The level of difficulty selected on the slider (1-20)
   * @return The target number of lines for Marathon Mode.
   */
  private int calculateTargetLines(int difficultyLevel) {
    return difficultyLevel
        + 1; // Adjust the multiplier as needed, e.g., 10 lines per difficulty level.
  }
/**
     * Handles the click event for showing Basic Mode scores.
     *
     * Loads the past scores for Basic Mode and displays them in the ListView.
     */
    @FXML
    public void onClickBasicMode() {
      
        // Hide the Marathon Mode scores and related image, show Basic Mode scores and related image
        MarathonPastScores.setVisible(false);
        marathonScoreImageView.setVisible(true);
       
        

        basicPastScores.setVisible(true);
        
        basicScoreImageView.setVisible(true);
        basicScoreImageView.toFront();
        basicPastScores.toFront();
        basicPastScores.getItems().clear();
        clickBasic.toFront();
        clickMarathon.toFront();

        // Load and display Basic Mode scores
        loadBasicModeScores();
    }

    /**
     * Handles the click event for showing Marathon Mode scores.
     *
     * Loads the past scores for Marathon Mode and displays them in the ListView.
     */
    @FXML
    public void onClickMarathonMode() {

      System.out.println("Marathon Mode clicked");
        // Hide the Basic Mode scores and related image, show Marathon Mode scores and related image
        basicPastScores.setVisible(false);
        basicScoreImageView.setVisible(true);

        basicScoreImageView.toBack();
       
        MarathonPastScores.setVisible(true);
        marathonScoreImageView.setVisible(true);
        MarathonPastScores.getItems().clear();
        clickBasic.toFront();
        clickMarathon.toFront();

        // Load and display Marathon Mode scores
        loadMarathonModeScores();
    }

    /**
     * Loads scores for Basic Mode into the corresponding ListView.
     */
    private void loadBasicModeScores() {
        try {
            List<Integer> basicScores = ScoreRecorder.getAllScores();
            if (!basicScores.isEmpty()) {
                for (int score : basicScores) {
                  basicPastScores.getItems().add(""+ score);
                }
            } else {
              basicPastScores.getItems().add("No scores available.");
            }
        } catch (IOException e) {
          basicPastScores.getItems().add("Failed to load scores.");
           
        }
    }

    /**
     * Loads scores for Marathon Mode into the corresponding ListView.
     */
    private void loadMarathonModeScores() {
        try {
            List<String> marathonScores = ScoreRecorder.getAllMarathonScores();
            if (!marathonScores.isEmpty()) {
                for (String score : marathonScores) {
                    String[] parts = score.split("\\|");
                    if (parts.length == 3) {
                        int linesCleared = Integer.parseInt(parts[0]);
                        int targetLines = Integer.parseInt(parts[1]);
                        int timeTakenInSeconds = Integer.parseInt(parts[2]);
                        int minutes = timeTakenInSeconds / 60;
                        int seconds = timeTakenInSeconds % 60;
                        String formattedTime = String.format("%02d:%02d", minutes, seconds);

                        MarathonPastScores.getItems().add(String.format("%d/%d                                                                %s", linesCleared, targetLines, formattedTime));
                    }
                }
            } else {
              MarathonPastScores.getItems().add("No scores available.");
            }
        } catch (IOException e) {
          MarathonPastScores.getItems().add("Failed to load scores.");
            
        }
    }
 
}
