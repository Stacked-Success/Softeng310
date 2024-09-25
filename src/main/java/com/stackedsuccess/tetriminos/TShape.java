package com.stackedsuccess.tetriminos;

public class TShape extends Tetrimino {
  public static final int SPAWN_VALUE = 6;

  public TShape() {
    super(new int[][]{
        {0, SPAWN_VALUE, 0},
        {SPAWN_VALUE, SPAWN_VALUE, SPAWN_VALUE},
        {0, 0, 0}
    }, 3, 3);
}
}
