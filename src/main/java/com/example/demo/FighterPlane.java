package com.example.demo;

/**
 * The {@code FighterPlane} class represents an abstract fighter plane that can take damage, 
 * fire projectiles, and manage its health. It extends the {@code ActiveActorDestructible} class 
 * and provides common functionality for fighter planes in the game, such as health management 
 * and the ability to fire projectiles at a controlled rate.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

    // Instance variables
    private int health;                 // The health of the fighter plane
    private long lastFiredTime;         // The time when the plane last fired a projectile
    private static final long FIRE_RATE = 1000; // Milliseconds between shots (1 second)

    /**
     * Constructs a {@code FighterPlane} object with the specified image, position, and health.
     * This constructor initializes the fighter plane by calling the constructor of the superclass 
     * and setting the health and last fired time.
     *
     * @param imageName the name of the image used for the plane
     * @param imageHeight the height of the image
     * @param initialXPos the initial X position of the plane
     * @param initialYPos the initial Y position of the plane
     * @param health the initial health of the fighter plane
     */
    public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
        // Call the constructor of the superclass (ActiveActorDestructible) to set the image and position
        super(imageName, imageHeight, initialXPos, initialYPos);
        this.health = health;
        this.lastFiredTime = System.currentTimeMillis(); // Initialize the firing time
    }

    /**
     * Abstract method that must be implemented by subclasses to fire a projectile.
     * Subclasses must provide the specific implementation of how to fire a projectile.
     *
     * @return an {@code ActiveActorDestructible} object representing the fired projectile
     */
    public abstract ActiveActorDestructible fireProjectile();

    /**
     * Checks if the health of the plane is zero or less.
     *
     * @return {@code true} if health is zero or less, otherwise {@code false}
     */
    private boolean isHealthAtZero() {
        return health <= 0;
    }

    /**
     * Decreases the health of the fighter plane by 1 and destroys the plane if health reaches zero.
     * This method is called when the plane takes damage.
     */
    @Override
    public void takeDamage() {
        health--;  // Decrease the health by 1
        if (isHealthAtZero()) {
            destroy();  // If health reaches zero, destroy the plane
        }
    }

    /**
     * Calculates the X position for a projectile relative to the plane's current position, 
     * given an offset from the plane's current position.
     *
     * @param xPositionOffset the offset to add to the X position
     * @return the calculated X position for the projectile
     */
    protected double getProjectileXPosition(double xPositionOffset) {
        return getLayoutX() + getTranslateX() + xPositionOffset; // Calculate based on current position + offset
    }

    /**
     * Calculates the Y position for a projectile relative to the plane's current position, 
     * given an offset from the plane's current position.
     *
     * @param yPositionOffset the offset to add to the Y position
     * @return the calculated Y position for the projectile
     */
    protected double getProjectileYPosition(double yPositionOffset) {
        return getLayoutY() + getTranslateY() + yPositionOffset; // Calculate based on current position + offset
    }

    /**
     * Gets the current health of the plane.
     *
     * @return the current health of the fighter plane
     */
    public int getHealth() {
        return health;
    }

    /**
     * Checks if the plane is allowed to fire a projectile based on the fire rate.
     * Ensures that enough time has passed since the last shot before firing again.
     *
     * @return {@code true} if the plane can fire, otherwise {@code false}
     */
    public boolean canFire() {
        long currentTime = System.currentTimeMillis(); // Get the current system time
        // Check if enough time has passed since the last shot
        if (currentTime - lastFiredTime >= FIRE_RATE) {
            lastFiredTime = currentTime; // Update the last fired time
            return true; // Allow the plane to fire
        }
        return false; // Not enough time has passed, so cannot fire
    }
}
