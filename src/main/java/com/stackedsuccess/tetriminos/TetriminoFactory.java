package com.stackedsuccess.tetriminos;

import java.util.Random;

public class TetriminoFactory {

  private static final Random random = new Random();

  // Private constructor to prevent instantiation
  private TetriminoFactory() {
    throw new IllegalStateException();
  }

  /**
   * Factory to produce random tetrimino object.
   *
   * @return tetrimino object
   */
  public static Tetrimino createRandomTetrimino() {
    int type = random.nextInt(7) + 1;
    return switch (type) {
      case IShape.SPAWN_Value -> new IShape();
      case JShape.SPAWN_Value -> new JShape();
      case LShape.SPAWN_Value -> new LShape();
      case OShape.SPAWN_Value -> new OShape();
      case SShape.SPAWN_Value -> new SShape();
      case TShape.SPAWN_Value -> new TShape();
      case ZShape.SPAWN_Value -> new ZShape();
      default -> throw new AssertionError();
    };
  }
}
