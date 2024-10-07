package com.stackedsuccess.managers;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;

import com.stackedsuccess.ScoreRecorder;
import java.io.IOException;

public class SkinDisplayMaker {

    private final int paneWidth = 150;
    private final int paneHeight = 250;
    private final int imageWidth = 120;
    private final int imageHeight = 120;

    private int pointsIncrement = 0;

    /**
     * Creates a pane for a skin with a thumbnail, skin name, points required, and a select/locked button.
     *
     * @param themeName the name of the skin theme (e.g., "TNTSkin", "ChessSkin")
     * @param selectSkinHandler the event handler for the "Select" button
     * @return the pane containing the skin preview, name, points label, and selection/locked button
     */
    public VBox createSkinPane(String themeName, EventHandler<ActionEvent> selectSkinHandler) {
        VBox pane = new VBox(10);
        pane.setPrefSize(paneWidth, paneHeight);
        pane.setAlignment(Pos.CENTER);

        ImageView skinImageView = createImageView(themeName);

        String displayName = themeName.replace("Skin", "");
        Text skinName = new Text(displayName);
        skinName.setFont(new Font(18));
        skinName.setFill(javafx.scene.paint.Color.WHITE);

        Text pointsLabel = new Text(pointsIncrement + " points");
        pointsLabel.setFont(new Font(16));
        pointsLabel.setFill(javafx.scene.paint.Color.WHITE);

        int highScore;
        try {
            String highScoreStr = ScoreRecorder.getHighScore();

            if (highScoreStr.equals("No high score available.")) {
                highScore = 0;
            } else {
                highScore = Integer.parseInt(highScoreStr.split("=")[1].trim());
            }
        } catch (IOException | NumberFormatException e) {
            highScore = 0;
        }

        Button skinButton;
        if (highScore >= pointsIncrement) {
            skinButton = new Button("Select");
            skinButton.setOnAction(selectSkinHandler);
        } else {
            skinButton = new Button("Locked");
            skinButton.setDisable(true);
        }
        skinButton.setFont(new Font(16));

        pointsIncrement += 100;

        pane.getChildren().addAll(skinImageView, skinName, pointsLabel, skinButton);
        VBox.setVgrow(skinImageView, Priority.ALWAYS);

        return pane;
    }

    /**
     * Creates a pane for skins that are "Coming Soon" with a disabled locked button and points label.
     *
     * @return the pane with a "Coming Soon" message, points required, and locked button
     */
    public VBox createComingSoonPane() {
        VBox pane = new VBox(10);
        pane.setPrefSize(paneWidth, paneHeight);
        pane.setAlignment(Pos.CENTER);

        ImageView skinImageView = new ImageView(new Image("file:src/main/resources/images/questionmark.png", imageWidth, imageHeight, true, true));
        skinImageView.setFitWidth(imageWidth);
        skinImageView.setFitHeight(imageHeight);
        skinImageView.setPreserveRatio(true);

        Text comingSoonText = new Text("Coming Soon");
        comingSoonText.setFont(new Font(16));
        comingSoonText.setFill(javafx.scene.paint.Color.WHITE);

        Text pointsLabel = new Text(pointsIncrement + " points");
        pointsLabel.setFont(new Font(14));
        pointsLabel.setFill(javafx.scene.paint.Color.WHITE);

        Button lockedButton = new Button("Locked");
        lockedButton.setFont(new Font(16));
        lockedButton.setDisable(true);

        pane.getChildren().addAll(skinImageView, comingSoonText, pointsLabel, lockedButton);
        VBox.setVgrow(skinImageView, Priority.ALWAYS);

        pointsIncrement += 100;

        return pane;
    }

    /**
     * Creates an ImageView for the skin thumbnail.
     *
     * @param themeName the name of the skin theme
     * @return the ImageView containing the skin image
     */
    private ImageView createImageView(String themeName) {
        String imagePath = "file:src/main/resources/images/" + themeName + "/block.png";
        Image image = new Image(imagePath, imageWidth, imageHeight, true, true);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(imageWidth);
        imageView.setFitHeight(imageHeight);
        imageView.setPreserveRatio(true);

        return imageView;
    }
}
