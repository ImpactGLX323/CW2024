package com.example.demo; //Refactored 1

public abstract class Projectile extends ActiveActorDestructible {

	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	@Override
	public void takeDamage() {
		destroy();
	}

	@Override
	public abstract void updatePosition();

}
