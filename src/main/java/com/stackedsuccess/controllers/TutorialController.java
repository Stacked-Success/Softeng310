package com.stackedsuccess.controllers;

import com.stackedsuccess.Main;
import com.stackedsuccess.managers.SceneManager.AppUI;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class TutorialController {

    @FXML
    private ImageView exitBtn;

    //Used to check if the tutorial has been viewed
    //If not the tutorial will be displayed when the game is started
    private boolean hasTutorialBeenViewed = false;
    private boolean createGame = false;
    private AppUI destinationAppUI = AppUI.MAIN_MENU;
    private Runnable onTutorialCompleted;


    public void initialize() {
        //Utilize the deafault constructor build into javafx
    }

    /**
     * This method expands the size of the exit cross when it is hovered
     * over to help users recognise that it is a clickable object
     * 
     * @param event the action of the user hovering their mouse over the exit cross
     */
    @FXML
    private void onExitHoveredOver(MouseEvent event) {
        exitBtn.scaleXProperty().set(1.2);
        exitBtn.scaleYProperty().set(1.2);
    }

    /**
     * Retruns the size of the exit cross to its original size when the user
     * is no longer hovering over it
     * 
     * @param event the users mouse being moved away from the exit cross
     */
    @FXML
    private void onExitNotHoveredOver(MouseEvent event) {
        exitBtn.scaleXProperty().set(1);
        exitBtn.scaleYProperty().set(1);
    }

    /**
     * This method closes the tutorial screen and returns the user to their game
     * when the user clicks on the exit cross. If appearing before the first game,
     * we have to create the game here to stop it starting in the background
     * 
     * @param event the action of clicking the exit xross
     */
    @FXML
    private void onClickExit(MouseEvent event) {

        hasTutorialBeenViewed = true;
        //If the tutorial is being displayed before the first game, create the game
        if (createGame) {
            onTutorialCompleted.run();
            createGame = false;
        } else {
            //Return to the desired screen
            Main.setUi(destinationAppUI);
        }
    }

    public boolean getHasTutorialBeenViewed() {
        return hasTutorialBeenViewed;
    }

    public void setHasTutorialBeenViewed(boolean hasTutorialBeenViewed) {
        this.hasTutorialBeenViewed = hasTutorialBeenViewed;
    }

    public boolean getCreateGame() {
        return createGame;
    }

    public void setCreateGame(boolean createGame) {
        this.createGame = createGame;
    }

    public AppUI getDestinationAppUI() {
        return destinationAppUI;
    }

    public void setDestinationAppUI(AppUI destinationAppUI) {
        this.destinationAppUI = destinationAppUI;
    }

    public Runnable getOnTutorialCompleted() {
        return onTutorialCompleted;
    }

    public void setOnTutorialCompleted(Runnable onTutorialCompleted) {
        this.onTutorialCompleted = onTutorialCompleted;
    }
    
}
