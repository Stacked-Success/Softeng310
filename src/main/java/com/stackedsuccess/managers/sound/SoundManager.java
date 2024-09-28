package com.stackedsuccess.managers.sound;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/** Manages the sound effects and background music for the application. */
public class SoundManager {

    private static final String SOUND_PATH = "src/main/resources/sounds/";
    private static final String SOUND_EXTENSION = ".wav";

    private Map<String, AudioClipWrapper> soundEffects;
    private Map<String, MediaPlayerWrapper> mediaPlayers;
    private Map<String, String> mediaStates;
    private boolean isSoundEffectsMuted;
    private boolean isBackgroundMusicMuted;

    /**
     * Private constructor for the SoundManager singleton.
     *
     * <p>This constructor initializes the sound effects and background music maps, as well as the
     * mute states for both. It also loads the default sounds that are used in the application.
     */
    private SoundManager() {
        soundEffects = new HashMap<>();
        mediaPlayers = new HashMap<>();
        mediaStates = new HashMap<>();
        isSoundEffectsMuted = false;
        isBackgroundMusicMuted = false;
        loadDefaultSounds();
    }

    /** Helper class to ensure a single instance of SoundManager. */
    private static class SoundManagerHelper {
        private static final SoundManager INSTANCE = new SoundManager();
    }

    /**
     * Returns the singleton instance of the SoundManager.
     *
     * @return the singleton instance of SoundManager
     */
    public static SoundManager getInstance() {
        return SoundManagerHelper.INSTANCE;
    }

    /**
     * Loads the default sound effects and background music.
     *
     * <p>This method initializes the MediaPlayer objects for background music and AudioClip objects
     * for sound effects. It also sets the initial states for the media players to "stopped".
     */
    private void loadDefaultSounds() {
        mediaPlayers.put("mainmenu", loadMedia("mainmenu"));
        mediaPlayers.put("ingame", loadMedia("ingame"));
        soundEffects.put("gameover", loadSoundEffect("gameover"));
        soundEffects.put("rotate", loadSoundEffect("rotate"));
        soundEffects.put("drop", loadSoundEffect("drop"));
        soundEffects.put("hold", loadSoundEffect("hold"));
        soundEffects.put("layer", loadSoundEffect("layer"));
        soundEffects.put("secondgameover", loadSoundEffect("secondgameover"));

        mediaPlayers.keySet().forEach(key -> mediaStates.put(key, "stopped"));
    }

    /**
     * Loads a sound effect from a file.
     *
     * @param soundName the name of the sound effect file (without extension)
     * @return the AudioClipWrapper object representing the sound effect
     */
    private AudioClipWrapper loadSoundEffect(String soundName) {
        String path = SOUND_PATH + soundName + SOUND_EXTENSION;
        AudioClip audioClip = new AudioClip(new File(path).toURI().toString());
        return new AudioClipWrapperImpl(audioClip);
    }

