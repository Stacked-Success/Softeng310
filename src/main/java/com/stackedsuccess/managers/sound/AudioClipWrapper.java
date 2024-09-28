package com.stackedsuccess.managers.sound;

/**
 * A wrapper interface for {@link javafx.scene.media.AudioClip} to make it more testable and flexible.
 */
public interface AudioClipWrapper {

    /**
     * Plays the audio clip.
     */
    void play();
}
