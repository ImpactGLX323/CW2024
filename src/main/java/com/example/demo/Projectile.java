package com.example.demo;

/**
 * An abstract class representing a projectile in the game.
 * It extends the ActiveActorDestructible class and provides functionality for handling damage
 * and updating the projectile's position.
 */
public abstract class Projectile extends ActiveActorDestructible {

    /**
     * Constructor to initialize a projectile with its image name, height, and initial position.
     * It calls the parent constructor to initialize common properties for the projectile.
     * 
     * @param imageName The name of the image associated with the projectile.
     * @param imageHeight The height of the projectile's image.
     * @param initialXPos The initial X position of the projectile.
     * @param initialYPos The initial Y position of the projectile.
     */
    public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        super(imageName, imageHeight, initialXPos, initialYPos);  // Call the parent constructor with image details and position
    }

    /**
     * Method to handle damage when the projectile is hit.
     * The projectile is destroyed when it takes damage.
     */
    @Override
    public void takeDamage() {
        destroy();  // Destroy the projectile when it takes damage
    }

    /**
     * Abstract method to update the position of the projectile.
     * The specific implementation will define how the projectile moves.
     */
    @Override
    public abstract void updatePosition();  // The specific implementation will define how the projectile moves
}
