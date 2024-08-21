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
      case IShape.spawnValue -> new IShape();
      case JShape.spawnValue -> new JShape();
      case LShape.spawnValue -> new LShape();
      case OShape.spawnValue -> new OShape();
      case SShape.spawnValue -> new SShape();
      case TShape.spawnValue -> new TShape();
      case ZShape.spawnValue -> new ZShape();
      default -> throw new AssertionError();
    };
  }
}
