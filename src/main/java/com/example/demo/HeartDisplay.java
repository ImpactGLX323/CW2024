package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * The {@code HeartDisplay} class represents a visual display of hearts, typically used to indicate the player's remaining lives.
 * The hearts are displayed in an {@code HBox} container, and the number of hearts can decrease as the player loses lives.
 */
public class HeartDisplay {

    // Path to the heart image
    private static final String HEART_IMAGE_PATH = "/com/example/demo/images/heart.png";
    // Height of each heart image
    private static final int HEART_HEIGHT = 50;
    // Index for removing the first heart from the container
    private static final int FIRST_HEART_INDEX = 0;

    // HBox container to hold the heart images
    private final HBox container;

    /**
     * Constructs a {@code HeartDisplay} object, initializing the container's position and populating it with hearts.
     *
     * @param xPosition the X position where the heart display will be located
     * @param yPosition the Y position where the heart display will be located
     * @param numberOfHeartsOnDisplay the initial number of hearts to display
     */
    public HeartDisplay(double xPosition, double yPosition, int numberOfHeartsOnDisplay) {
        this.container = new HBox();  // Create a new HBox to hold the hearts
        initializeContainer(xPosition, yPosition); // Set the position of the heart container
        initializeHearts(numberOfHeartsOnDisplay); // Add hearts to the container based on the given number
    }

    /**
     * Sets the position of the heart container on the screen.
     *
     * @param xPosition the X position for the container
     * @param yPosition the Y position for the container
     */
    private void initializeContainer(double xPosition, double yPosition) {
        container.setLayoutX(xPosition); // Set the X position
        container.setLayoutY(yPosition); // Set the Y position
    }

    /**
     * Adds the specified number of hearts to the container.
     *
     * @param numberOfHearts the number of hearts to add to the container
     */
    private void initializeHearts(int numberOfHearts) {
        for (int i = 0; i < numberOfHearts; i++) {
            ImageView heart = createHeartImageView(); // Create a heart ImageView
            container.getChildren().add(heart); // Add the heart ImageView to the container
        }
    }

    /**
     * Creates and returns an {@code ImageView} representing a heart.
     *
     * @return an {@code ImageView} displaying the heart image
     */
    private ImageView createHeartImageView() {
        ImageView heart = new ImageView(loadHeartImage()); // Load the heart image
        heart.setFitHeight(HEART_HEIGHT); // Set the height of the heart
        heart.setPreserveRatio(true); // Preserve the aspect ratio of the image
        return heart;
    }

    /**
     * Loads the heart image from the resources folder.
     *
     * @return an {@code Image} object representing the heart image
     */
    private Image loadHeartImage() {
        return new Image(getClass().getResource(HEART_IMAGE_PATH).toExternalForm()); // Load the heart image from resources
    }

    /**
     * Removes the first heart from the container. If the container is empty, no action is taken.
     */
    public void removeHeart() {
        if (!container.getChildren().isEmpty()) { // Check if the container has any hearts
            container.getChildren().remove(FIRST_HEART_INDEX); // Remove the first heart
        }
    }

    /**
     * Returns the {@code HBox} container holding the hearts.
     *
     * @return the {@code HBox} containing the heart images
     */
    public HBox getContainer() {
        return container;
    }
}
