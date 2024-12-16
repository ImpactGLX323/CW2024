package com.example.demo.controller;

import com.example.demo.Audio;
import com.example.demo.LevelOne;
import com.example.demo.LevelParent;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int SCREEN_WIDTH = 1300;
    private static final int SCREEN_HEIGHT = 750;
    private static final String TITLE = "Sky Battle";

    private static final String BACKGROUND_MUSIC_PATH = "com.example.demo.images.BackgroundMusic.mp3";

    private Controller myController;
    private Audio audio;

    @Override
    public void start(Stage stage) {
        try {
            initializeStage(stage);

            audio = new Audio();
            audio.playBackgroundMusic(BACKGROUND_MUSIC_PATH);

            LevelParent initialLevel = new LevelOne(
                "/com/example/demo/images/background1.jpg",
                SCREEN_WIDTH,
                SCREEN_HEIGHT,
                5,
                stage
            );

            myController = new Controller(stage, initialLevel, audio);

            myController.launchGame();
        } catch (Exception e) {
            handleInitializationError(e);

            if (audio != null) {
                audio.stopBackgroundMusic();
            }
        }
    }

    private void initializeStage(Stage stage) {
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.setHeight(SCREEN_HEIGHT);
        stage.setWidth(SCREEN_WIDTH);
    }

    private void handleInitializationError(Exception e) {
        System.err.println("Failed to initialize the application.");
        e.printStackTrace();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
