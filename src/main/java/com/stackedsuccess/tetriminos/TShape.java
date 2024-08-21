package com.stackedsuccess.tetriminos;

public class TShape extends Tetrimino {
  public static final int SPAWN_Value = 6;

  public TShape() {
    layout =
        new int[][] {
          {0, SPAWN_Value, 0},
          {SPAWN_Value, SPAWN_Value, SPAWN_Value},
          {0, 0, 0}
        };
    width = 3;
    height = 3;
    xPos = Tetrimino.defaultSpawnX;
    yPos = Tetrimino.defaultSpawnY;
  }
}
