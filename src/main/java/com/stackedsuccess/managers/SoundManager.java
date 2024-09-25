package com.stackedsuccess.managers;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    private static final String SOUND_PATH = "src/main/resources/sounds/";
    private static final String SOUND_EXTENSION = ".mp3";

    private Map<String, AudioClip> soundEffects;
    private Map<String, MediaPlayer> mediaPlayers;
    private boolean isMuted;

    public SoundManager() {
        soundEffects = new HashMap<>();
        mediaPlayers = new HashMap<>();
        isMuted = false;
        loadDefaultSounds();
    }

    private void loadDefaultSounds() {
        mediaPlayers.put("mainmenu", loadMedia("mainmenu"));
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
            return mediaPlayer;
        } catch (Exception e) {
            System.err.println("Error loading media: " + path);
            return null;
        }
    }

    public void playSoundEffect(String soundKey) {
        if (isMuted) {
            System.out.println("Sound is muted. Not playing sound effect: " + soundKey);
            return;
        }

        AudioClip soundEffect = soundEffects.get(soundKey);
        if (soundEffect != null) {
            soundEffect.play();
        } else {
            System.err.println("Sound effect not found: " + soundKey);
        }
    }

    public void playBackgroundMusic(String mediaKey) {
        if (isMuted) {
            System.out.println("Sound is muted. Not playing background music: " + mediaKey);
            return;
        }

        MediaPlayer mediaPlayer = mediaPlayers.get(mediaKey);
        if (mediaPlayer != null) {
            mediaPlayer.play();
        } else {
            System.err.println("Background music not found: " + mediaKey);
        }
    }

    public void stopBackgroundMusic(String mediaKey) {
        MediaPlayer mediaPlayer = mediaPlayers.get(mediaKey);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        } else {
            System.err.println("Background music not found: " + mediaKey);
        }
    }

    public void stopAllBackgroundMusic() {
        for (Map.Entry<String, MediaPlayer> entry : mediaPlayers.entrySet()) {
            MediaPlayer mediaPlayer = entry.getValue();
            if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.stop();
                System.out.println("Stopped background music: " + entry.getKey());
            }
        }
    }

    public void mute() {
        if (!isMuted) {
            isMuted = true;
            for (MediaPlayer mediaPlayer : mediaPlayers.values()) {
                if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.setVolume(0); 
                }
            }
            System.out.println("All sounds muted.");
        }
    }

    public void unmute() {
        if (isMuted) {
            isMuted = false;
            for (MediaPlayer mediaPlayer : mediaPlayers.values()) {
                if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.setVolume(1);
                }
            }
            System.out.println("Sound is unmuted.");
        }
    }

    public boolean isMuted() {
        return isMuted;
    }
}
