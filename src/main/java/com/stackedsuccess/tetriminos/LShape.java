package com.stackedsuccess.tetriminos;

public class LShape extends Tetrimino {
  public static final int SPAWN_Value = 3;

  public LShape() {
    layout =
        new int[][] {
          {0, 0, SPAWN_Value},
          {SPAWN_Value, SPAWN_Value, SPAWN_Value},
          {0, 0, 0}
        };
    width = 3;
    height = 3;
    xPos = Tetrimino.defaultSpawnX;
    yPos = Tetrimino.defaultSpawnY;
  }
}
