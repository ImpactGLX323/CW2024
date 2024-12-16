package com.example.demo; 

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A class representing a shield image in the game.
 * It extends ImageView and provides functionality to display or hide the shield image.
 */
public class ShieldImage extends ImageView {

    // Path to the shield image file
    private static final String IMAGE_PATH = "/com/example/demo/images/shield.jpg";
    
    // The size of the shield image
    private static final int SHIELD_SIZE = 200;

    /**
     * Constructor to initialize the shield image with position.
     * The shield image is loaded from the specified path, positioned at the given coordinates,
     * and initially hidden.
     * 
     * @param xPosition The x position where the shield should be placed.
     * @param yPosition The y position where the shield should be placed.
     */
    public ShieldImage(double xPosition, double yPosition) {
        setLayoutX(xPosition);  // Set the x position of the shield
        setLayoutY(yPosition);  // Set the y position of the shield
        setImage(new Image(getClass().getResource(IMAGE_PATH).toExternalForm()));  // Load the image from the specified path
        setVisible(false);  // Initially hide the shield
        setFitHeight(SHIELD_SIZE);  // Set the height of the shield
        setFitWidth(SHIELD_SIZE);  // Set the width of the shield
    }

    /**
     * Method to make the shield visible.
     * This method sets the visibility of the shield to true.
     */
    public void showShield() {
        setVisible(true);  // Show the shield by making it visible
    }

    /**
     * Method to hide the shield.
     * This method sets the visibility of the shield to false.
     */
    public void hideShield() {
        setVisible(false);  // Hide the shield by making it invisible
    }
}

