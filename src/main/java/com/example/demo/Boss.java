package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = .04;
	private static final double BOSS_SHIELD_PROBABILITY = .002;
	private static final int IMAGE_HEIGHT = 300;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 100;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = -100;
	private static final int Y_POSITION_LOWER_BOUND = 475;
	private static final int MAX_FRAMES_WITH_SHIELD = 500;

	private final List<Integer> movePattern = new ArrayList<>();
	private boolean isShielded = false; //Initialized boss attack attributes here
	private int consecutiveMovesInSameDirection = 0;
	private int indexOfCurrentMove = 0;
	private int framesWithShieldActivated = 0;

	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		initializeMovePattern();
	}

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();

		if (isOutOfBounds(currentPosition)) {
			setTranslateY(initialTranslateY);
		}
	}

	@Override
	public void updateActor() {
		updatePosition();
		updateShieldState();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		return shouldFireProjectile() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(0);
		}
		Collections.shuffle(movePattern);
	}

	private void updateShieldState() {
		if (isShielded) {
			framesWithShieldActivated++;
			if (framesWithShieldActivated >= MAX_FRAMES_WITH_SHIELD) {
				deactivateShield();
			}
		} else if (Math.random() < BOSS_SHIELD_PROBABILITY) {
			activateShield();
		}
	}

	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;

		if (consecutiveMovesInSameDirection >= MAX_FRAMES_WITH_SAME_MOVE) {
			resetMovePattern();
		}

		return currentMove;
	}

	private boolean isOutOfBounds(double position) {
		return position < Y_POSITION_UPPER_BOUND || position > Y_POSITION_LOWER_BOUND;
	}

	private boolean shouldFireProjectile() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	private void activateShield() {
		isShielded = true;
		framesWithShieldActivated = 0;
	}

	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}

	private void resetMovePattern() {
		Collections.shuffle(movePattern);
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = (indexOfCurrentMove + 1) % movePattern.size();
	}
}
