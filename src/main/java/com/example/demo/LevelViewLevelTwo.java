package com.example.demo;

import javafx.scene.Group;

/**
 * A class representing the view of Level Two in the game.
 * It extends the LevelView class and adds functionality specific to Level Two, such as displaying and hiding the shield image.
 */
public class LevelViewLevelTwo extends LevelView {

    // Constants defining the position of the shield image
    private static final int SHIELD_X_POSITION = 1150;  // X position of the shield image
    private static final int SHIELD_Y_POSITION = 500;   // Y position of the shield image

    // Instance variables for the root container and shield image
    private final Group root;  // The root container where all UI elements are added
    private final ShieldImage shieldImage;  // Shield image to be displayed for Level Two

    /**
     * Constructor to initialize LevelViewLevelTwo, extending LevelView.
     * Initializes the root container and creates a shield image at predefined positions.
     * 
     * @param root The root container where the UI elements will be added.
     * @param heartsToDisplay The number of hearts to display for the player.
     */
    public LevelViewLevelTwo(Group root, int heartsToDisplay) {
        super(root, heartsToDisplay);  // Call the superclass constructor to initialize hearts and other elements
        this.root = root;  // Set the root container
        this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);  // Create the shield image
        addImagesToRoot();  // Add shield image to the root container
    }

    /**
     * Method to add the shield image to the root container.
     * The shield image is displayed as part of the Level Two view.
     */
    private void addImagesToRoot() {
        root.getChildren().addAll(shieldImage);  // Add shield image to the root container
    }

    /**
     * Method to show the shield on the screen.
     * The shield image is made visible in the Level Two view.
     */
    public void showShield() {
        shieldImage.showShield();  // Display the shield image
    }

    /**
     * Method to hide the shield from the screen.
     * The shield image is made invisible in the Level Two view.
     */
    public void hideShield() {
        shieldImage.hideShield();  // Hide the shield image
    }
}
