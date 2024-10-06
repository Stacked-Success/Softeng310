package com.stackedsuccess;

import com.stackedsuccess.managers.TetriminoImageManager;
import com.stackedsuccess.tetriminos.*;
import javafx.scene.image.Image;
import javafx.application.Platform;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class TetriminoImageManagerTest {

    private TetriminoImageManager imageManager;

     @BeforeAll
    public static void initToolkit() {
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() {
        imageManager = TetriminoImageManager.getInstance();
    }

    @Test
    public void testGetInstance() {
        TetriminoImageManager instance1 = TetriminoImageManager.getInstance();
        TetriminoImageManager instance2 = TetriminoImageManager.getInstance();
        assertSame(instance1, instance2, "The TetriminoImageManager instance should be a singleton.");
    }

    @Test
    public void testSetSkinTheme() {
        String skinName = "NewSkin";
        imageManager.setSkinTheme(skinName);

        String savedSkin = imageManager.getCurrentSkin();
        assertEquals(skinName, savedSkin, "The skin name should be saved and returned by getCurrentSkin().");
    }

    @Test
    public void testLoadImagesForTetriminoShapes() {
        imageManager.setSkinTheme("DefaultSkin");

        assertNotNull(imageManager.getTetriminoImage(IShape.class), "Image for IShape should be loaded.");
        assertNotNull(imageManager.getTetriminoImage(JShape.class), "Image for JShape should be loaded.");
        assertNotNull(imageManager.getTetriminoImage(LShape.class), "Image for LShape should be loaded.");
        assertNotNull(imageManager.getTetriminoImage(OShape.class), "Image for OShape should be loaded.");
        assertNotNull(imageManager.getTetriminoImage(SShape.class), "Image for SShape should be loaded.");
        assertNotNull(imageManager.getTetriminoImage(TShape.class), "Image for TShape should be loaded.");
        assertNotNull(imageManager.getTetriminoImage(ZShape.class), "Image for ZShape should be loaded.");
    }

    @Test
    public void testGetBlockImage() {
        Image blockImage = imageManager.getBlockImage();
        assertNotNull(blockImage, "Block image should be loaded.");
    }

    @Test
    public void testGetHighlightImage() {
        Image highlightImage = imageManager.getHighlightImage();
        assertNotNull(highlightImage, "Highlight image should be loaded.");
    }

    @Test
    public void testReadSkinFromFile() throws IOException {
        String expectedSkin = "TestSkin";
        Files.writeString(Paths.get("skinSelection.txt"), expectedSkin);

        String savedSkin = imageManager.getCurrentSkin();
        assertEquals(expectedSkin, savedSkin, "The skin name should be read from the file.");
        
        // Cleanup
        Files.deleteIfExists(Paths.get("skinSelection.txt"));
    }

    @Test
    public void testSaveSkinToFile() throws IOException {
        String skinName = "TestSkin";

        imageManager.setSkinTheme(skinName);

        File skinFile = new File("skinSelection.txt");
        assertTrue(skinFile.exists(), "Skin file should be created after setting the skin theme.");
        
        String savedSkin = Files.readString(Paths.get("skinSelection.txt"));
        assertEquals(skinName, savedSkin.trim(), "The skin file should contain the saved skin name.");
        
        Files.deleteIfExists(Paths.get("skinSelection.txt"));
    }
}
