package com.stackedsuccess.managers;

import com.stackedsuccess.tetriminos.*;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

/**
 * Manages the images for the various Tetrimino shapes and provides methods to retrieve and update
 * them. This class also handles loading default images for Tetriminos and other game elements such
 * as the block and highlight images.
 */
public class TetriminoImageManager {

  private static TetriminoImageManager instance;
  private String imagePath = "file:src/main/resources/images/"; // Default path for images
  private static final String IMAGE_EXTENSION = ".png";

  private Image blockImage;
  private Image highlightImage;
  private Map<Class<? extends Tetrimino>, Image> tetriminoImages;

  /**
   * Constructs an instance of TetriminoImageManager and loads the default images for Tetriminos,
   * blocks, and highlights.
   */
  public TetriminoImageManager() {
    tetriminoImages = new HashMap<>();
    loadDefaultImages();
  }

  /**
 * Returns the singleton instance of the TetriminoImageManager.
 * 
 * This method ensures that only one instance of the TetriminoImageManager
 * is created during the application's lifecycle.
 * 
 * @return the singleton instance of {@code TetriminoImageManager}
 */
  public static TetriminoImageManager getInstance() {
    if (instance == null) {
      instance = new TetriminoImageManager();
    }
    return instance;
  }

  /**
   * Switches the skin theme by changing the image path to the new theme directory.
   *
   * @param themeName the name of the skin theme directory (e.g., "TNTSkin", "WaterSkin")
   */
  public void setSkinTheme(String themeName) {
    this.imagePath = "file:src/main/resources/images/" + themeName + "/";
    loadDefaultImages();
  }

  /**
   * Loads an image from the file system based on the provided Tetrimino name.
   *
   * @param tetriminoName The name of the Tetrimino (corresponding to its class name) to load the
   * image for.
   * @return The Image object representing the Tetrimino's image.
   */
  private Image loadImage(String tetriminoName) {
    System.out.println("Loading image for: " + tetriminoName);
    return new Image(imagePath + tetriminoName + IMAGE_EXTENSION);
  }

  /**
   * Retrieves the default block image used for static blocks or general game blocks.
   *
   * @return The block image.
   */
  public Image getBlockImage() {
    return blockImage;
  }

  /**
   * Retrieves the highlight image used to show the ghost or shadow of a Tetrimino's landing
   * position.
   *
   * @return The highlight image.
   */
  public Image getHighlightImage() {
    return highlightImage;
  }

  /**
   * Retrieves the image for the given Tetrimino class.
   *
   * @param tetriminoClass The class of the Tetrimino whose image is to be retrieved.
   * @return The Image associated with the provided Tetrimino class.
   */
  public Image getTetriminoImage(Class<? extends Tetrimino> tetriminoClass) {
    return tetriminoImages.get(tetriminoClass);
  }

  /**
   * Loads the default images for all Tetrimino shapes, block images, and highlight images. The
   * images are stored in a map, where the key is the Tetrimino class and the value is the
   * corresponding image.
   */
  private void loadDefaultImages() {
    System.out.println("Loading images from path: " + imagePath);

    tetriminoImages.put(IShape.class, loadImage(IShape.class.getSimpleName()));
    tetriminoImages.put(JShape.class, loadImage(JShape.class.getSimpleName()));
    tetriminoImages.put(LShape.class, loadImage(LShape.class.getSimpleName()));
    tetriminoImages.put(OShape.class, loadImage(OShape.class.getSimpleName()));
    tetriminoImages.put(SShape.class, loadImage(SShape.class.getSimpleName()));
    tetriminoImages.put(TShape.class, loadImage(TShape.class.getSimpleName()));
    tetriminoImages.put(ZShape.class, loadImage(ZShape.class.getSimpleName()));

    blockImage = new Image(imagePath + "block.png", 42, 42, true, false);
    highlightImage = new Image("file:src/main/resources/images/highlight.png", 42, 42, true, false);
    System.out.println(imagePath);
  }
}
