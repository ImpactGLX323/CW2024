package com.example.demo; 

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShieldImage extends ImageView {

    private static final String IMAGE_PATH = "/com/example/demo/images/shield.jpg";
    private static final int SHIELD_SIZE = 200;

    public ShieldImage(double xPosition, double yPosition) {
        setLayoutX(xPosition);
        setLayoutY(yPosition);
        setImage(new Image(getClass().getResource(IMAGE_PATH).toExternalForm()));
        setVisible(false);
        setFitHeight(SHIELD_SIZE);
        setFitWidth(SHIELD_SIZE);
    }

    public void showShield() {
        setVisible(true);
    }

    public void hideShield() {
        setVisible(false);
    }
}
