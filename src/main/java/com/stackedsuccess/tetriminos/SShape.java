package com.stackedsuccess.tetriminos;

public class SShape extends Tetrimino {
  public static final int SPAWN_Value = 5;

  public SShape() {
    layout =
        new int[][] {
          {0, SPAWN_Value, SPAWN_Value},
          {SPAWN_Value, SPAWN_Value, 0},
          {0, 0, 0}
        };
    width = 3;
    height = 3;
    xPos = Tetrimino.defaultSpawnX;
    yPos = Tetrimino.defaultSpawnY;
  }
}
