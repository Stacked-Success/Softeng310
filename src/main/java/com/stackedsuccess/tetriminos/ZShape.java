package com.stackedsuccess.tetriminos;

public class ZShape extends Tetrimino {
  public static final int spawnValue = 7;

  public ZShape() {
    layout =
        new int[][] {
          {spawnValue, spawnValue, 0},
          {0, spawnValue, spawnValue},
          {0, 0, 0}
        };
    width = 3;
    height = 3;
    xPos = Tetrimino.DEFAULT_SPAWN_X;
    yPos = Tetrimino.DEFAULT_SPAWN_Y;
  }
}
