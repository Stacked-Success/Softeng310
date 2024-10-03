package com.stackedsuccess.controllers;

import java.io.IOException;

import com.stackedsuccess.Main;
import com.stackedsuccess.managers.SceneManager;
import com.stackedsuccess.managers.TetriminoImageManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class SkinShopController {
    
       private TetriminoImageManager imageManager;

    public SkinShopController() {
        imageManager = TetriminoImageManager.getInstance(); 
    }

    @FXML
    void onSkin(ActionEvent event) {
        imageManager.setSkinTheme("ChessSkin");

        System.out.println("Switched to TNT Skin");
    }

    @FXML
void onBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/HomeScreen.fxml"));
        Parent root = loader.load();
        SceneManager.addScene(SceneManager.AppUI.MAIN_MENU, root);
        Main.setUi(SceneManager.AppUI.MAIN_MENU);
        
    
}
}
