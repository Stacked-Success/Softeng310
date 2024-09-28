package com.stackedsuccess.soundTest;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import com.stackedsuccess.managers.sound.MediaPlayerWrapperImpl;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class MediaPlayerWrapperTest {

    private MediaPlayerWrapperImpl mediaPlayerWrapper;
    private static final String TEST_MEDIA = "/sounds/mainmenu.wav";

    @BeforeEach
    void setUp() {
        URL resourceUrl = getClass().getResource(TEST_MEDIA);
        assertNotNull(resourceUrl, "Test media file should exist");

        Media media = new Media(resourceUrl.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayerWrapper = new MediaPlayerWrapperImpl(mediaPlayer);
    }

    @Test
    void testPlay() {
        assertDoesNotThrow(() -> mediaPlayerWrapper.play());
    }

    @Test
    void testPause() {
        mediaPlayerWrapper.play();
        assertDoesNotThrow(() -> mediaPlayerWrapper.pause());
    }

    @Test
    void testStop() {
        mediaPlayerWrapper.play();
        assertDoesNotThrow(() -> mediaPlayerWrapper.stop());
    }

    @Test
    void testPlay_whenMediaPlayerIsNull() {
        MediaPlayerWrapperImpl nullMediaPlayerWrapper = new MediaPlayerWrapperImpl(null);
        assertThrows(NullPointerException.class, nullMediaPlayerWrapper::play);
    }

    @Test
    void testPause_whenMediaPlayerIsNull() {
        MediaPlayerWrapperImpl nullMediaPlayerWrapper = new MediaPlayerWrapperImpl(null);
        assertThrows(NullPointerException.class, nullMediaPlayerWrapper::pause);
    }

    @Test
    void testStop_whenMediaPlayerIsNull() {
        MediaPlayerWrapperImpl nullMediaPlayerWrapper = new MediaPlayerWrapperImpl(null);
        assertThrows(NullPointerException.class, nullMediaPlayerWrapper::stop);
    }

    @Test
    void testPlay_whenMediaFileDoesNotExist() {
        String invalidMediaPath = "/sounds/nonExistentMedia.wav";
        URL resourceUrl = getClass().getResource(invalidMediaPath);
        assertNull(resourceUrl, "Resource should be null if the file does not exist");

        if (resourceUrl == null) {
            assertThrows(IllegalArgumentException.class, () -> {
                Media nonExistentMedia = new Media("");
                MediaPlayer nonExistentMediaPlayer = new MediaPlayer(nonExistentMedia);
                MediaPlayerWrapperImpl nonExistentMediaPlayerWrapper = new MediaPlayerWrapperImpl(nonExistentMediaPlayer);
                nonExistentMediaPlayerWrapper.play();
            });
        }
        
    }
     @Test
    void testSetVolume() {
        double expectedVolume = 0.5;
        mediaPlayerWrapper.setVolume(expectedVolume);
        assertEquals(expectedVolume, mediaPlayerWrapper.getVolume(), 0.01);
    }
}
