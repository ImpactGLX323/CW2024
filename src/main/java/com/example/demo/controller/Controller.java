package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.example.demo.LevelParent;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Controller {

    private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
    private final Stage stage;

    public Controller(Stage stage) {
        this.stage = stage;
    }

    public void launchGame() {
        stage.show();
        try {
            goToLevel(LEVEL_ONE_CLASS_NAME);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException 
                 | IllegalAccessException | InvocationTargetException e) {
            showError(e);
        } catch (ClassCastException e) {
            showError(new Exception("The specified class does not extend LevelParent."));
        }
    }

    private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<?> myClass = Class.forName(className);
        Constructor<?> constructor = myClass.getConstructor(double.class, double.class);

        // Ensure the class is compatible with LevelParent
		
        Object instance = constructor.newInstance(stage.getHeight(), stage.getWidth());
        if (!(instance instanceof LevelParent)) {
            throw new ClassCastException("Class " + className + " does not extend LevelParent.");
        }

        LevelParent myLevel = (LevelParent) instance;
        Scene scene = myLevel.initializeScene();
        stage.setScene(scene);
        myLevel.startGame();
    }

    private void showError(Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred while launching the game.");
        alert.setContentText("Details: " + e.getMessage());
        // Optionally log the stack trace for debugging
        e.printStackTrace();
        alert.showAndWait();
    }
}