package com.stackedsuccess.controllers;

import com.stackedsuccess.GameInstance;
import com.stackedsuccess.Main;
import com.stackedsuccess.managers.GameStateManager;
import com.stackedsuccess.managers.SceneManager;
import com.stackedsuccess.managers.TetriminoImageManager;
import com.stackedsuccess.managers.SceneManager.AppUI;
import com.stackedsuccess.managers.sound.SoundManager;
import com.stackedsuccess.ScoreRecorder;
import com.stackedsuccess.tetriminos.*;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameBoardController implements GameStateManager {

  @FXML
  Pane basePane;
  @FXML
  Pane holdPiece;
  @FXML
  GridPane displayGrid;

  @FXML
  Label scoreLabel;
  @FXML
  Label levelLabel;
  @FXML
  Label lineLabel;

  @FXML
  ImageView holdPieceView;
  @FXML
  ImageView nextPieceView;

  @FXML
  Button pauseButton;
  @FXML
  Pane pauseBackground;
  @FXML
  Pane pauseLabelBackground;
  @FXML
  Label pauseLabel;

  @FXML
  VBox gameOverBox;
  @FXML
  Label gameOverLabel;
  @FXML
  Label gameOverScoreLabel;
  @FXML
  Label gameOverHighScoreLabel;
  @FXML
  Button gameOverExitButton;
  @FXML
  Button gameOverRestartButton;
  @FXML
  Button mainMenuBtn;

  @FXML
  VBox timerVbox;
  @FXML
  Label timerLabel;

  @FXML
  private Button tutorialBtn;

  private static final int SOLID_BLOCK_VALUE = -2;

  private int targetLines; // Stores the target number of lines in Marathon Mode
  private boolean isMarathonMode; // Stores whether Marathon Mode is active

  private GameInstance gameInstance;
  private final TetriminoImageManager imageManager;

  private Timeline gameTimer; // JavaFX Timeline to schedule timer updates
  private int elapsedSeconds; // Track the total number of elapsed seconds

  public GameBoardController() {
    imageManager = TetriminoImageManager.getInstance(); 
  }

  /**
   * Set the GameInstance object to be used by this controller.
   *
   * @param gameInstance the GameInstance to control the game logic
   */
  public void setGameInstance(GameInstance gameInstance) {
    this.gameInstance = gameInstance;
    this.isMarathonMode = gameInstance.isMarathonMode(); // Store whether it is Marathon Mode
    this.targetLines = gameInstance.getTargetLines(); // Get the target lines if Marathon Mode is active
  }

  private TutorialController tutorialController;

  /**
   * Initialises the game board controller by setting up the game grid and
   * starting the game instance.
   *
   * <p>
   * This method resets the labels to their default state and requests focus on
   * the base pane.
   * It then starts the game instance, assigns the current controller to the game
   * board, and updates
   * the next piece view with the corresponding image of the upcoming Tetrimino.
   * Additionally, it sets
   * a window close handler for the stage to manage the application closure.
   *
   * <p>
   * This method is annotated with `@FXML` to indicate that it is called during
   * the loading of the
   * FXML file, and it runs initialisation tasks after the FXML components have
   * been loaded.
   */
  @FXML
  public void initialize() {
    // resets score, line, and level to initial state
    resetLabels();
    basePane.requestFocus();

    Platform.runLater(() -> {
      if (gameInstance != null) {
        gameInstance.start();

        // Update next piece view
        nextPieceView
            .setImage(imageManager.getTetriminoImage(gameInstance.getGameBoard().getNextTetrimino().getClass()));

        // Set window close handler
        setWindowCloseHandler(getStage());

        // Initialize lineLabel for Marathon Mode
        if (isMarathonMode) {
          lineLabel.setText("0/" + targetLines);
          // start timer
          startTimer();
        } else {
          timerVbox.setVisible(false);
        }

      } 
    });

    tutorialController = new TutorialController();
    tutorialController.setDestinationAppUI(AppUI.GAME);
    tutorialController.setHasTutorialBeenViewed(true);
  }

  /**
   * Updates the visual display of the game board and the currently moving
   * Tetrimino pieces.
   *
   * <p>
   * This method receives the current state of the game board and updates the
   * display to reflect
   * the position of both stationary and moving pieces. It processes the board to
   * include moving pieces
   * and then updates the visual grid (the game board)
   *
   * @param board the current state of the game board to visualise, where each
   *              cell contains a value
   *              representing the type of block or an empty space
   */

  @FXML
  @Override
  public void updateDisplay(int[][] board) {
    int[][] updatedBoard = addMovingPieces(board);

    Platform.runLater(
        () -> {
          displayGrid.getChildren().clear();

          // iterates through every cell in the grid of the game board to update where the
          // pieces move
          for (int y = 0; y < displayGrid.getRowCount(); y++) {
            for (int x = 0; x < displayGrid.getColumnCount(); x++) {
              int blockValue = updatedBoard[y][x];
              if (blockValue == 0) {
                continue;
              }
              displayGrid.add(getBlock(blockValue), x, y);
            }
          }
        });
  }

  /**
   * Handles the game over event, triggered when a Tetrimino is placed out of
   * bounds.
   *
   * <p>
   * This method sets the game state to "game over," saves the player's score
   * using the
   * `ScoreRecorder`, and plays a game over animation, a piece is out of bounds
   * when it is
   * above the playable screen.
   *
   * @throws IOException if an I/O error occurs during score saving
   */

  @FXML
  @Override
  public void gameOver() throws IOException {
    // Stop the game timer if it is running
    if (gameTimer != null) {
      gameTimer.stop();
    }
    int linesCleared = gameInstance.getGameBoard().getTotalLinesCleared();

    try {
      if (gameInstance.isMarathonMode()) {
          // Save Marathon Mode Score
          int targetLines = gameInstance.getTargetLines();
          int timeTakenInSeconds = elapsedSeconds; // Assuming elapsedSeconds is tracking the timer

          ScoreRecorder.saveMarathonScore(linesCleared, targetLines, timeTakenInSeconds);
      } else {
          // Save Basic Mode Score
          int finalScore = Integer.parseInt(scoreLabel.getText());
          ScoreRecorder.saveScore(String.valueOf(finalScore));

      }
  } catch (IOException e) {
      e.printStackTrace();
  }


    gameInstance.setGameOver(true);
    
    playGameOverAnimation();
    if(isMarathonMode && linesCleared >= targetLines) {
      SoundManager.getInstance().playSoundEffect("victory");
    } else {
      SoundManager.getInstance().playSoundEffect("gameover");
    }
   
    
  }

  /**
   * Handles key press events and sends them to the game instance for processing.
   *
   * <p>
   * This method checks for specific key presses, such as the ESC key to toggle
   * the pause screen,
   * and passes all key events to the game instance to control the board.
   *
   * @param event the key event that was pressed, containing information about
   *              which key was pressed
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    // pauses the game if ESC is called
    if (event.getCode() == KeyCode.ESCAPE) {
      togglePauseScreen();
    }
    // otherwise passes the players input to the game instance
    gameInstance.handleInput(event);
  }

  /**
   * Toggles the display of the pause screen based on the game's current paused
   * state.
   *
   * <p>
   * If the game is currently paused, this method brings the pause screen elements
   * to the front,
   * updates their styles and opacity to make them visible. If the game is not
   * paused, it sends the
   * pause screen elements to the back and makes them transparent.
   */
  private void togglePauseScreen() {
    if (gameInstance.isPaused()) {
      basePane.requestFocus();
      pauseBackground.toBack();
      pauseLabelBackground.toBack();
      pauseBackground.setOpacity(0);
      pauseLabelBackground.setOpacity(0);
      SoundManager.getInstance().resumeBackgroundMusic("ingame");
    } else {
      pauseBackground.toFront();
      pauseLabelBackground.toFront();
      pauseLabel.setStyle("-fx-text-fill: #fdfad0; -fx-font-size: 85px;");
      pauseButton.toFront();
      pauseBackground.setOpacity(0.5);
      pauseLabelBackground.setOpacity(1);
      SoundManager.getInstance().pauseBackgroundMusic("ingame");
    }
  }

  /**
   * Pauses or resumes the game when the pause button is clicked.
   *
   * <p>
   * This method toggles the visibility of the pause screen and changes the game's
   * pause state.
   * If the game is currently running, it will be paused; if it is paused, it will
   * resume.
   */
  @FXML
  public void onClickPauseButton() {
    togglePauseScreen();
    gameInstance.togglePause();
  }

  /**
   * Closes the game when the exit button is clicked
   *
   * <p>
   * This method is linked to the "Exit" button in the user interface. When the
   * button is clicked, the application will terminate
   * </p>
   */
  @FXML
  void onClickExit(ActionEvent event) {
    System.exit(0);
  }

  /**
   * restarts the game when restart is clicked.
   *
   * <p>
   * When the restart button is clicked, it reloads the main menu scene,
   * effectively restarting the application's UI to its initial state.
   * </p>
   *
   * @param event The event triggered by the restart button click.
   * @throws IOException If there is an error while loading the FXML file for the
   *                     home screen.
   */
  @FXML
  void onClickRestart(ActionEvent event) throws IOException {
    SoundManager.getInstance().stopBackgroundMusic("ingame");
    SceneManager.addScene(AppUI.MAIN_MENU, loadFxml("HomeScreen"));
    Main.setUi(AppUI.MAIN_MENU);
    // Stop and reset the timer
    if (gameTimer != null) {
      gameTimer.stop();
      elapsedSeconds = 0;
      updateTimerLabel();
    }
  }

  /**
   * Updates the score displayed on the game board.
   *
   * @param score the current score
   */
  @Override
  public void updateScore(int score) {
    Platform.runLater(() -> scoreLabel.setText(String.valueOf(score)));
  }

  /**
   * Updates the line displayed on the game board.
   *
   * @param line the current level
   */
  @Override
  public void updateLine(int line) {
    Platform.runLater(() -> {
      if (isMarathonMode) {
        lineLabel.setText(line + "/" + targetLines);
        // Check if the player has reached the target number of lines
        if (line >= targetLines) {
          gameInstance.setGameOver(true);
          try {
            gameOver();
          } catch (IOException e) {
            throw new IllegalArgumentException("Issue regarding ScoreReader");
          }
        }
      } else {
        lineLabel.setText(String.valueOf(line));
      }
    });
  }

  /**
   * Updates the level displayed on the game board.
   *
   * @param level the current level
   */
  @Override
  public void updateLevel(int level) {
    Platform.runLater(() -> levelLabel.setText(String.valueOf(level)));
  }

  public void setBaseLevel(int baseLevel) {
    Platform.runLater(() -> gameInstance.getGameBoard().setBaseLevel(baseLevel));
  }

  /**
   * Sets the view of the next tetromino to be loaded.
   *
   * @param tetrimino the tetrimino to be displayed in the next piece view
   */
  @Override
  public void setNextPieceView(Tetrimino tetrimino) {
    Image image = imageManager.getTetriminoImage(tetrimino.getClass());
    nextPieceView.setImage(image);
  }

  /**
   * Sets the view of the hold tetromino to be loaded.
   *
   * @param tetrimino the tetrimino to be displayed in the hold image view
   */
  @Override
  public void setHoldPieceView(Tetrimino tetrimino) {
    Image image = imageManager.getTetriminoImage(tetrimino.getClass());
    holdPieceView.setImage(image);
  }

  /**
   * Adds moving tetrimino piece and ghost piece to game board array to support
   * visualising
   * on-screen.
   *
   * @param board the game board
   * @return the game board including current tetrimino and ghost tetrimino
   */
  private int[][] addMovingPieces(int[][] board) {
    // Create clone to separate from original reference
    int[][] newBoard = new int[board.length][];
    for (int i = 0; i < board.length; i++) {
      newBoard[i] = board[i].clone();
    }

    int[][] updatedBoard = addGhostTetriminoPiece(newBoard);
    return addCurrentTetriminoPosition(updatedBoard);
  }

  /**
   * Adds the position of the current Tetrimino's ghost piece to the game board to
   * support visualisation.
   *
   * <p>
   * The ghost piece is a visual aid that shows where the Tetrimino will land if
   * dropped directly
   * from its current position. This method calculates the ghost piece's position
   * and marks it on
   * the game board
   * </p>
   *
   * @param board The game board to append the ghost piece's position to
   * @return The updated game board with the ghost piece's position added
   */
  private int[][] addGhostTetriminoPiece(int[][] board) {
    Tetrimino currentTetrimino = gameInstance.getGameBoard().getCurrentTetrimino();
    int xPos = currentTetrimino.getXPos();
    int yPosGhost = currentTetrimino.calculateGhostY(gameInstance.getGameBoard());
    int width = currentTetrimino.getWidth();
    int height = currentTetrimino.getHeight();
    int[][] tetriminoLayout = currentTetrimino.getTetriminoLayout();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (tetriminoLayout[y][x] != 0) {
          board[yPosGhost + y][xPos + x] = -1;
        }
      }
    }

    return board;
  }

  /**
   * Add position of current tetrimino piece to board to support visualisation.
   *
   * <p>
   * The current piece is a visual aid to show where your current Tetrimino is,
   * This method calculates where the current piece is and marks it on the game
   * board
   * </p>
   *
   * @param board the game board to append position to
   * @return the updated game board
   */
  private int[][] addCurrentTetriminoPosition(int[][] board) {
    Tetrimino currentTetrimino = gameInstance.getGameBoard().getCurrentTetrimino();
    int xPos = currentTetrimino.getXPos();
    int yPos = currentTetrimino.getYPos();
    int width = currentTetrimino.getWidth();
    int height = currentTetrimino.getHeight();
    int[][] tetriminoLayout = currentTetrimino.getTetriminoLayout();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (tetriminoLayout[y][x] != 0) {
          board[yPos + y][xPos + x] = tetriminoLayout[y][x];
        }
      }
    }
    return board;
  }

  /**
   * Get appropriate block image to display in a single cell of the game board.
   *
   * @param blockValue tetrimino piece value in game board
   * @return ImageView of a game element
   */
  private ImageView getBlock(int blockValue) {
    ImageView tetriminoBlock;
    if (blockValue == -1) {
      tetriminoBlock = new ImageView(imageManager.getHighlightImage());
    } else {
      tetriminoBlock = new ImageView(imageManager.getBlockImage());
      ColorAdjust colorAdjust = new ColorAdjust();

      switch (blockValue) {
        case IShape.SPAWN_VALUE:
          colorAdjust.setHue(-0.5);
          break;
        case JShape.SPAWN_VALUE:
          colorAdjust.setHue(-0.3);
          break;
        case LShape.SPAWN_VALUE:
          colorAdjust.setHue(-0.15);
          break;
        case OShape.SPAWN_VALUE:
          colorAdjust.setHue(0);
          break;
        case SShape.SPAWN_VALUE:
          colorAdjust.setHue(0.15);
          break;
        case TShape.SPAWN_VALUE:
          colorAdjust.setHue(0.3);
          break;
        case ZShape.SPAWN_VALUE:
          colorAdjust.setHue(0.5);
          break;
        case SOLID_BLOCK_VALUE:
          colorAdjust.setSaturation(-1);
          break;
        default:
          throw new IllegalArgumentException("Unknown shape " + blockValue);
      }

      tetriminoBlock.setEffect(colorAdjust);
    }

    return tetriminoBlock;
  }

  /** Reset game scoring labels to default; for initialising the game */
  private void resetLabels() {
    scoreLabel.setText("0");
    levelLabel.setText("1");
    lineLabel.setText("0");
  }

  /** Method for playing game over animation. */
  private void playGameOverAnimation() {
    int rows = displayGrid.getRowCount();
    int cols = displayGrid.getColumnCount();
    Timeline animationTimeline = new Timeline();

    // Fill board with solid blocks
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        final int curRow = row;
        final int curCol = col;
        int delay = (row * 50);
        KeyFrame keyFrame = new KeyFrame(
            Duration.millis(delay),
            event -> {
              displayGrid.add(getBlock(SOLID_BLOCK_VALUE), curCol, curRow);
            });
        animationTimeline.getKeyFrames().add(keyFrame);
      }
    }

    // Add delay before revealing game over elements
    KeyFrame actionsKeyFrame = new KeyFrame(
        Duration.millis(1000),
        event -> {
          enableGameOverElements();
        });
    animationTimeline.getKeyFrames().add(actionsKeyFrame);

    // Remove solid blocks
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        int delay = 2000 + (row * 50);
        int finalRow = row;
        KeyFrame keyFrame = new KeyFrame(
            Duration.millis(delay),
            event -> {
              displayGrid
                  .getChildren()
                  .removeIf(node -> finalRow == GridPane.getRowIndex(node));
            });
        animationTimeline.getKeyFrames().add(keyFrame);
      }
    }
    animationTimeline.setOnFinished(event -> {
      if(isMarathonMode && gameInstance.getGameBoard().getTotalLinesCleared() >= targetLines) {
        SoundManager.getInstance().playSoundEffect("secondVictory");
      } else {
        SoundManager.getInstance().playSoundEffect("secondgameover");
      }
      
      mainMenuBtn.setVisible(true);
    });

    animationTimeline.play();
  }

  /**
   * Handles the visibility of elements related to the game over screen.
   *
   * <p>
   * This method makes the game over UI elements visible and interactive when the
   * game ends.
   * It updates the score and high score labels to display the player's final
   * score and the highest
   * score recorded
   * </p>
   */
  private void enableGameOverElements() {
    gameOverBox.setVisible(true);
    gameOverBox.setDisable(false);
    gameOverExitButton.setDisable(false);
    gameOverRestartButton.setDisable(false);
    gameOverExitButton.setVisible(true);
    gameOverRestartButton.setVisible(true);
    gameOverScoreLabel.setText("Score: " + scoreLabel.getText());
   

    if (gameInstance.isMarathonMode()) {
      int linesCleared = gameInstance.getGameBoard().getTotalLinesCleared();
      int targetLines = gameInstance.getTargetLines();

      if (linesCleared >= targetLines) {
          // Player won Marathon Mode
          gameOverLabel.setText("Victory!");
          gameOverScoreLabel.setText("Score: " + linesCleared + "/" + targetLines);
          gameOverHighScoreLabel.setVisible(false); // Hide high score for winning Marathon Mode
      } else {
          // Player lost Marathon Mode
          gameOverLabel.setText("Game Over");
          gameOverScoreLabel.setText("Score: " + linesCleared + "/" + targetLines);
          try {
              gameOverHighScoreLabel.setText("High Score: " + ScoreRecorder.getHighScore());
          } catch (IOException e) {
              throw new IllegalArgumentException("Issue regarding ScoreReader");
          }
      }
  } else {
      // Basic Mode
      gameOverLabel.setText("Game Over");
      gameOverScoreLabel.setText("Score: " + scoreLabel.getText());
      try {
          gameOverHighScoreLabel.setText("High Score: " + ScoreRecorder.getHighScore());
      } catch (IOException e) {
          throw new IllegalArgumentException("Issue regarding ScoreReader");
      }
  }
  }

  /**
   * Creates event handler to stop the game on window close.
   *
   * @param stage the stage containing the game scene
   */
  private void setWindowCloseHandler(Stage stage) {
    stage.setOnCloseRequest(
        event -> {
          gameInstance.setGameOver(true);

          // TODO: Remove when more scenes added.
          System.exit(0);
        });
  }

  /**
   * Get current stage pane.
   *
   * @return current stage
   */
  private Stage getStage() {
    return (Stage) basePane.getScene().getWindow();
  }

  /**
   * Loads the FXML file and returns the parent node.
   *
   * @param fxml the name of the FXML file (without extension)
   * @return the parent node of the input file
   * @throws IOException if the file is not found
   */
  public static Parent loadFxml(final String fxml) throws IOException {
    return new FXMLLoader(Main.class.getResource("/fxml/" + fxml + ".fxml")).load();
  }

  /**
   * Opens the tutorial screen when the tutorial button is clicked
   *
   * <p>
   * This method is linked to the tutorial button. When the button is clicked, the
   * application will switch to the tutorial screen
   * </p>
   * 
   * @throws IOException
   */
  @FXML
  public void goToTutorial() throws IOException {
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/Tutorial.fxml"));
    loader.setController(tutorialController);
    Parent root = loader.load();
    SceneManager.addScene(AppUI.GAME_TUTORIAL, root);
    Main.setUi(AppUI.GAME_TUTORIAL);
  }

  /**
   * Starts the timer for the game when it's in Marathon Mode.
   * 
   */

  private void startTimer() {
    elapsedSeconds = 0; // Reset elapsed time to zero

    // Create a Timeline to increment the elapsed time every second
    gameTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
      elapsedSeconds++;
      updateTimerLabel();
    }));

    // Set the timeline to continue indefinitely
    gameTimer.setCycleCount(Timeline.INDEFINITE);
    gameTimer.play();
  }

  /**
   * update the timer Label to display the elapsed time.
   */

  private void updateTimerLabel() {
    int minutes = elapsedSeconds / 60;
    int seconds = elapsedSeconds % 60;
    String timeFormatted = String.format("%02d:%02d", minutes, seconds);
    Platform.runLater(() -> timerLabel.setText(timeFormatted));
  }

}