package com.stackedsuccess.tetriminos;

public class LShape extends Tetrimino {
  public static final int spawnValue = 3;

  public LShape() {
    layout =
        new int[][] {
          {0, 0, spawnValue},
          {spawnValue, spawnValue, spawnValue},
          {0, 0, 0}
        };
    width = 3;
    height = 3;
    xPos = Tetrimino.DEFAULT_SPAWN_X;
    yPos = Tetrimino.DEFAULT_SPAWN_Y;
  }
}
