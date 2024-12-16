package com.example.demo;

public class UserProjectile extends Projectile {

    // Image name for the user projectile
    private static final String IMAGE_NAME = "userfire.png";
    
    // Image height for the user projectile
    private static final int IMAGE_HEIGHT = 125;
    
    // Horizontal velocity for the user projectile
    private static final int HORIZONTAL_VELOCITY = 15;

    /**
     * Constructor to initialize the user projectile with specified positions.
     * The constructor calls the parent class constructor to initialize the image and position.
     * 
     * @param initialXPos The initial X position of the user projectile.
     * @param initialYPos The initial Y position of the user projectile.
     */
    public UserProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    /**
     * Method to update the position of the projectile horizontally.
     * This method moves the projectile horizontally based on the defined velocity.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);  // Move horizontally with the defined velocity
    }

    /**
     * Method to update the actor (projectile) during each game update cycle.
     * This method calls the updatePosition method to move the projectile.
     */
    @Override
    public void updateActor() {
        updatePosition();  // Updates the position of the projectile
    }
}
