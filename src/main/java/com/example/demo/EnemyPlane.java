package com.example.demo; //REFACTORED 1

/**
 * The {@code EnemyPlane} class represents an enemy fighter plane in the game. It extends from {@code FighterPlane} 
 * and includes specific behaviors such as movement, firing projectiles, and health management. 
 * The enemy plane moves horizontally, fires projectiles based on a fire rate, and has specific health.
 */
public class EnemyPlane extends FighterPlane {

    // Constants specific to the EnemyPlane class
    private static final String IMAGE_NAME = "enemyplane.png";  // Image used for the enemy plane
    private static final int IMAGE_HEIGHT = 150;               // Height of the enemy plane image
    private static final int HORIZONTAL_VELOCITY = -6;         // Horizontal speed at which the enemy plane moves
    private static final double PROJECTILE_X_OFFSET = -100.0;  // X offset for the projectile position relative to the enemy plane
    private static final double PROJECTILE_Y_OFFSET = 50.0;    // Y offset for the projectile position relative to the enemy plane
    private static final int INITIAL_HEALTH = 1;               // Initial health of the enemy plane
    private static final double FIRE_RATE = 0.01;              // Fire rate (probability of firing a projectile)

    /**
     * Constructs an {@code EnemyPlane} object with the specified initial X and Y positions.
     * The constructor calls the superclass {@code FighterPlane} constructor with predefined values 
     * such as image, height, position, and health.
     *
     * @param initialXPos the initial X position of the enemy plane
     * @param initialYPos the initial Y position of the enemy plane
     */
    public EnemyPlane(double initialXPos, double initialYPos) {
        // Call the superclass (FighterPlane) constructor with the image, height, position, and health
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }

    /**
     * Updates the position of the enemy plane. The enemy plane moves horizontally at a predefined velocity.
     */
    @Override
    public void updatePosition() {
        // Move the enemy plane horizontally by the predefined velocity (to the left)
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * Determines if the enemy plane should fire a projectile. 
     * If the condition is met (based on fire rate), it creates and returns a new projectile.
     *
     * @return an {@code ActiveActorDestructible} object representing the fired projectile, or {@code null} if no projectile is fired
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        // If the condition is met (based on fire rate), create and return a new projectile
        if (shouldFireProjectile()) {
            return createProjectile();
        }
        // If the condition is not met, return null (no projectile fired)
        return null;
    }

    /**
     * Determines if the enemy plane should fire a projectile based on the fire rate.
     * This method uses randomization to simulate the firing decision.
     *
     * @return {@code true} if the projectile should be fired, otherwise {@code false}
     */
    private boolean shouldFireProjectile() {
        // Randomly decides whether to fire based on the fire rate
        return Math.random() < FIRE_RATE;
    }

    /**
     * Creates a new projectile for the enemy plane at a calculated position relative to the plane.
     * 
     * @return a new {@code ActiveActorDestructible} projectile representing the enemy's attack
     */
    private ActiveActorDestructible createProjectile() {
        // Calculate the X and Y positions for the projectile relative to the enemy plane
        double xPosition = getProjectileXPosition(PROJECTILE_X_OFFSET);
        double yPosition = getProjectileYPosition(PROJECTILE_Y_OFFSET);
        // Return a new EnemyProjectile object with the calculated position
        return new EnemyProjectile(xPosition, yPosition);
    }

    /**
     * Updates the actor's state, in this case, updating the position of the enemy plane.
     */
    @Override
    public void updateActor() {
        // Update the position of the enemy plane
        updatePosition();
    }
}
