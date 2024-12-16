// Controller.java
package com.example.demo.controller;

import com.example.demo.Audio;
import com.example.demo.LevelParent;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Controller {

    private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
    private static final String LEVEL_TWO_CLASS_NAME = "com.example.demo.LevelTwo";
    private static final String BACKGROUND_MUSIC_PATH = "com.example.demo.images.BackgroundMusic.mp3";

    private final Stage stage;
    private final Audio audio;
    private LevelParent currentLevel;

    public Controller(Stage stage, LevelParent initialLevel, Audio audio) {
        this.stage = stage;
        this.currentLevel = initialLevel;
        this.audio = audio;
        this.stage.setScene(initialLevel.initializeScene());
    }

    public void launchGame() {
        stage.show();
        try {
            goToLevel(LEVEL_ONE_CLASS_NAME);
        } catch (ClassCastException e) {
            showError("An error occurred while launching the game.", e);
        }
    }

    private void goToLevel(String className) throws ClassCastException {
        LevelParent level = createLevelInstance(className);
        if (level != null) {
            currentLevel = level;
            Scene scene = level.initializeScene();
            stage.setScene(scene);
            currentLevel.startGame();

            // Play background music for the new level
            audio.stopBackgroundMusic();
            if (className.equals(LEVEL_ONE_CLASS_NAME)) {
                audio.playBackgroundMusic(BACKGROUND_MUSIC_PATH);
            } else if (className.equals(LEVEL_TWO_CLASS_NAME)) {
                audio.playBackgroundMusic(BACKGROUND_MUSIC_PATH);
            }
        }
    }

    private LevelParent createLevelInstance(String className) throws ClassCastException {
        try {
            Class<?> clazz = Class.forName(className);
            Object instance = clazz.getConstructor(String.class, double.class, double.class, int.class, Stage.class)
                    .newInstance("background.png", stage.getHeight(), stage.getWidth(), 100, stage);
            if (!(instance instanceof LevelParent)) {
                throw new ClassCastException("Class " + className + " does not extend LevelParent.");
            }
            return (LevelParent) instance;
        } catch (Exception e) {
            showError("Error while creating level instance: " + className, e);
            return null;
        }
    }

    private void showError(String header, Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText("Details: " + e.getMessage());
        e.printStackTrace();
        alert.showAndWait();
    }

    public void restartGame() {
        if (currentLevel != null) {
            currentLevel.resetLevel();
            try {
                goToLevel(LEVEL_ONE_CLASS_NAME);
            } catch (ClassCastException e) {
                showError("An error occurred while restarting the game.", e);
            }
        }
    }

    public void goToNextLevel() {
        if (currentLevel != null) {
            try {
                LevelParent nextLevel = createLevelInstance(LEVEL_TWO_CLASS_NAME);
                if (nextLevel != null) {
                    currentLevel.goToNextLevel(nextLevel);

                    // Play music for the next level
                    audio.stopBackgroundMusic();
                    audio.playBackgroundMusic(BACKGROUND_MUSIC_PATH);
                }
            } catch (ClassCastException e) {
                showError("An error occurred while transitioning to the next level.", e);
            }
        }
    }
}
