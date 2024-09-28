package com.stackedsuccess.soundTest;

import javafx.scene.media.AudioClip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.stackedsuccess.managers.sound.AudioClipWrapperImpl;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class AudioClipWrapperTest {

    private AudioClipWrapperImpl audioClipWrapper;
    private static final String TEST_MEDIA = "/sounds/drop.wav";


    @BeforeEach
    void setUp() {
        URL resourceUrl = getClass().getResource(TEST_MEDIA);
        assertNotNull(resourceUrl, "Test media file should exist");

        AudioClip realAudioClip = new AudioClip(resourceUrl.toString());
        audioClipWrapper = new AudioClipWrapperImpl(realAudioClip);
    }

    @Test
    void testPlay() {
        assertDoesNotThrow(() -> audioClipWrapper.play());
    }

    @Test
    void testPlay_whenAudioClipIsNull() {
        AudioClipWrapperImpl nullAudioClipWrapper = new AudioClipWrapperImpl(null);
        assertThrows(NullPointerException.class, nullAudioClipWrapper::play);
    }

    @Test
    void testPlay_whenSoundFileDoesNotExist() {
        String invalidSoundPath = "/sounds/nonExistentSound.wav";
        java.net.URL resourceUrl = getClass().getResource(invalidSoundPath);
        assertNull(resourceUrl, "Resource should be null if the file does not exist");

        // If the resource URL is null, creating an AudioClip should not proceed, and we should test for an appropriate exception.
        assertThrows(IllegalArgumentException.class, () -> {
            // This code block should not run because resourceUrl is null
            AudioClip nonExistentAudioClip = new AudioClip("");
            AudioClipWrapperImpl nonExistentAudioClipWrapper = new AudioClipWrapperImpl(nonExistentAudioClip);
            nonExistentAudioClipWrapper.play();
        });
    }

}
