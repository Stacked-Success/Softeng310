package com.stackedsuccess.controllers;

import com.stackedsuccess.GameInstance;
import com.stackedsuccess.ScoreRecorder;
import com.stackedsuccess.tetriminos.Tetrimino;
import java.io.IOException;
import java.util.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameBoardController implements GameInstance.TetriminoUpdateListener {

  @FXML Pane basePane;
  @FXML Pane holdPiece;
  @FXML GridPane gameGrid;
  @FXML GridPane displayGrid;

  @FXML Label scoreLabel;
  @FXML Label levelLabel;
  @FXML Label lineLabel;

  @FXML ImageView holdPieceView;
  @FXML ImageView nextPieceView;

  private GameInstance gameInstance = new GameInstance();
  private int score = 0;
  private int line = 0;
  private ArrayList<Node> previousGhostTetrominos = new ArrayList<>();

  /**
   * Initialises the game board controller, setting up the game grid and starting the game instance.
   *
   * <p>Set everything to default, and start the game instance while also setting listeners for
   * tetrimino updates and setting the game board controller to the current instance.
   */
  @FXML
  public void initialize() {

    scoreLabel.setText("Score:" + score);
    levelLabel.setText("Level: 1");
    lineLabel.setText("Line: " + line);
    displayGrid.gridLinesVisibleProperty().set(true);
    gameInstance.setTetriminoUpdateListener(this);
    Platform.runLater(
        () -> {
          gameInstance.start();
          gameInstance.getGameBoard().setController(this); // Set the controller
          setWindowCloseHandler(getStage());
        });
  }

  /**
   * Listener for tetrimino updates, updates the game grid with the new tetrimino layout.
   *
   * @param tetrimino the tetrimino that is currently on the board
   */
  @Override
  public void onTetriminoUpdate(Tetrimino tetrimino) {
    Platform.runLater(() -> renderTetrimino(tetrimino));
  }

  /**
   * Displays on the game grid where the tetrimino is located. Sets the background color of the grid
   * to be black where the tetrimino is located.
   *
   * @param tetrimino the tetrimino to be displayed on the grid
   */
  @FXML
  private void renderTetrimino(Tetrimino tetrimino) {
    gameGrid.getChildren().clear(); // Clear previous tetrimino
    gameGrid.gridLinesVisibleProperty().setValue(true);

    int[][] layout = tetrimino.getTetriminoLayout();
    for (int row = 0; row < layout.length; row++) {
      for (int col = 0; col < layout[row].length; col++) {
        if (layout[row][col] != 0) {
          Pane pane = new Pane();
          pane.setStyle(getTetriminoStyle(tetrimino));
          gameGrid.add(pane, tetrimino.getXPos() + col, tetrimino.getYPos() + row);
        }
      }
    }
  }

  /**
   * Updates the display grid above the game grid with position of the static tetriminos.
   *
   * @param tetrimino the tetrimino to be displayed on the grid
   */
  @FXML
  public void updateDisplayGrid(Tetrimino tetrimino) {
    Platform.runLater(
        () -> {
          int[][] layout = tetrimino.getTetriminoLayout();
          for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
              if (layout[row][col] != 0) {
                Pane pane = new Pane();
                pane.setStyle(getTetriminoStyle(tetrimino));
                displayGrid.add(pane, tetrimino.getXPos() + col, tetrimino.getYPos() + row);
              }
            }
          }
        });
  }

  /**
   * Clears the line at the given index and shifts all rows above downwards. Also increments the
   * score and level if needed.
   *
   * @param lineIndex the index of the line to be cleared
   */
  @FXML
  public void clearLine(int lineIndex) {
    Platform.runLater(
        () -> {
          displayGrid
              .getChildren()
              .removeIf(
                  node -> {
                    Integer rowIndex = GridPane.getRowIndex(node);
                    return rowIndex != null && rowIndex.intValue() == lineIndex;
                  });

          // Shift rows down
          for (Node node : displayGrid.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            if (rowIndex != null && rowIndex < lineIndex) {
              GridPane.setRowIndex(node, rowIndex + 1);
            }
          }
        });
  }

  /**
   * Updates the ghost block on the display grid.
   *
   * @param tetrimino the tetrimino to be displayed as a ghost block
   * @param ghostY the y position of the ghost block
   */
  @FXML
  public void updateGhostBlock(Tetrimino tetrimino, int ghostY) {
    Platform.runLater(
        () -> {
          // Clear previous ghost block
          displayGrid.getChildren().removeAll(previousGhostTetrominos);
          previousGhostTetrominos.clear();

          // Check for overlap
          if (isOverlapping(tetrimino, ghostY)) {
            return; // Do not display ghost block if overlapping
          }

          int[][] layout = tetrimino.getTetriminoLayout();
          for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
              if (layout[row][col] != 0) {
                Pane pane = new Pane();
                pane.setStyle("-fx-background-color: lightgrey;");
                displayGrid.add(pane, tetrimino.getXPos() + col, ghostY + row);
                previousGhostTetrominos.add(pane);
              }
            }
          }
        });
  }

  /**
   * Checks if the tetrimino is overlapping with the ghost block.
   *
   * @param tetrimino the tetrimino to be checked for overlap
   * @param ghostY the y position of the ghost block
   * @return true if the tetrimino is overlapping with the ghost block, false otherwise
   */
  @FXML
  private boolean isOverlapping(Tetrimino tetrimino, int ghostY) {
    int[][] layout = tetrimino.getTetriminoLayout();
    for (int row = 0; row < layout.length; row++) {
      for (int col = 0; col < layout[row].length; col++) {
        if (layout[row][col] != 0) {
          int blockY = tetrimino.getYPos() + row;
          if (blockY == ghostY + row) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Sends the key pressed event to game instance to utilise.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    gameInstance.handleInput(event);
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
   * Updates the score displayed on the game board.
   *
   * @param score the current score
   */
  public void updateScore(int score) {
    Platform.runLater(() -> scoreLabel.setText("Score: " + score));
  }

  /**
   * Updates the line displayed on the game board.
   *
   * @param line the current level
   */
  public void updateLine(int line) {
    Platform.runLater(() -> lineLabel.setText("Line: " + line));
  }

  /**
   * Updates the level displayed on the game board.
   *
   * @param level the current level
   */
  public void updateLevel(int level) {
    Platform.runLater(() -> levelLabel.setText("Level: " + level));
  }

  /**
   * Sets the view of the next tetromino to be loaded.
   *
   * @param tetrimino the tetrimino to be displayed in the next piece view
   */
  public void setNextPieceView(Tetrimino tetrimino) {
    Image image = new Image("/images/" + tetrimino.getClass().getSimpleName() + ".png");
    nextPieceView.setImage(image);
  }

  /**
   * Sets the view of the hold tetromino to be loaded.
   *
   * @param tetrimino the tetrimino to be displayed in the hold image view
   */
  public void setHoldPieceView(Tetrimino tetrimino) {
    Image image = new Image("/images/" + tetrimino.getClass().getSimpleName() + ".png");
    holdPieceView.setImage(image);
  }

  /** Method for checking which tetrimino shape is in play, and setting the style accordingly
   * 
   * @param tetrimino the tetrimino to be displayed in the hold image view
   * @return tetriminoStyle the style of the tetrimino
   */
  public String getTetriminoStyle(Tetrimino tetrimino) {
    String tetriminoStyle = "";
    String borderColour = "-fx-border-color: black;";
    String borderThickness = "-fx-border-width: 2px;";
    switch (tetrimino.getClass().getSimpleName()) {
      case "IShape":
        tetriminoStyle = "-fx-background-color: #ff7e00;" + borderColour + borderThickness;
        break;
      case "JShape":
        tetriminoStyle = "-fx-background-color: #2c349c;" + borderColour + borderThickness;
        break;
      case "LShape":
        tetriminoStyle = "-fx-background-color: #ec1c24;" + borderColour + borderThickness;
        break;
      case "OShape":
        tetriminoStyle = "-fx-background-color: #24b44c;" + borderColour + borderThickness;
        break;
      case "SShape":
        tetriminoStyle = "-fx-background-color: #a424f4;" + borderColour + borderThickness;
        break;
      case "TShape":
        tetriminoStyle = "-fx-background-color: #fcf404;" + borderColour + borderThickness;
        break;
      case "ZShape":
        tetriminoStyle = "-fx-background-color: #04b4ec;" + borderColour + borderThickness;
        break;
      default:
        tetriminoStyle = "-fx-background-color: #000000;" + borderColour + borderThickness;
        break;
    }
    return tetriminoStyle;
    }

  /**
   * Method for handling game over event, when tetriminos spawn and collide into each other. Exits
   * the game when called
   *
   * @throws IOException
   */
  @FXML
  public void gameOver() throws IOException {
    // Save score before exiting
    ScoreRecorder.saveScore(scoreLabel.getText());

    System.exit(0);
  }
}
