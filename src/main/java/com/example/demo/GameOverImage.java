package com.example.demo; //Refactored 1

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameOverImage extends ImageView {

    private static final String IMAGE_PATH = "/com/example/demo/images/gameover.png";

    public GameOverImage(double xPosition, double yPosition) {
        setImage(loadImage());
        setLayoutX(xPosition);
        setLayoutY(yPosition);
    }

    private Image loadImage() { //Added this method
        return new Image(getClass().getResource(IMAGE_PATH).toExternalForm());
    }
}
