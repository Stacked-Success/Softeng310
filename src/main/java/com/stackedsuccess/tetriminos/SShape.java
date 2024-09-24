package com.stackedsuccess.tetriminos;

public class SShape extends Tetrimino {
  public static final int SPAWN_VALUE = 5;

  public SShape() {
    super(new int[][]{
        {0, SPAWN_VALUE, SPAWN_VALUE},
        {SPAWN_VALUE, SPAWN_VALUE, 0},
        {0, 0, 0}
    }, 3, 3);
}
}
