package com.stackedsuccess.tetriminos;

public class IShape extends Tetrimino {
  public static final int spawnValue = 1;

  public IShape() {
    layout =
        new int[][] {
          {0, 0, 0, 0},
          {spawnValue, spawnValue, spawnValue, spawnValue},
          {0, 0, 0, 0},
          {0, 0, 0, 0}
        };
    width = 4;
    height = 4;
    xPos = Tetrimino.defaultSpawnX;
    yPos = Tetrimino.defaultSpawnY - 1;
  }
}
