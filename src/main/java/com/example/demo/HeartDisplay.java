package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class HeartDisplay {

    private static final String HEART_IMAGE_PATH = "/com/example/demo/images/heart.png";
    private static final int HEART_HEIGHT = 50;
    private static final int FIRST_HEART_INDEX = 0;

    private final HBox container;

    public HeartDisplay(double xPosition, double yPosition, int numberOfHeartsOnDisplay) {
        this.container = new HBox();
        initializeContainer(xPosition, yPosition);
        initializeHearts(numberOfHeartsOnDisplay);
    }

    private void initializeContainer(double xPosition, double yPosition) {
        container.setLayoutX(xPosition);
        container.setLayoutY(yPosition);
    }

    private void initializeHearts(int numberOfHearts) {
        for (int i = 0; i < numberOfHearts; i++) {
            ImageView heart = createHeartImageView();
            container.getChildren().add(heart);
        }
    }

    private ImageView createHeartImageView() {
        ImageView heart = new ImageView(loadHeartImage());
        heart.setFitHeight(HEART_HEIGHT);
        heart.setPreserveRatio(true);
        return heart;
    }

    private Image loadHeartImage() {
        return new Image(getClass().getResource(HEART_IMAGE_PATH).toExternalForm());
    }

    public void removeHeart() {
        if (!container.getChildren().isEmpty()) {
            container.getChildren().remove(FIRST_HEART_INDEX);
        }
    }

    public HBox getContainer() {
        return container;
    }
}