    /**
     * Loads background music from a file.
     *
     * @param mediaName the name of the background music file (without extension)
     * @return the MediaPlayerWrapper object representing the background music, or null if loading
     *     fails
     */
    private MediaPlayerWrapper loadMedia(String mediaName) {
        String path = SOUND_PATH + mediaName + SOUND_EXTENSION;
        try {
            Media media = new Media(new File(path).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.7);
            return new MediaPlayerWrapperImpl(mediaPlayer);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Plays a sound effect.
     *
     * @param soundKey the key representing the sound effect to play
     */
    public synchronized void playSoundEffect(String soundKey) {
        if (isSoundEffectsMuted) {
            return;
        }

        AudioClipWrapper soundEffect = soundEffects.get(soundKey);
        if (soundEffect != null) {
            soundEffect.play();
        }
    }

    /**
     * Plays background music.
     *
     * @param mediaKey the key representing the background music to play
     */
    public synchronized void playBackgroundMusic(String mediaKey) {
        if (isBackgroundMusicMuted) {
            return;
        }

        MediaPlayerWrapper mediaPlayer = mediaPlayers.get(mediaKey);
        if (mediaPlayer != null) {
            String currentState = mediaStates.get(mediaKey);
            if ("playing".equals(currentState)) {
                return;
            }
            mediaPlayer.play();
            mediaStates.put(mediaKey, "playing");
        }
    }

    /**
     * Pauses the background music.
     *
     * @param mediaKey the key representing the background music to pause
     */
    public synchronized void pauseBackgroundMusic(String mediaKey) {
        if (isBackgroundMusicMuted) {
            return;
        }

        MediaPlayerWrapper mediaPlayer = mediaPlayers.get(mediaKey);
        if (mediaPlayer != null && "playing".equals(mediaStates.get(mediaKey))) {
            mediaPlayer.pause();
            mediaStates.put(mediaKey, "paused");
        }
    }

    /**
     * Resumes the background music.
     *
     * @param mediaKey the key representing the background music to resume
     */
    public synchronized void resumeBackgroundMusic(String mediaKey) {
        if (isBackgroundMusicMuted) {
            return;
        }

        MediaPlayerWrapper mediaPlayer = mediaPlayers.get(mediaKey);
        if (mediaPlayer != null && "paused".equals(mediaStates.get(mediaKey))) {
            mediaPlayer.play();
            mediaStates.put(mediaKey, "playing");
        }
    }

    /**
     * Stops the background music.
     *
     * @param mediaKey the key representing the background music to stop
     */
    public synchronized void stopBackgroundMusic(String mediaKey) {
        if (isBackgroundMusicMuted) {
            return;
        }

        MediaPlayerWrapper mediaPlayer = mediaPlayers.get(mediaKey);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaStates.put(mediaKey, "stopped");
        }
    }

    /** Stops all background music currently playing. */
    public synchronized void stopAllBackgroundMusic() {
        if (isBackgroundMusicMuted) {
            return;
        }

        for (Map.Entry<String, MediaPlayerWrapper> entry : mediaPlayers.entrySet()) {
            MediaPlayerWrapper mediaPlayer = entry.getValue();
            String mediaKey = entry.getKey();
            if (mediaPlayer != null && "playing".equals(mediaStates.get(mediaKey))) {
                mediaPlayer.stop();
                mediaStates.put(mediaKey, "stopped");
            }
        }
    }

    /** Mutes all sound effects. */
    public synchronized void muteSoundEffects() {
        isSoundEffectsMuted = true;
    }

    /** Unmutes all sound effects. */
    public synchronized void unmuteSoundEffects() {
        isSoundEffectsMuted = false;
    }

    /**
     * Checks if sound effects are muted.
     *
     * @return true if sound effects are muted, false otherwise
     */
    public synchronized boolean isSoundEffectsMuted() {
        return isSoundEffectsMuted;
    }

    /** Mutes all background music by setting their volume to zero. */
    public synchronized void muteBackgroundMusic() {
        isBackgroundMusicMuted = true;
        for (String mediaKey : mediaPlayers.keySet()) {
            MediaPlayerWrapper mediaPlayer = mediaPlayers.get(mediaKey);
            if (mediaPlayer != null && "playing".equals(mediaStates.get(mediaKey))) {
                mediaPlayer.setVolume(0);
            }
        }
    }

    /** Unmutes all background music by restoring their volume to the default level. */
    public synchronized void unmuteBackgroundMusic() {
        isBackgroundMusicMuted = false;
        for (String mediaKey : mediaPlayers.keySet()) {
            MediaPlayerWrapper mediaPlayer = mediaPlayers.get(mediaKey);
            if (mediaPlayer != null && "playing".equals(mediaStates.get(mediaKey))) {
                mediaPlayer.setVolume(0.7);
            }
        }
    }

    /**
     * Checks if background music is muted.
     *
     * @return true if background music is muted, false otherwise
     */
    public synchronized boolean isBackgroundMusicMuted() {
        return isBackgroundMusicMuted;
    }

    /**
     * Returns the map of media states for background music in the application.
     *
     * @return a map where the keys are music identifiers (e.g., "mainmenu", "ingame") and the values
     *     are states (e.g., "playing", "paused", "stopped").
     */
    public Map<String, String> getMediaStates() {
        return mediaStates;
    }
}
