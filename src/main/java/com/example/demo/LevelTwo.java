/**
 * The {@code LevelTwo} class represents the second level in the game, extending the functionality of the abstract {@code LevelParent} class.
 * This level includes specific configurations such as the background image, total enemies, and player health.
 */
package com.example.demo;

import javafx.stage.Stage;

public class LevelTwo extends LevelParent {

    /**
     * Background image for LevelTwo.
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";

    /**
     * Total number of enemies to spawn in LevelTwo.
     */
    private static final int TOTAL_ENEMIES = 10;

    /**
     * Number of kills required to advance to the next level.
     */
    private static final int KILLS_TO_ADVANCE = 20;

    /**
     * Probability of spawning each enemy during the game.
     */
    private static final double ENEMY_SPAWN_PROBABILITY = 0.30;

    /**
     * Initial health of the player's plane in LevelTwo.
     */
    private static final int PLAYER_INITIAL_HEALTH = 3;

    /**
     * Constructor for LevelTwo, initializing the level with specific configurations.
     *
     * @param backgroundImageName the name of the background image file (not used in this implementation)
     * @param screenHeight the height of the game screen
     * @param screenWidth the width of the game screen
     * @param playerInitialHealth the initial health of the player's plane
     * @param stage the primary stage of the application
     */
    public LevelTwo(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, Stage stage) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, stage);
        System.out.println("LevelTwo instantiated with background: " + BACKGROUND_IMAGE_NAME); // Debug message
    }

    /**
     * Initializes the friendly units in LevelTwo by adding the user's plane to the scene graph.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser()); // Add the user plane to the scene's root
    }

    /**
     * Spawns enemy units for LevelTwo based on the defined spawn probability and total enemies.
     */
    @Override
    protected void spawnEnemyUnits() {
        int enemiesToSpawn = TOTAL_ENEMIES - getCurrentNumberOfEnemies(); // Calculate how many enemies to spawn
        for (int i = 0; i < enemiesToSpawn; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) { // If random value is less than spawn probability
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition(); // Random Y position for the enemy
                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition); // Create a new enemy plane
                try {
                    addEnemyUnit(newEnemy); // Add the new enemy to the game
                } catch (Exception e) {
                    System.err.println("Error adding enemy unit: " + e.getMessage()); // Error handling if enemy unit fails to be added
                }
            }
        }
    }

    /**
     * Instantiates the {@code LevelView} for LevelTwo, displaying level-specific information such as player health.
     *
     * @return a new {@code LevelView} object initialized with the player's initial health
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH); // Create a new LevelView object with initial health
    }

    /**
     * Checks whether the game is over in LevelTwo based on the player's status or kill count.
     * If the user's plane is destroyed, the game is lost.
     * If the user has enough kills, the game advances to the next level.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) { // If the user's plane is destroyed
            loseGame(); // Trigger the lose game condition
        } else if (getUserKillCount() >= KILLS_TO_ADVANCE) { // If the user has killed enough enemies to advance
            winGame(); // Trigger the win game condition
        }
    }
}
