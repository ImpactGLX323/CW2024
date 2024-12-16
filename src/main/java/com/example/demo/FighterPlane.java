package com.example.demo;

public abstract class FighterPlane extends ActiveActorDestructible {

    private int health;
    private long lastFiredTime;
    private static final long FIRE_RATE = 1000; // Milliseconds between shots (1 second)

    public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
        super(imageName, imageHeight, initialXPos, initialYPos);
        this.health = health;
        this.lastFiredTime = System.currentTimeMillis(); // Initialize firing time
    }

    public abstract ActiveActorDestructible fireProjectile();

    private boolean isHealthAtZero() {
        return health <= 0;
    }

    @Override
    public void takeDamage() {
        health--;
        if (isHealthAtZero()) {
            destroy();
        }
    }

    protected double getProjectileXPosition(double xPositionOffset) {
        return getLayoutX() + getTranslateX() + xPositionOffset;
    }

    protected double getProjectileYPosition(double yPositionOffset) {
        return getLayoutY() + getTranslateY() + yPositionOffset;
    }

    public int getHealth() {
        return health;
    }

    // The canFire() method checks if enough time has passed since the last shot
    public boolean canFire() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFiredTime >= FIRE_RATE) {
            lastFiredTime = currentTime; // Update the last fired time
            return true; // Allow firing
        }
        return false; // Not enough time has passed, so cannot fire
    }
}

