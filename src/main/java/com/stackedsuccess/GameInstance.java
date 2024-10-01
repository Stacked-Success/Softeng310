package com.stackedsuccess;

import com.stackedsuccess.managers.GameStateManager;
import com.stackedsuccess.managers.sound.SoundManager;
import com.stackedsuccess.tetriminos.Tetrimino;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.input.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


// This class defines the game instance, controlling the game loop for the current game.
public class GameInstance {

  private boolean isGameOver;
  private int score;

  private GameControls gameControls;
  private GameBoard gameBoard;
  private int gameDelay;

  private boolean isPaused;

  private Tetrimino currentTetrimino;
  private TetriminoUpdateListener tetriminoUpdateListener;
  private final GameStateManager gameStateManager;

  private boolean isMarathonMode; 
  private int targetLines;

  private Timer timer;

  private ScheduledExecutorService scheduler;
  private ScheduledFuture<?> scheduledTask;


  

  /**
   * Constructs a new instance of the game with default initial settings.
   *
   * <p>This constructor initialises the game state by setting the initial score,
   * game delay, and flags for pause and game over status</p>
   */
  public GameInstance(GameStateManager gameStateManager, boolean isMarathonMode, int targetLines) {
    score = 0;
    gameDelay = 10;
    isPaused = false;
    isGameOver = false;
    this.gameStateManager = gameStateManager;
    this.isMarathonMode = isMarathonMode; 
    this.targetLines = targetLines;
  }

  /**
   * An interface for listening to updates of a Tetrimino.
   */
  public interface TetriminoUpdateListener {
    void onTetriminoUpdate(Tetrimino tetrimino);
  }

  /**
   * Sets the listener for Tetrimino updates
   *
   * @param listener The TetriminoUpdateListener to be notified of updates.
   */
  public void setTetriminoUpdateListener(TetriminoUpdateListener listener) {
    this.tetriminoUpdateListener = listener;
  }

  /**
   * Notifies the registered listener that the current Tetrimino has been updated
   *
   * <p>This method is called whenever the Tetrimino's state changes. It checks if a
   * listener has been registered, and if so, it triggers the listener's
   * onTetriminoUpdate() method, passing the updated Tetrimino as an argument.</p>
   */
  private void notifyTetriminoUpdate() {
    if (tetriminoUpdateListener != null) {
      tetriminoUpdateListener.onTetriminoUpdate(currentTetrimino);
    }
  }

  /**
   * Starts the game instance to periodically update the game board and handle game movement.
   *
   * <p>This method initialises the game board, retrieves the current Tetrimino, and sets up the
   * game controls. It also creates a timer that periodically triggers the game loop, which updates
   * the game state, handles Tetrimino movements, and checks whether the game is paused or over.</p>
   */
  public void start() {
    gameBoard = new GameBoard(gameStateManager, this);
    currentTetrimino = gameBoard.getCurrentTetrimino();
    gameControls = new GameControls();

    // Create a ScheduledExecutorService to regularly update the game loop when not paused
    scheduler = Executors.newScheduledThreadPool(1);
    scheduledTask = scheduler.scheduleAtFixedRate(() -> {
        if (!isPaused && !isGameOver) {
            try {
                gameBoard.update();
            } catch (IOException e) {
                System.err.println("Failed to update game board: " + e.getMessage());
                e.printStackTrace();
            }
            currentTetrimino = gameBoard.getCurrentTetrimino();
            notifyTetriminoUpdate();
        } else {
            System.out.println("Game is either paused or over, skipping update.");
        }
    }, 0, gameDelay, TimeUnit.MILLISECONDS);
}

  /**
   * Handles the key events for current game based on set controls.
   *
   * @param key the KeyEvent triggered by user in game window
   */
  public void handleInput(KeyEvent key) {
    Action action = gameControls.getAction(key);
    if (action != null) {
      if (updatesTetrimino(action) && !isPaused) {
        gameBoard.getCurrentTetrimino().updateTetrimino(gameBoard, action);
        playSoundForAction(action);
      } else {
        if (action == Action.PAUSE) {
          togglePause();
        } else if (action == Action.HOLD) {
          gameBoard.holdTetrimino();
          SoundManager.getInstance().playSoundEffect("hold");
        }
      }
    }
  }

   /**
   * Plays the appropriate sound effect based on the action performed.
   *
   * <p>This method plays different sound effects depending on the action, such as
   * rotating or dropping the Tetrimino.</p>
   *
   * @param action the action for which to play a sound effect
   */
  private void playSoundForAction(Action action) {
    switch (action) {
        case ROTATE_CLOCKWISE:
        case ROTATE_COUNTERCLOCKWISE:
        SoundManager.getInstance().playSoundEffect("rotate");
            break;
        case HARD_DROP:
        SoundManager.getInstance().playSoundEffect("drop");
            break;
        default:
            break;
    }
}

  /** Toggles the game to be paused, halting game updates. */
  public void togglePause() {
    if (!isGameOver) isPaused = !isPaused;
  }

  /**
   * Check if the given Action will update tetrimino.
   *
   * @param action the action to check
   * @return whether action will update tetrimino or not
   */
  private boolean updatesTetrimino(Action action) {
    return action == Action.MOVE_LEFT
        || action == Action.MOVE_RIGHT
        || action == Action.MOVE_DOWN
        || action == Action.HARD_DROP
        || action == Action.ROTATE_CLOCKWISE
        || action == Action.ROTATE_COUNTERCLOCKWISE;
  }

  /**
   * Get the current game board.
   *
   * @return current game board
   */
  public GameBoard getGameBoard() {
    return gameBoard;
  }

  /**
   * Get t