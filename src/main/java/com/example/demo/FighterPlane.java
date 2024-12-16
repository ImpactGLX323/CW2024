package com.example.demo;

public abstract class FighterPlane extends ActiveActorDestructible {

	private int health;

	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	public abstract ActiveActorDestructible fireProjectile();
	
	private  boolean ishealthAtZero(){
		return health <= 0;
	}

	@Override
	public void takeDamage() {
		health--;
		if (ishealthAtZero()) {
			this.destroy();
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
		
}
