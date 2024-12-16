package com.example.demo;

/**
 * The {@code EnemyProjectile} class represents a projectile fired by an enemy in the game. 
 * It extends the {@code Projectile} class and includes specific behavior for movement and initialization.
 * The enemy projectile moves horizontally to the left at a predefined speed and uses a specific image and size.
 */
public class EnemyProjectile extends Projectile {

    // Constants specific to the EnemyProjectile class
    private static final String IMAGE_NAME = "enemyFire.png";  // Image used for the enemy projectile
    private static final int IMAGE_HEIGHT = 50;               // Height of the enemy projectile image
    private static final int HORIZONTAL_VELOCITY = -10;       // Horizontal speed at which the enemy projectile moves (negative for left movement)

    /**
     * Constructs an {@code EnemyProjectile} object with the specified initial X and Y positions.
     * The constructor calls the superclass {@code Projectile} constructor with the image, height, and position.
     *
     * @param initialXPos the initial X position of the enemy projectile
     * @param initialYPos the initial Y position of the enemy projectile
     */
    public EnemyProjectile(double initialXPos, double initialYPos) {
        // Call the superclass (Projectile) constructor with the image, height, and initial position
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    /**
     * Updates the position of the enemy projectile. The projectile moves horizontally at a predefined velocity.
     */
    @Override
    public void updatePosition() {
        // Move the enemy projectile horizontally by the predefined velocity (to the left)
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * Updates the actor's state, in this case, updating the position of the enemy projectile.
     */
    @Override
    public void updateActor() {
        // Update the position of the enemy projectile
        updatePosition();
    }
}
