package com.stackedsuccess.tetriminos;

public class OShape extends Tetrimino {
  public static final int spawnValue = 4;

  public OShape() {
    layout =
        new int[][] {
          {0, 0, 0, 0},
          {0, spawnValue, spawnValue, 0},
          {0, spawnValue, spawnValue, 0},
          {0, 0, 0, 0}
        };
    width = 4;
    height = 4;
    xPos = Tetrimino.defaultSpawnX;
    yPos = Tetrimino.defaultSpawnY - 1;
  }
}
