package com.stackedsuccess.managers;

import com.stackedsuccess.tetriminos.*;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import java.util.prefs.Preferences;

public class TetriminoImageManager {

    private static TetriminoImageManager instance;
    private static final String DEFAULT_SKIN = "DefaultSkin";
    private static final String PREF_SKIN_KEY = "selectedSkin";
    
    private String imagePath = "file:src/main/resources/images/";
    private static final String IMAGE_EXTENSION = ".png";
  
    private Image blockImage;
    private Image highlightImage;
    private Map<Class<? extends Tetrimino>, Image> tetriminoImages;
  
    private Preferences prefs;
  
    /**
     * Private constructor for TetriminoImageManager to implement singleton pattern.
     * Loads the saved skin theme from preferences or defaults to the "DefaultSkin".
     */
    private TetriminoImageManager() {
        tetriminoImages = new HashMap<>();
        prefs = Preferences.userNodeForPackage(TetriminoImageManager.class);
        String savedSkin = prefs.get(PREF_SKIN_KEY, DEFAULT_SKIN);
        setSkinTheme(savedSkin);
    }
  
    /**
     * Gets the singleton instance of TetriminoImageManager.
     * 
     * @return the single instance of TetriminoImageManager
     */
    public static TetriminoImageManager getInstance() {
        if (instance == null) {
            instance = new TetriminoImageManager();
        }
        return instance;
    }
  
    /**
     * Sets the skin theme for the game. This will change the images used for all Tetrimino shapes
     * and save the selected skin to preferences.
     *
     * @param themeName the name of the skin theme to apply (e.g., "DefaultSkin", "TNTSkin")
     */
    public void setSkinTheme(String themeName) {
        this.imagePath = "file:src/main/resources/images/" + themeName + "/";
        prefs.put(PREF_SKIN_KEY, themeName);
        loadDefaultImages();
    }

    /**
     * Gets the currently selected skin theme from preferences.
     * 
     * @return the name of the currently selected skin theme
     */
    public String getCurrentSkin() {
        return prefs.get(PREF_SKIN_KEY, DEFAULT_SKIN);
    }
  
    /**
     * Loads the image for a specific Tetrimino shape based on its class name.
     * 
     * @param tetriminoName the name of the Tetrimino shape (e.g., "IShape", "TShape")
     * @return the Image object for the specified Tetrimino
     */
    private Image loadImage(String tetriminoName) {
        System.out.println("Loading image for: " + tetriminoName);
        return new Image(imagePath + tetriminoName + IMAGE_EXTENSION);
    }
  
     /**
     * Gets the image for the block in the selected skin.
     * 
     * @return the Image object representing the block in the selected skin
     */
    public Image getBlockImage() {
        return blockImage;
    }
  
    /**
     * Gets the image for the highlight in the selected skin.
     * 
     * @return the Image object representing the highlight in the selected skin
     */
    public Image getHighlightImage() {
        return highlightImage;
    }
  
    /**
     * Gets the image for a specific Tetrimino shape.
     * 
     * @param tetriminoClass the class of the Tetrimino shape (e.g., IShape.class, TShape.class)
     * @return the Image object for the specified Tetrimino shape
     */
    public Image getTetriminoImage(Class<? extends Tetrimino> tetriminoClass) {
        return tetriminoImages.get(tetriminoClass);
    }
  
    /**
     * Loads the default images for all Tetrimino shapes and stores them in the tetriminoImages map.
     * Also loads the block and highlight images for the selected skin.
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
