package com.stackedsuccess.tetriminos;

public class JShape extends Tetrimino {
  public static final int spawnValue = 2;

  public JShape() {
    layout =
        new int[][] {
          {spawnValue, 0, 0},
          {spawnValue, spawnValue, spawnValue},
          {0, 0, 0}
        };
    width = 3;
    height = 3;
    xPos = Tetrimino.defaultSpawnX;
    yPos = Tetrimino.defaultSpawnY;
  }
}
