package com.example.demo; 

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WinImage extends ImageView {

    // Path to the image for the win screen
	private static final String WIN_IMAGE_PATH = "/com/example/demo/images/youwin.png";
    
    // Dimensions for the win image
	private static final int IMAGE_HEIGHT = 500;
	private static final int IMAGE_WIDTH = 600;

    /**
     * Constructor to initialize the win image with specified position.
     * This constructor loads the win image, sets its position on the screen,
     * and initializes its size and visibility.
     * 
     * @param xPosition The x-coordinate of the win image.
     * @param yPosition The y-coordinate of the win image.
     */
	public WinImage(double xPosition, double yPosition) {
		// Load and set the win image
		this.setImage(this.loadWinImage());
		this.setLayoutX(xPosition);  // Set the x position of the image
		this.setLayoutY(yPosition);  // Set the y position of the image
		this.setFitHeight(IMAGE_HEIGHT);  // Set the image's height
		this.setFitWidth(IMAGE_WIDTH);    // Set the image's width
		this.setVisible(false);  // Initially, the win image is hidden
	}

    /**
     * Private method to load the win image from the specified path.
     * 
     * @return An Image object representing the win image.
     */
	private Image loadWinImage() {
		return new Image(getClass().getResource(WIN_IMAGE_PATH).toExternalForm());
	}

    /**
     * Method to show the win image (makes it visible).
     * This method is used to display the win image on the screen.
     */
	public void showWinImage() {
		this.setVisible(true);  // Set the win image to be visible
	}
}
