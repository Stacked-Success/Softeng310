package com.stackedsuccess.soundTest;

import static org.junit.jupiter.api.Assertions.*;

import com.stackedsuccess.managers.sound.SoundManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
class SoundManagerTest {

  private SoundManager soundManager;

  @BeforeEach
  void setUp() {
    soundManager = SoundManager.getInstance();
    soundManager.stopAllBackgroundMusic();
    soundManager.unmuteSoundEffects();
    soundManager.unmuteBackgroundMusic();
  }

  @Test
  void testPlaySoundEffect() {
    assertDoesNotThrow(() -> soundManager.playSoundEffect("drop"));
  }

  @Test
  void testPlaySoundEffect_whenSoundEffectDoesNotExist() {
    assertDoesNotThrow(() -> soundManager.playSoundEffect("nonExistentSound"));
  }

  @Test
  void testPlayBackgroundMusic() {
    assertDoesNotThrow(() -> soundManager.playBackgroundMusic("mainmenu"));
  }

  @Test
  void testPlayBackgroundMusic_whenMediaDoesNotExist() {
    assertDoesNotThrow(() -> soundManager.playBackgroundMusic("nonExistentMedia"));
  }

  @Test
  void testMuteSoundEffects() {
    soundManager.muteSoundEffects();
    assertTrue(soundManager.isSoundEffectsMuted());
  }

  @Test
  void testUnmuteSoundEffects() {
    soundManager.unmuteSoundEffects();
    assertFalse(soundManager.isSoundEffectsMuted());
  }

  @Test
  void testMuteBackgroundMusic() {
    soundManager.muteBackgroundMusic();
    assertTrue(soundManager.isBackgroundMusicMuted());
  }

  @Test
  void testUnmuteBackgroundMusic() {
    soundManager.unmuteBackgroundMusic();
    assertFalse(soundManager.isBackgroundMusicMuted());
  }

  @Test
  void testStopAllBackgroundMusic() throws InterruptedException {
    soundManager.playBackgroundMusic("mainmenu");
    soundManager.stopAllBackgroundMusic();
    assertEquals("stopped", soundManager.getMediaStates().get("mainmenu"));
  }

  @Test
  void testPauseBackgroundMusic() throws InterruptedException {
    soundManager.playBackgroundMusic("mainmenu");
    soundManager.pauseBackgroundMusic("mainmenu");
    assertEquals("paused", soundManager.getMediaStates().get("mainmenu"));
  }

  @Test
  void testResumeBackgroundMusic() throws InterruptedException {
    soundManager.playBackgroundMusic("mainmenu");
    soundManager.pauseBackgroundMusic("mainmenu");
    soundManager.resumeBackgroundMusic("mainmenu");
    assertEquals("playing", soundManager.getMediaStates().get("mainmenu"));
  }

  @Test
  void testStopBackgroundMusic() {
    soundManager.playBackgroundMusic("mainmenu");
    soundManager.stopBackgroundMusic("mainmenu");
    assertEquals("stopped", soundManager.getMediaStates().get("mainmenu"));
  }

  @Test
  void testStopBackgroundMusic_whenMediaKeyDoesNotExist() {
    assertDoesNotThrow(() -> soundManager.stopBackgroundMusic("nonExistentMedia"));
  }

  @Test
  void testPauseBackgroundMusic_whenMediaKeyDoesNotExist() {
    assertDoesNotThrow(() -> soundManager.pauseBackgroundMusic("nonExistentMedia"));
  }

  @Test
  void testResumeBackgroundMusic_whenMediaKeyDoesNotExist() {
    assertDoesNotThrow(() -> soundManager.resumeBackgroundMusic("nonExistentMedia"));
  }

  @Test
  void testPlaySoundEffect_whenMuted() {
    soundManager.muteSoundEffects();
    assertDoesNotThrow(() -> soundManager.playSoundEffect("drop"));
  }

  @Test
  void testPlayBackgroundMusic_whenMuted() {
    soundManager.muteBackgroundMusic();
    assertDoesNotThrow(() -> soundManager.playBackgroundMusic("mainmenu"));
  }

  @Test
  void testMuteSoundEffects_whenAlreadyMuted() {
    soundManager.muteSoundEffects();
    soundManager.muteSoundEffects();
    assertTrue(soundManager.isSoundEffectsMuted());
  }

  @Test
  void testUnmuteSoundEffects_whenAlreadyUnmuted() {
    soundManager.unmuteSoundEffects();
    soundManager.unmuteSoundEffects();
    assertFalse(soundManager.isSoundEffectsMuted());
  }

  @Test
  void testMuteBackgroundMusic_whenAlreadyMuted() {
    soundManager.muteBackgroundMusic();
    soundManager.muteBackgroundMusic();
    assertTrue(soundManager.isBackgroundMusicMuted());
  }

  @Test
  void testUnmuteBackgroundMusic_whenAlreadyUnmuted() {
    soundManager.unmuteBackgroundMusic();
    soundManager.unmuteBackgroundMusic();
    assertFalse(soundManager.isBackgroundMusicMuted());
  }

  @Test
  void testPlayBackgroundMusicWhenAlreadyPlaying() {
    soundManager.playBackgroundMusic("mainmenu");
    String stateBefore = soundManager.getMediaStates().get("mainmenu");

    soundManager.playBackgroundMusic("mainmenu");
    String stateAfter = soundManager.getMediaStates().get("mainmenu");

    assertEquals("playing", stateBefore);
    assertEquals("playing", stateAfter);
  }

  @Test
void testStopBackgroundMusicWhenAlreadyStopped() {
    soundManager.stopBackgroundMusic("mainmenu");
    soundManager.stopBackgroundMusic("mainmenu");
    
    assertEquals("stopped", soundManager.getMediaStates().get("mainmenu"));
}

@Test
void testResumeBackgroundMusicWhenNeverPaused() {
    soundManager.playBackgroundMusic("mainmenu");
    soundManager.resumeBackgroundMusic("mainmenu");
    
    assertEquals("playing", soundManager.getMediaStates().get("mainmenu"));
}

@Test
void testPauseBackgroundMusicWhenNeverPlayed() {
    soundManager.pauseBackgroundMusic("mainmenu");
    assertEquals("stopped", soundManager.getMediaStates().get("mainmenu"));
}

@Test
void testMuteThenPlayBackgroundMusic() {
    soundManager.muteBackgroundMusic();
    soundManager.playBackgroundMusic("mainmenu");
    
    assertTrue(soundManager.isBackgroundMusicMuted());
    assertEquals("stopped", soundManager.getMediaStates().get("mainmenu"));
}

@Test
void testUnmuteBackgroundMusicWhenNeverMuted() {
    soundManager.unmuteBackgroundMusic();
    assertFalse(soundManager.isBackgroundMusicMuted());
}

@Test
void testMultipleBackgroundMusicTracksPlayingSimultaneously() {
    soundManager.playBackgroundMusic("mainmenu");
    soundManager.playBackgroundMusic("ingame");
    
    assertEquals("playing", soundManager.getMediaStates().get("mainmenu"));
    assertEquals("playing", soundManager.getMediaStates().get("ingame"));
}
}
