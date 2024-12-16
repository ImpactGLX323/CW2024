package com.example.demo;

import javafx.stage.Stage;

public class LevelTwo extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final int TOTAL_ENEMIES = 10;
    private static final int KILLS_TO_ADVANCE = 20;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.30;
    private static final int PLAYER_INITIAL_HEALTH = 3;

    public LevelTwo(String backgroundImageName, double screenHeight, double screenWidth,int playerInitialHealth, Stage stage) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, stage);
        System.out.println("LevelTwo instantiated with background: " + BACKGROUND_IMAGE_NAME);
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    @Override
    protected void spawnEnemyUnits() {
        int enemiesToSpawn = TOTAL_ENEMIES - getCurrentNumberOfEnemies();
        for (int i = 0; i < enemiesToSpawn; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                try {
                    addEnemyUnit(newEnemy);
                } catch (Exception e) {
                    System.err.println("Error adding enemy unit: " + e.getMessage());//Added error handling for addEnemyUnit so thats errors dont crash the game
                }
            }
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (getUserKillCount() >= KILLS_TO_ADVANCE) {
            winGame();
        }
    }
}
