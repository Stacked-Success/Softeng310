package com.stackedsuccess.tetriminos;

public class JShape extends Tetrimino {
  public static final int SPAWN_VALUE = 2;

  public JShape() {
      super(new int[][] {
          {SPAWN_VALUE, 0, 0},
          {SPAWN_VALUE, SPAWN_VALUE, SPAWN_VALUE},
          {0, 0, 0}
      }, 3, 3);
  }
}
