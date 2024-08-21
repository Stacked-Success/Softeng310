package com.stackedsuccess.tetriminos;

public class ZShape extends Tetrimino {
  public static final int SPAWN_Value = 7;

  public ZShape() {
    layout =
        new int[][] {
          {SPAWN_Value, SPAWN_Value, 0},
          {0, SPAWN_Value, SPAWN_Value},
          {0, 0, 0}
        };
    width = 3;
    height = 3;
    xPos = Tetrimino.defaultSpawnX;
    yPos = Tetrimino.defaultSpawnY;
  }
}
