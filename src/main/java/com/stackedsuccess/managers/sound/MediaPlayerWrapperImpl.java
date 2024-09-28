package com.stackedsuccess.managers.sound;

import javafx.scene.media.MediaPlayer;

/**
 * A concrete implementation of {@link MediaPlayerWrapper} that wraps around a {@link
 * javafx.scene.media.MediaPlayer}.
 */
public class MediaPlayerWrapperImpl implements MediaPlayerWrapper {

  private final MediaPlayer mediaPlayer;

  /**
   * Constructs an instance of {@code MediaPlayerWrapperImpl} with the given {@link MediaPlayer}.
   *
   * @param mediaPlayer the media player to be wrapped
   */
  public MediaPlayerWrapperImpl(MediaPlayer mediaPlayer) {
    this.mediaPlayer = mediaPlayer;
  }

  /** Plays the media. */
  @Override
  public void play() {
    mediaPlayer.play();
  }

  /** Pauses the media. */
  @Override
  public void pause() {
    mediaPlayer.pause();
  }

  /** Stops the media. */
  @Override
  public void stop() {
    mediaPlayer.stop();
  }

  /**
   * Sets the volume of the media player.
   *
   * @param volume the volume level to be set, where 0 is muted and 1 is the maximum volume
   */
  @Override
  public void setVolume(double volume) {
    mediaPlayer.setVolume(volume);
  }

  /**
   * Gets the current volume of the media player.
   *
   * @return the current volume level, where 0 is muted and 1 is the maximum volume
   */
  @Override
  public double getVolume() {
    return mediaPlayer.getVolume();
  }
}
