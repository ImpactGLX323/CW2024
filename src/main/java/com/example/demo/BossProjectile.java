package com.example.demo;

/**
 * Represents a projectile fired by the boss character in the game.
 * The projectile moves horizontally with a fixed velocity and is represented by an image.
 */
public class BossProjectile extends Projectile {

    // Constants specific to the BossProjectile class
    private static final String IMAGE_NAME = "fireball.png"; // Image used for the projectile
    private static final int IMAGE_HEIGHT = 75;             // Height of the image
    private static final int HORIZONTAL_VELOCITY = -15;     // Horizontal speed at which the projectile moves
    private static final int INITIAL_X_POSITION = 950;      // Initial X position of the projectile

    /**
     * Constructs a BossProjectile with a specified initial Y position.
     *
     * @param initialYPos the initial Y position of the projectile
     */
    public BossProjectile(double initialYPos) {
        // Call the superclass constructor with the projectile's image, height, initial X, and initial Y positions
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
    }

    /**
     * Updates the position of the projectile by moving it horizontally.
     * This method is called repeatedly during the game loop.
     */
    @Override
    public void updatePosition() {
        // Move the projectile horizontally by the predefined velocity
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * Updates the state of the projectile.
     * This includes updating its position.
     */
    @Override
    public void updateActor() {
        // Update the projectile's position
        updatePosition();
    }
}
