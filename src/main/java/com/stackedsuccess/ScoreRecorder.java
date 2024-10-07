package com.stackedsuccess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ScoreRecorder {

  private ScoreRecorder() {
    throw new IllegalStateException();
  }

  private static final String SCOREFILE = "score.txt";
  private static final int MAX_SCORES = 12;
  private static final String MARATHON_SCOREFILE = "marathon_score.txt";

  /**
   * Save the score to the file and update the high score if necessary.
   *
   * @param score the score to save
   * @throws IOException if an I/O error occurs
   */
  public static void saveScore(String score) throws IOException {
    List<Integer> scores = getAllScores();
    scores.add(Integer.parseInt(score));
    Collections.sort(scores, Collections.reverseOrder());
    if (scores.size() > MAX_SCORES) {
      scores.remove(scores.size() - 1);
    }
    writeScores(scores);
  }

  /**
   * Get the high score as a string.
   *
   * @return the high score as a string
   * @throws IOException
   */
  public static String getHighScore() throws IOException {
    List<Integer> scores = getAllScores();
    if (scores.isEmpty()) {
      return "0";
    }
    return String.valueOf(scores.get(0));
  }

  /**
   * Get all scores from the file.
   *
   * @return a list of all scores
   * @throws IOException
   */
  public static List<Integer> getAllScores() throws IOException {
    List<Integer> scores = new ArrayList<>();
    File file = new File(SCOREFILE);
    if (!file.exists()) {
        return scores;
    }
    try (BufferedReader reader = new BufferedReader(new FileReader(SCOREFILE))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                scores.add(Integer.parseInt(line));
            }
        }
    }
    return scores;
}

  /**
   * Write the scores to the file.
   *
   * @param scores the scores to write
   * @throws IOException
   */
  public static void writeScores(List<Integer> scores) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCOREFILE))) {
      for (int score : scores) {
        writer.write(String.valueOf(score));
        writer.newLine();
      }
    }
  }

  /**
   * Creates a score file if it does not already exist.
   *
   * <p>This method checks if the score file exists. If the file does not exist, it attempts to create a new one.
   * If the file creation fails on the first attempt, the method retries. If the file creation fails again, an
   * IOException is thrown to indicate that the score file could not be created</p>
   */
  public static void createScoreFile() {
    File scoreFile = new File(SCOREFILE);
    if (!scoreFile.exists()) {
      try {
        boolean isFileCreated = scoreFile.createNewFile();
        if (!isFileCreated) {
          // Retry creating the file
          isFileCreated = scoreFile.createNewFile();
          if (!isFileCreated) {
            throw new IOException("Failed to create score file after retrying.");
          }
        }
      } catch (IOException e) {
        throw new IllegalArgumentException("Creating score file", e);
      }
    }
  }

  /**
     * Save a Marathon Mode score to the file.
     *
     * @param linesCleared number of lines cleared
     * @param targetLines target number of lines to clear
     * @param timeTakenInSeconds time taken in seconds
     * @throws IOException if an I/O error occurs
     */
    public static void saveMarathonScore(int linesCleared, int targetLines, int timeTakenInSeconds) throws IOException {
      List<String> scores = getAllMarathonScores();
      String newScore = linesCleared + "|" + targetLines + "|" + timeTakenInSeconds;
      scores.add(newScore);

      if (scores.size() > MAX_SCORES) {
          scores.remove(scores.size() - 1); // Keep only the top MAX_SCORES entries
      }
      writeMarathonScores(scores);
  }

  /**
   * Get all Marathon Mode scores from the file.
   *
   * @return a list of all scores as strings
   * @throws IOException
   */
  public static List<String> getAllMarathonScores() throws IOException {
    List<String> scores = new ArrayList<>();
    File file = new File(MARATHON_SCOREFILE);
    if (!file.exists()) {
        return scores;
    }
    try (BufferedReader reader = new BufferedReader(new FileReader(MARATHON_SCOREFILE))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                scores.add(line);
            }
        }
    }
    return scores;
}

  /**
   * Write the Marathon Mode scores to the file.
   *
   * @param scores the scores to write
   * @throws IOException
   */
  public static void writeMarathonScores(List<String> scores) throws IOException {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(MARATHON_SCOREFILE))) {
          for (String score : scores) {
              writer.write(score);
              writer.newLine();
          }
      }
  }

  /**
   * Creates a Marathon score file if it does not already exist.
   */
  public static void createMarathonScoreFile() {
      File marathonScoreFile = new File(MARATHON_SCOREFILE);
      if (!marathonScoreFile.exists()) {
          try {
              boolean isFileCreated = marathonScoreFile.createNewFile();
              if (!isFileCreated) {
                  // Retry creating the file
                  isFileCreated = marathonScoreFile.createNewFile();
                  if (!isFileCreated) {
                      throw new IOException("Failed to create marathon score file after retrying.");
                  }
              }
          } catch (IOException e) {
              throw new IllegalArgumentException("Creating marathon score file", e);
          }
      }
  }

  /**
   * Parse the marathon scores into a human-readable format.
   *
   * @return a list of formatted marathon scores
   * @throws IOException
   */
  public static List<String> getFormattedMarathonScores() throws IOException {
      List<String> rawScores = getAllMarathonScores();
      List<String> formattedScores = new ArrayList<>();

      for (String rawScore : rawScores) {
          String[] parts = rawScore.split("\\|");
          if (parts.length == 3) {
              int linesCleared = Integer.parseInt(parts[0]);
              int targetLines = Integer.parseInt(parts[1]);
              int timeTakenInSeconds = Integer.parseInt(parts[2]);

              int minutes = timeTakenInSeconds / 60;
              int seconds = timeTakenInSeconds % 60;
              String formattedTime = String.format("%02d:%02d", minutes, seconds);

              formattedScores.add("Lines Cleared: " + linesCleared + "/" + targetLines + ", Time: " + formattedTime);
          }
      }

      return formattedScores;
  }
 

}

