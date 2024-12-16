package com.example.demo;

/**
 * Represents a destructible active actor in the game.
 * Extends {@link ActiveActor} and implements the {@link Destructible} interface.
 * This abstract class provides a base for actors that can be destroyed or take damage,
 * while requiring subclasses to implement specific behavior for updating position and state.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

    // A boolean flag indicating whether the actor is destroyed
    private boolean isDestroyed;

    /**
     * Constructs a destructible active actor with the specified image, height, and initial position.
     *
     * @param imageName   the name of the image file to use for this actor
     * @param imageHeight the height of the image in pixels
     * @param initialXPos the initial X position of the actor
     * @param initialYPos the initial Y position of the actor
     */
    public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        // Call the parent constructor to initialize the image and position
        super(imageName, imageHeight, initialXPos, initialYPos);

        // Initialize isDestroyed to false, meaning the actor is not destroyed by default
        isDestroyed = false;
    }

    /**
     * Updates the position of the actor.
     * Subclasses must implement this method to define the movement behavior.
     */
    @Override
    public abstract void updatePosition();

    /**
     * Updates the actor's state.
     * Subclasses must implement this method to define specific update behavior.
     */
    public abstract void updateActor();

    /**
     * Defines how the actor takes damage.
     * This method is required by the {@link Destructible} interface and must be implemented by subclasses.
     */
    @Override
    public abstract void takeDamage();

    /**
     * Destroys the actor by setting its destroyed state to true.
     * This method fulfills the contract of the {@link Destructible} interface.
     */
    @Override
    public void destroy() {
        setDestroyed(true);
    }

    /**
     * Sets the destroyed state of the actor.
     *
     * @param isDestroyed the new destroyed state of the actor
     */
    protected void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

    /**
     * Checks whether the actor is destroyed.
     *
     * @return {@code true} if the actor is destroyed, {@code false} otherwise
     */
    public boolean isDestroyed() {
        return isDestroyed;
    }
}
