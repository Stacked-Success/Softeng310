package com.stackedsuccess.tetriminos;

public class ZShape extends Tetrimino {
  public static final int SPAWN_VALUE = 7;

  public ZShape() {
    super(new int[][]{
        {SPAWN_VALUE, SPAWN_VALUE, 0},
        {0, SPAWN_VALUE, SPAWN_VALUE},
        {0, 0, 0}
    }, 3, 3);
}
}
