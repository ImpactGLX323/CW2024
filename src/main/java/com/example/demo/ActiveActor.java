package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents an abstract active actor in the game.
 * Extends {@link ImageView} to handle graphical representation and provides common functionality
 * for setting image properties, positioning, and movement.
 * Subclasses are required to implement the {@code updatePosition} method.
 */
public abstract class ActiveActor extends ImageView {

    // Directory path for images used in the game
    private static final String IMAGE_DIRECTORY = "/com/example/demo/images/";

    /**
     * Constructs an ActiveActor with the specified image, height, and initial position.
     *
     * @param imageName   the name of the image file to use for this actor
     * @param imageHeight the height of the image in pixels
     * @param initialXPos the initial X position of the actor
     * @param initialYPos the initial Y position of the actor
     */
    public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        // Set the image of the actor by loading it using the provided image name
        setImage(loadImage(imageName));

        // Set the height of the image
        setFitHeight(imageHeight);

        // Set the initial position of the actor on the X and Y axes
        setLayoutX(initialXPos);
        setLayoutY(initialYPos);

        // Preserve the image's aspect ratio while resizing
        setPreserveRatio(true);
    }

    /**
     * Loads the image resource based on the provided image name.
     *
     * @param imageName the name of the image file to load
     * @return the {@link Image} object loaded from the resources
     */
    private Image loadImage(String imageName) {
        return new Image(getClass().getResource(IMAGE_DIRECTORY + imageName).toExternalForm());
    }

    /**
     * Abstract method to update the position of the actor.
     * Subclasses must implement this method to define how the actor's position is updated.
     */
    public abstract void updatePosition();

    /**
     * Moves the actor horizontally by a specified delta value.
     *
     * @param deltaX the amount to move the actor along the X-axis
     */
    protected void moveHorizontally(double deltaX) {
        setTranslateX(getTranslateX() + deltaX); // Adjust the X position by deltaX
    }

    /**
     * Moves the actor vertically by a specified delta value.
     *
     * @param deltaY the amount to move the actor along the Y-axis
     */
    protected void moveVertically(double deltaY) {
        setTranslateY(getTranslateY() + deltaY); // Adjust the Y position by deltaY
    }
}
