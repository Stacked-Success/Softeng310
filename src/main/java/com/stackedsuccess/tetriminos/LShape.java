package com.stackedsuccess.tetriminos;

public class LShape extends Tetrimino {
  public static final int SPAWN_VALUE = 3;

  public LShape() {
    super(new int[][]{
        {0, 0, SPAWN_VALUE},
        {SPAWN_VALUE, SPAWN_VALUE, SPAWN_VALUE},
        {0, 0, 0}
    }, 3, 3);
}
}
