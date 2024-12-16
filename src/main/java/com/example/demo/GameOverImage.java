package com.example.demo; 

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The {@code GameOverImage} class represents an image that is displayed when the game is over. 
 * It extends {@code ImageView} and loads a game over image at a specified position on the screen.
 */
public class GameOverImage extends ImageView {

    // Path to the game over image
    private static final String IMAGE_PATH = "/com/example/demo/images/gameover.png";

    /**
     * Constructs a {@code GameOverImage} object and initializes the game over image at the specified position.
     *
     * @param xPosition the X position where the image will be displayed
     * @param yPosition the Y position where the image will be displayed
     */
    public GameOverImage(double xPosition, double yPosition) {
        setImage(loadImage()); // Load the image and set it to the ImageView
        setLayoutX(xPosition);  // Set the X position of the image
        setLayoutY(yPosition);  // Set the Y position of the image
    }

    /**
     * Loads the game over image from the specified path.
     *
     * @return an {@code Image} object representing the loaded game over image
     */
    private Image loadImage() { // Added this method
        return new Image(getClass().getResource(IMAGE_PATH).toExternalForm()); // Load image from resources
    }
}

