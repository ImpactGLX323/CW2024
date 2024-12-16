package com.example.demo; //Refactored 1

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class ActiveActor extends ImageView {

	private static final String IMAGE_DIRECTORY = "/com/example/demo/images/";

	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		setImage(loadImage(imageName));
		setFitHeight(imageHeight);
		setLayoutX(initialXPos);
		setLayoutY(initialYPos);
		setPreserveRatio(true);
	}

	private Image loadImage(String imageName) {
		return new Image(getClass().getResource(IMAGE_DIRECTORY + imageName).toExternalForm());
	}


	public abstract void updatePosition();

	protected void moveHorizontally(double deltaX) {
		setTranslateX(getTranslateX() + deltaX);
	}

	protected void moveVertically(double deltaY) {
		setTranslateY(getTranslateY() + deltaY);
	}
}
