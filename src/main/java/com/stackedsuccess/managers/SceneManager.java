package com.stackedsuccess.managers;

import java.util.*;

import com.stackedsuccess.controllers.HomeScreenController;

import javafx.scene.Parent;

public class SceneManager {
  public enum AppUI {
    MAIN_MENU,
    GAME,
    HOME_TUTORIAL,
    SKINSHOP,
    GAME_TUTORIAL,
    NAME_ENTRY,
    SETTINGS;
  }

  private static HashMap<AppUI, Parent> sceneMap = new HashMap<AppUI, Parent>();
  private static HomeScreenController homeScreenController; //Used to reference the home screen controller remotly

  
  /**
   * Adds a scene and its associated parent node to the scene map
   *
   * @param scene the AppUI scene to be added to the map
   * @param parent the Parent node associated with the scene
   */
  public static void addScene(AppUI scene, Parent parent) {
    sceneMap.put(scene, parent);
  }
  
  /**
   * Retrieves the parent node associated with a given scene.
   *
   * @param scene the AppUI scene whose associated parent node is to be retrieved.
   * @return the Parent node associated with the given scene
   */
  public static Parent getScene(AppUI scene) {
    return sceneMap.get(scene);
  }

  /**
   * Sets the HomeScreenController for the SceneManager
   * 
   * @param controller the instance of the home screen controller
   */
  public static void setHomeScreenController(HomeScreenController controller) {
    homeScreenController = controller;
  }
  
  /**
   * Retrieves the HomeScreenController for the SceneManager
   * 
   * @return the instance of the home screen controller
   */
  public static HomeScreenController getHomeScreenController() {
    return homeScreenController;
  }
}
