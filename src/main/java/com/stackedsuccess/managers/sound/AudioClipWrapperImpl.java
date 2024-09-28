package com.stackedsuccess.managers.sound;

import javafx.scene.media.AudioClip;

/**
 * A concrete implementation of {@link AudioClipWrapper} that wraps around a {@link javafx.scene.media.AudioClip}.
 */
public class AudioClipWrapperImpl implements AudioClipWrapper {

    private final AudioClip audioClip;

    /**
     * Constructs an instance of {@code AudioClipWrapperImpl} with the given {@link AudioClip}.
     *
     * @param audioClip the audio clip to be wrapped
     */
    public AudioClipWrapperImpl(AudioClip audioClip) {
        this.audioClip = audioClip;
    }

    /**
     * Plays the audio clip.
     */
    @Override
    public void play() {
        audioClip.play();
    }
}
