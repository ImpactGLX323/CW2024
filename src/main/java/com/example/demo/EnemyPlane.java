package com.example.demo; //REFACTORED 1

public class EnemyPlane extends FighterPlane {

    private static final String IMAGE_NAME = "enemyplane.png";
    private static final int IMAGE_HEIGHT = 150;
    private static final int HORIZONTAL_VELOCITY = -6;
    private static final double PROJECTILE_X_OFFSET = -100.0;
    private static final double PROJECTILE_Y_OFFSET = 50.0;
    private static final int INITIAL_HEALTH = 1;
    private static final double FIRE_RATE = 0.01;

    public EnemyPlane(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        if (shouldFireProjectile()) {
            return createProjectile();
        }
        return null;
    }

    private boolean shouldFireProjectile() { //Refactored
        return Math.random() < FIRE_RATE;
    }

    private ActiveActorDestructible createProjectile() {
        double xPosition = getProjectileXPosition(PROJECTILE_X_OFFSET);
        double yPosition = getProjectileYPosition(PROJECTILE_Y_OFFSET);
        return new EnemyProjectile(xPosition, yPosition);
    }

    @Override
    public void updateActor() {
        updatePosition();
    }
}
