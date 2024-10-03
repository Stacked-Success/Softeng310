package com.stackedsuccess.managers;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

import com.stackedsuccess.tetriminos.*;

/**
 * Manages the images for the various Tetrimino shapes and provides methods to retrieve and update them.
 * This class also handles loading default images for Tetriminos and other game elements such as the block and highlight images.
 */
public class TetriminoImageManager {

    private static final String IMAGE_PATH = "file:src/main/resources/images/";
    private static final String IMAGE_EXTENSION = ".png";
    private Image blockImage;
    private Image highlightImage;

    private Map<Class<? extends Tetrimino>, Image> tetriminoImages;

    /**
     * Constructs an instance of TetriminoImageManager and loads the default images for Tetriminos, blocks, and highlights.
     */
    public TetriminoImageManager() {
        tetriminoImages = new HashMap<>();
        loadDefaultImages();
    }

    /**
     * Loads the default images for all Tetrimino shapes, block images, and highlight images.
     * The images are stored in a map, where the key is the Tetrimino class and the value is the corresponding image.
     */
    private void loadDefaultImages() {
        tetriminoImages.put(IShape.class, loadImage(IShape.class.getSimpleName()));
        tetriminoImages.put(JShape.class, loadImage(JShape.class.getSimpleName()));
        tetriminoImages.put(LShape.class, loadImage(LShape.class.getSimpleName()));
        tetriminoImages.put(OShape.class, loadImage(OShape.class.getSimpleName()));
        tetriminoImages.put(SShape.class, loadImage(SShape.class.getSimpleName()));
        tetriminoImages.put(TShape.class, loadImage(TShape.class.getSimpleName()));
        tetriminoImages.put(ZShape.class, loadImage(ZShape.class.getSimpleName()));

        blockImage = new Image(IMAGE_PATH + "block.png", 42, 42, true, false);
        highlightImage = new Image(IMAGE_PATH + "highlight.png", 42, 42, true, false);
    }

    /**
     * Loads an image from the file system based on the provided Tetrimino name.
     * 
     * @param tetriminoName The name of the Tetrimino (corresponding to its class name) to load the image for.
     * @return The Image object representing the Tetrimino's image.
     */
    private Image loadImage(String tetriminoName) {
        return new Image(IMAGE_PATH + tetriminoName + IMAGE_EXTENSION);
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
     * Sets a custom image for a specific Tetrimino class.
     * 
     * @param tetriminoClass The class of the Tetrimino for which to set the image.
     * @param image The custom image to associate with the provided Tetrimino class.
     */
    public void setTetriminoImage(Class<? extends Tetrimino> tetriminoClass, Image image) {
        tetriminoImages.put(tetriminoClass, image);
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
     * Retrieves the highlight image used to show the ghost or shadow of a Tetrimino's landing position.
     * 
     * @return The highlight image.
     */
    public Image getHighlightImage() {
        return highlightImage;
    }
}
