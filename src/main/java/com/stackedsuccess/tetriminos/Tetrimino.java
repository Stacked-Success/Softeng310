package com.stackedsuccess.tetriminos;

import com.stackedsuccess.Action;
import com.stackedsuccess.GameBoard;

public abstract class Tetrimino {
  protected int[][] layout;
  protected int width;
  protected int height;

  private boolean hasHardDropped = false;

  protected int xPos;
  protected int yPos;


  /**
   * Updates tetrimino based on given action and game board state.
   *
   * @param gameBoard the current game board
   * @param action the movement action on the tetrimino
   */
  public void updateTetrimino(GameBoard gameBoard, Action action) {

    switch (action) {
      case MOVE_LEFT:
        if (!hasHardDropped && !gameBoard.checkCollision(xPos - 1, yPos)) xPos--;
        break;
      case MOVE_RIGHT:
        if (!hasHardDropped && !gameBoard.checkCollision(xPos + 1, yPos)) xPos++;
        break;
      case MOVE_DOWN:
        if (!gameBoard.checkCollision(xPos, yPos + 1)) yPos++;
        break;
      case ROTATE_CLOCKWISE:
        if(!hasHardDropped){ rotateClockwise(gameBoard);}
        break;
      case ROTATE_COUNTERCLOCKWISE:
        if(!hasHardDropped) {rotateCounterClockwise(gameBoard);}
        break;
      case HARD_DROP:
        while (!gameBoard.checkCollision(xPos, yPos + 1)) yPos++;
        hasHardDropped = true;
        break;
      default:
        return;
    }
    // Calculate ghost position
    int ghostY = calculateGhostY(gameBoard);
    gameBoard.getController().updateGhostBlock(this, ghostY);
  }

  /**
   * Get-type function.
   *
   * @return tetrimino layout as 2D integer array
   */
  public int[][] getTetriminoLayout() {
    return layout;
  }

  /**
   * Get-type function.
   *
   * @return tetrimino width
   */
  public int getWidth() {
    return width;
  }

  /**
   * Get-type function.
   *
   * @return tetrimino height
   */
  public int getHeight() {
    return height;
  }

  /**
   * Calculates the y position of the ghost tetrimino.
   *
   * @param gameBoard the current game board
   * @return the y position of the ghost tetrimino
   */
  public int calculateGhostY(GameBoard gameBoard) {
    int ghostY = yPos;
    while (!gameBoard.checkCollision(xPos, ghostY + 1)) {
      ghostY++;
    }
    return ghostY;
  }

  /** Rotate tetrimino layout clockwise. */
  private void rotateClockwise(GameBoard gameBoard) {
    int[][] rotatedLayout = getRotatedLayout();
    int[] adjustedPosition = adjustPositionForBounds(rotatedLayout, gameBoard);
    int newX = adjustedPosition[0];
    int newY = adjustedPosition[1];

    if (!hasCollisions(rotatedLayout, newX, newY, gameBoard)) {
      xPos = newX;
      yPos = newY;
      layout = rotatedLayout;
    }
  }

  /** Rotate tetrimino layout counter-clockwise. */
  private int[][] getRotatedLayout() {
    int[][] rotatedLayout = new int[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rotatedLayout[width - j - 1][i] = layout[i][j];
      }
    }
    return rotatedLayout;
  }

  /** Adjust rotation of tetrimino if it would have gone out of bounds */
  private int[] adjustPositionForBounds(int[][] rotatedLayout, GameBoard gameBoard) {
    int newX = xPos;
    int newY = yPos;

    for (int i = 0; i < rotatedLayout.length; i++) {
      for (int j = 0; j < rotatedLayout[i].length; j++) {
        if (rotatedLayout[i][j] != 0) {
          int[] adjustedPosition = adjustForSingleCell(xPos + j, yPos + i, gameBoard);
          newX = adjustedPosition[0];
          newY = adjustedPosition[1];
        }
      }
    }
    return new int[] {newX, newY};
  }

  /** Helper method to adjust position for a single cell if it is out of bounds */
  private int[] adjustForSingleCell(int tempX, int tempY, GameBoard gameBoard) {
    int newX = xPos;
    int newY = yPos;

    if (gameBoard.isOutOfBounds(tempX, tempY)) {
      if (tempX < 0) newX++;
      if (tempX >= gameBoard.getWidth()) newX--;
      if (tempY < 0) newY++;
      if (tempY >= gameBoard.getHeight()) newY--;
    }

    return new int[] {newX, newY};
  }

  /** Check if rotated tetrimino layout has collisions with existing cells on the game board */
  private boolean hasCollisions(int[][] rotatedLayout, int newX, int newY, GameBoard gameBoard) {
    for (int i = 0; i < rotatedLayout.length; i++) {
      for (int j = 0; j < rotatedLayout[i].length; j++) {
        if (rotatedLayout[i][j] != 0) {
          int tempX = newX + j;
          int tempY = newY + i;
          if (gameBoard.isCellOccupied(tempX, tempY)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /** Rotate tetrimino layout counter-clockwise. */
  private void rotateCounterClockwise(GameBoard gameBoard) {
    for (int i = 0; i < 3; i++) rotateClockwise(gameBoard);
  }

  /**
   * Get-type function.
   *
   * @return x position of the tetrimino
   */
  public int getXPos() {
    return xPos;
  }

  /**
   * Get-type function.
   *
   * @return y position of the tetrimino
   */
  public int getYPos() {
    return yPos;
  }

  /**
   * Set-type function.
   *
   * @param xPos the x position to set
   */
  public void setXPos(int xPos) {
    this.xPos = xPos;
  }

  /**
   * Set-type function.
   *
   * @param yPos the y position to set
   */
  public void setYPos(int yPos) {
    this.yPos = yPos;
  }

  public boolean getHasHardDropped() {
    return hasHardDropped;
  }

}
