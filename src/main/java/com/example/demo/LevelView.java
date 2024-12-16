/**
 * The {@code LevelView} class is responsible for managing the visual elements of the game level,
 * including the heart display for player health, win image, and game over image.
 */
package com.example.demo;

import javafx.scene.Group;

public class LevelView {
    
    /**
     * X position of the heart display.
     */
    private static final double HEART_DISPLAY_X_POSITION = 5;

    /**
     * Y position of the heart display.
     */
    private static final double HEART_DISPLAY_Y_POSITION = 25;

    /**
     * X position of the win image.
     */
    private static final int WIN_IMAGE_X_POSITION = 355;

    /**
     * Y position of the win image.
     */
    private static final int WIN_IMAGE_Y_POSITION = 175;

    /**
     * X position of the loss screen image.
     */
    private static final int LOSS_SCREEN_X_POSITION = -160;

    /**
     * Y position of the loss screen image.
     */
    private static final int LOSS_SCREEN_Y_POSISITION = -375;

    /**
     * The root container where all visual elements are added.
     */
    private final Group root;

    /**
     * Win image to be displayed when the player wins.
     */
    private final WinImage winImage;

    /**
     * Game over image to be displayed when the player loses.
     */
    private final GameOverImage gameOverImage;

    /**
     * Heart display to show the remaining player health.
     */
    private final HeartDisplay heartDisplay;

    /**
     * Constructor to initialize the {@code LevelView} with the root container and initial heart count.
     *
     * @param root the root container for adding visual elements
     * @param heartsToDisplay the initial number of hearts to display
     */
    public LevelView(Group root, int heartsToDisplay) {
        this.root = root; // Set the root container
        this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay); // Create the heart display
        this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION); // Create the win image
        this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSISITION); // Create the game over image
    }

    /**
     * Resets the level view. Currently a placeholder for potential future functionality.
     */
    public void resetLevelView() {
        System.out.println("LevelView reset"); // Placeholder message
    }

    /**
     * Displays the heart display on the screen.
     */
    public void showHeartDisplay() {
        root.getChildren().add(heartDisplay.getContainer()); // Add the heart display container to the root
    }

    /**
     * Displays the win image on the screen.
     */
    public void showWinImage() {
        root.getChildren().add(winImage); // Add the win image to the root
        winImage.showWinImage(); // Display the win image
    }
    
    /**
     * Displays the game over image on the screen.
     */
    public void showGameOverImage() {
        root.getChildren().add(gameOverImage); // Add the game over image to the root
    }

    /**
     * Updates the heart display by removing hearts based on the remaining health.
     *
     * @param heartsRemaining the number of hearts to remain displayed
     */
    public void removeHearts(int heartsRemaining) {
        int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size(); // Get the current number of hearts
        heartsRemaining = Math.max(0, Math.min(heartsRemaining, currentNumberOfHearts)); // Ensure hearts remaining is within valid range
        for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
            heartDisplay.removeHeart(); // Remove hearts from the display
        }
    }
}
