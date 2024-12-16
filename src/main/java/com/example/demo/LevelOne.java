package com.example.demo;

import javafx.stage.Stage;

/**
 * The {@code LevelOne} class represents the first level of the game. 
 * It defines the background, enemy spawning mechanics, and win/loss conditions specific to Level One.
 */
public class LevelOne extends LevelParent {

    // Background image for the level
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background.png";
    // Total number of enemies that can spawn in the level
    private static final int TOTAL_ENEMIES = 5;
    // Number of kills required to advance to the next level
    private static final int KILLS_TO_ADVANCE = 10;
    // Probability of spawning an enemy
    private static final double ENEMY_SPAWN_PROBABILITY = 0.20;
    // Initial health for the player
    private static final int PLAYER_INITIAL_HEALTH = 5;

    /**
     * Constructs a {@code LevelOne} instance, initializing the level's properties and background.
     *
     * @param backgroundImageName the name of the background image
     * @param screenHeight the height of the game screen
     * @param screenWidth the width of the game screen
     * @param playerInitialHealth the initial health of the player
     * @param stage the JavaFX {@code Stage} used for displaying the level
     */
    public LevelOne(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, Stage stage) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, stage);
        System.out.println("LevelOne instantiated with background: " + BACKGROUND_IMAGE_NAME);
    }

    /**
     * Initializes the friendly units, such as the player's fighter plane, on the level.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser()); // Add the user's character to the root container
    }

    /**
     * Spawns enemy units on the screen based on a defined probability.
     */
    @Override
    protected void spawnEnemyUnits() {
        int enemiesToSpawn = TOTAL_ENEMIES - getCurrentNumberOfEnemies(); // Calculate how many enemies need to be spawned
        for (int i = 0; i < enemiesToSpawn; i++) {
            // Spawn an enemy based on a random probability
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition(); // Randomize Y position for the new enemy
                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition); // Create a new enemy plane
                try {
                    addEnemyUnit(newEnemy); // Add the newly created enemy to the game
                } catch (Exception e) {
                    // Handle error when adding an enemy unit, ensuring it does not crash the game
                    System.err.println("Error adding enemy unit: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Instantiates the {@code LevelView}, which represents the visual components of the level.
     *
     * @return a {@code LevelView} object for the current level
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH); // Initialize the level view with the root container and player's initial health
    }

    /**
     * Checks if the game is over by evaluating the player's status or kill count.
     * If the player is destroyed, the game is lost. If the player achieves the required kills, the game is won.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame(); // If the player is destroyed, the game is lost
        } else if (getUserKillCount() >= KILLS_TO_ADVANCE) {
            winGame(); // If the player reaches the kill threshold, the game is won
        }
    }
}
