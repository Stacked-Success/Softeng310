package com.stackedsuccess.tetriminos;

public class IShape extends Tetrimino {
  public static final int SPAWN_VALUE = 1;

  public IShape() {
      super(new int[][] {
          {0, 0, 0, 0},
          {SPAWN_VALUE, SPAWN_VALUE, SPAWN_VALUE, SPAWN_VALUE},
          {0, 0, 0, 0},
          {0, 0, 0, 0}
      }, 4, 4);
  }
}
