package com.stackedsuccess.tetriminos;

public class IShape extends Tetrimino {
  public static final int SPAWN_Value = 1;

  public IShape() {
    layout =
        new int[][] {
          {0, 0, 0, 0},
          {SPAWN_Value, SPAWN_Value, SPAWN_Value, SPAWN_Value},
          {0, 0, 0, 0},
          {0, 0, 0, 0}
        };
    width = 4;
    height = 4;
    xPos = Tetrimino.defaultSpawnX;
    yPos = Tetrimino.defaultSpawnY - 1;
  }
}
