package com.stackedsuccess.managers.sound;

/**
 * A wrapper interface for {@link javafx.scene.media.MediaPlayer} to make it more testable and
 * flexible.
 */
public interface MediaPlayerWrapper {

  /** Plays the media. */
  void play();

  /** Pauses the media. */
  void pause();

  /** Stops the media. */
  void stop();

  /**
   * Sets the volume of the media player.
   *
   * @param volume the volume level to be set, where 0 is muted and 1 is the maximum volume
   */
  void setVolume(double volume);

  /**
   * Gets the current volume of the media player.
   *
   * @return the current volume level, where 0 is muted and 1 is the maximum volume
   */
  double getVolume();
}
