package com.example.demo; //REFACTORED 1

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WinImage extends ImageView {

	private static final String WIN_IMAGE_PATH = "/com/example/demo/images/youwin.png";
	private static final int IMAGE_HEIGHT = 500;
	private static final int IMAGE_WIDTH = 600;

	public WinImage(double xPosition, double yPosition) {
		this.setImage(this.loadWinImage());
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setFitHeight(IMAGE_HEIGHT);
		this.setFitWidth(IMAGE_WIDTH);
		this.setVisible(false);
	}

	private Image loadWinImage() {
		return new Image(getClass().getResource(WIN_IMAGE_PATH).toExternalForm());
	}

	public void showWinImage() {
		this.setVisible(true);
	}
}
