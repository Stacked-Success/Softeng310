package com.stackedsuccess.tetriminos;

public class OShape extends Tetrimino {
  public static final int SPAWN_VALUE = 4;

  public OShape() {
    super(new int[][]{
        {0, 0, 0, 0},
        {0, SPAWN_VALUE, SPAWN_VALUE, 0},
        {0, SPAWN_VALUE, SPAWN_VALUE, 0},
        {0, 0, 0, 0}
    }, 4, 4);
}
}
