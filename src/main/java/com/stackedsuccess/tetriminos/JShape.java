package com.stackedsuccess.tetriminos;

public class JShape extends Tetrimino {
  public static final int SPAWN_Value = 2;

  public JShape() {
    layout =
        new int[][] {
          {SPAWN_Value, 0, 0},
          {SPAWN_Value, SPAWN_Value, SPAWN_Value},
          {0, 0, 0}
        };
    width = 3;
    height = 3;
    xPos = Tetrimino.defaultSpawnX;
    yPos = Tetrimino.defaultSpawnY;
  }
}
