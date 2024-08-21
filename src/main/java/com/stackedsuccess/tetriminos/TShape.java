package com.stackedsuccess.tetriminos;

public class TShape extends Tetrimino {
  public static final int spawnValue = 6;

  public TShape() {
    layout =
        new int[][] {
          {0, spawnValue, 0},
          {spawnValue, spawnValue, spawnValue},
          {0, 0, 0}
        };
    width = 3;
    height = 3;
    xPos = Tetrimino.DEFAULT_SPAWN_X;
    yPos = Tetrimino.DEFAULT_SPAWN_Y;
  }
}
