package com.example.demo;

public class UserPlane extends FighterPlane {

    // Image name for the user plane
    private static final String IMAGE_NAME = "userplane.png";
    
    // Upper and lower bounds for the Y position of the user plane
    private static final double Y_UPPER_BOUND = -40;
    private static final double Y_LOWER_BOUND = 600.0;
    
    // Initial X and Y positions for the user plane
    private static final double INITIAL_X_POSITION = 5.0;
    private static final double INITIAL_Y_POSITION = 300.0;
    
    // Image height and velocity for the user plane
    private static final int IMAGE_HEIGHT = 150;
    private static final int VERTICAL_VELOCITY = 8;
    
    // X and Y position offsets for the projectile fired by the user plane
    private static final int PROJECTILE_X_POSITION = 110;
    private static final int PROJECTILE_Y_POSITION_OFFSET = 20;

    // Instance variables for the user's velocity multiplier, kill count, and number of kills
    private int velocityMultiplier;
    private int numberOfKills;
    private int killCount = 0;

    /**
     * Method to increment the kill count by one.
     * This method increases the user's kill count each time it is called.
     */
    public void incrementKillCount() {
        killCount++;
    }

    /**
     * Method to retrieve the current kill count.
     * 
     * @return The current kill count.
     */
    public int getKillCount() {
        return killCount;
    }

    /**
     * Constructor to initialize the user plane with a specific initial health.
     * Initializes the plane's image, position, and health, and sets the initial velocity multiplier and kill count.
     * 
     * @param initialHealth The initial health of the user plane.
     */
    public UserPlane(int initialHealth) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
        velocityMultiplier = 0;  // Initially, the user plane is not moving
        numberOfKills = 0;       // Initialize the number of kills to 0
    }

    /**
     * Method to update the position of the user plane based on movement.
     * If the plane is moving, it adjusts its Y position based on the vertical velocity.
     * If the plane goes out of bounds, it reverts to its previous position.
     */
    @Override
    public void updatePosition() {
        if (isMoving()) {
            double initialTranslateY = getTranslateY();
            moveVertically(VERTICAL_VELOCITY * velocityMultiplier);  // Move vertically based on velocity multiplier
            double newPosition = getLayoutY() + getTranslateY();
            
            // If the new position is out of bounds, revert the position
            if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
                setTranslateY(initialTranslateY);
            }
        }
    }

    /**
     * Method to update the actor (plane) on each game update cycle.
     * This calls the updatePosition method to update the plane's position.
     */
    @Override
    public void updateActor() {
        updatePosition();  // Updates the position of the plane
    }

    /**
     * Method to fire a projectile from the user plane.
     * This method creates and returns a new projectile based on the user's current position.
     * 
     * @return A new instance of a projectile fired by the user plane.
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        // Create and return a new projectile based on the user's position
        return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
    }

    /**
     * Helper method to check if the plane is moving (based on velocity multiplier).
     * 
     * @return true if the plane is moving, false otherwise.
     */
    private boolean isMoving() {
        return velocityMultiplier != 0;
    }

    /**
     * Method to move the user plane up.
     * This method sets the velocity multiplier to move the plane upwards.
     */
    public void moveUp() {
        velocityMultiplier = -1;  // Set velocity multiplier to move up
    }

    /**
     * Method to move the user plane down.
     * This method sets the velocity multiplier to move the plane downwards.
     */
    public void moveDown() {
        velocityMultiplier = 1;   // Set velocity multiplier to move down
    }

    /**
     * Method to stop the user plane's movement.
     * This method sets the velocity multiplier to zero, stopping any movement.
     */
    public void stop() {
        velocityMultiplier = 0;   // Set velocity multiplier to stop the movement
    }

    /**
     * Method to retrieve the number of kills the user plane has made.
     * 
     * @return The number of kills made by the user plane.
     */
    public int getNumberOfKills() {
        return numberOfKills;
    }

    /**
     * Method to increment the kill count by a specified number of kills.
     * 
     * @param kills The number of kills to add to the kill count.
     */
    public void incrementKillCountBy(int kills) {
        for (int i = 0; i < kills; i++) {
            incrementKillCount();  // Increment the kill count for each kill
        }
    }
}
