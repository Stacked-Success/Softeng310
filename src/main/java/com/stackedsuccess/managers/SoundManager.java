package com.stackedsuccess.managers;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    private static final String SOUND_PATH = "src/main/resources/sounds/";
    private static final String SOUND_EXTENSION = ".wav";

    private Map<String, AudioClip> soundEffects;
    private Map<String, MediaPlayer> mediaPlayers;
    private Map<String, String> mediaStates;
    private boolean isSoundEffectsMuted;
    private boolean isBackgroundMusicMuted;

    private SoundManager() {
        soundEffects = new HashMap<>();
        mediaPlayers = new HashMap<>();
        mediaStates = new HashMap<>();
        isSoundEffectsMuted = false;
        isBackgroundMusicMuted = false;
        loadDefaultSounds();
    }

    private static class SoundManagerHelper {
        private static final SoundManager INSTANCE = new SoundManager();
    }

    public static SoundManager getInstance() {
        return SoundManagerHelper.INSTANCE;
    }

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

    private AudioClip loadSoundEffect(String soundName) {
        String path = SOUND_PATH + soundName + SOUND_EXTENSION;
        return new AudioClip(new File(path).toURI().toString());
    }

    private MediaPlayer loadMedia(String mediaName) {
        String path = SOUND_PATH + mediaName + SOUND_EXTENSION;
        try {
            Media media = new Media(new File(path).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.7);
            return mediaPlayer;
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized void playSoundEffect(String soundKey) {
        if (isSoundEffectsMuted) {
            return;
        }

        AudioClip soundEffect = soundEffects.get(soundKey);
        if (soundEffect != null) {
            soundEffect.play();
        } else {
            System.err.println("Sound effect not found: " + soundKey);
        }
    }

    public synchronized void playBackgroundMusic(String mediaKey) {
        if (isBackgroundMusicMuted) {
            return;
        }

        MediaPlayer mediaPlayer = mediaPlayers.get(mediaKey);
        if (mediaPlayer != null) {
            if ("playing".equals(mediaStates.get(mediaKey))) {
                return;
            }
            mediaPlayer.play();
            mediaStates.put(mediaKey, "playing");
        } else {
            System.err.println("Background music not found: " + mediaKey);
        }
    }

    public synchronized void pauseBackgroundMusic(String mediaKey) {
        if (isBackgroundMusicMuted) {
            return;
        }

        MediaPlayer mediaPlayer = mediaPlayers.get(mediaKey);
        if (mediaPlayer != null) {
            if ("playing".equals(mediaStates.get(mediaKey))) {
                mediaPlayer.pause();
                mediaStates.put(mediaKey, "paused");
            } else {
                System.out.println("Background music is not playing: " + mediaKey);
            }
        } else {
            System.err.println("Background music not found: " + mediaKey);
        }
    }

    public synchronized void resumeBackgroundMusic(String mediaKey) {
        if (isBackgroundMusicMuted) {
            return;
        }

        MediaPlayer mediaPlayer = mediaPlayers.get(mediaKey);
        if (mediaPlayer != null) {
            if ("paused".equals(mediaStates.get(mediaKey))) {
                mediaPlayer.play();
                mediaStates.put(mediaKey, "playing");
            } else {
                System.out.println("Background music is not paused: " + mediaKey);
            }
        } else {
            System.err.println("Background music not found: " + mediaKey);
        }
    }

    public synchronized void stopBackgroundMusic(String mediaKey) {
        if (isBackgroundMusicMuted) {
            return;
        }

        MediaPlayer mediaPlayer = mediaPlayers.get(mediaKey);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaStates.put(mediaKey, "stopped");
        } else {
            System.err.println("Background music not found: " + mediaKey);
        }
    }

    public synchronized void stopAllBackgroundMusic() {
        if (isBackgroundMusicMuted) {
            return;
        }

        for (Map.Entry<String, MediaPlayer> entry : mediaPlayers.entrySet()) {
            MediaPlayer mediaPlayer = entry.getValue();
            if (mediaPlayer != null && "playing".equals(mediaStates.get(entry.getKey()))) {
                mediaPlayer.stop();
                mediaStates.put(entry.getKey(), "stopped");
            }
        }
    }

    // Mute/Unmute Sound Effects
    public synchronized void muteSoundEffects() {
        isSoundEffectsMuted = true;
    }

    public synchronized void unmuteSoundEffects() {
        isSoundEffectsMuted = false;
    }

    public synchronized boolean isSoundEffectsMuted() {
        return isSoundEffectsMuted;
    }

    public synchronized void muteBackgroundMusic() {
        isBackgroundMusicMuted = true;
        for (String mediaKey : mediaPlayers.keySet()) {
            MediaPlayer mediaPlayer = mediaPlayers.get(mediaKey);
            if (mediaPlayer != null && "playing".equals(mediaStates.get(mediaKey))) {
                mediaPlayer.setVolume(0);
            }
        }
    }
    
    public synchronized void unmuteBackgroundMusic() {
        isBackgroundMusicMuted = false;
        for (String mediaKey : mediaPlayers.keySet()) {
            MediaPlayer mediaPlayer = mediaPlayers.get(mediaKey);
            if (mediaPlayer != null && "playing".equals(mediaStates.get(mediaKey))) {
                mediaPlayer.setVolume(1);
            }
        }
    }

    public synchronized boolean isBackgroundMusicMuted() {
        return isBackgroundMusicMuted;
    }
}
