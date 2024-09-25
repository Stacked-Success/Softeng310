package com.stackedsuccess;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

import com.stackedsuccess.tetriminos.*;

public class TetriminoImageManager {

    private static final String IMAGE_PATH = "file:src/main/resources/images/";
    private static final String IMAGE_EXTENSION = ".png";
    private Image blockImage;
    private Image highlightImage;

    private Map<Class<? extends Tetrimino>, Image> tetriminoImages;

    public TetriminoImageManager() {
        tetriminoImages = new HashMap<>();
        loadDefaultImages();
    }

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

    private Image loadImage(String tetriminoName) {
        return new Image(IMAGE_PATH + tetriminoName + IMAGE_EXTENSION);
    }

    public Image getTetriminoImage(Class<? extends Tetrimino> tetriminoClass) {
        return tetriminoImages.get(tetriminoClass);
    }

    public void setTetriminoImage(Class<? extends Tetrimino> tetriminoClass, Image image) {
        tetriminoImages.put(tetriminoClass, image);
    }

    public Image getBlockImage() {
        return blockImage;
    }

    public Image getHighlightImage() {
        return highlightImage;
    }
}
