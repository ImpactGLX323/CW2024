package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The {@code LevelParent} class serves as an abstract base class for all game levels.
 * It manages the background, units, timeline, and scene transitions between levels.
 */
public abstract class LevelParent {

    private static final String BACKGROUND_MUSIC_PATH = "/com/example/demo/audio/BackgroundMusic.mp3";
    private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
    private static final int MILLISECOND_DELAY = 50;
    protected static final int KILLS_TO_ADVANCE = 100;

    private final double screenHeight;
    private final double screenWidth;
    private final double enemyMaximumYPosition;

    private final Group root;
    private final Timeline timeline;
    private final UserPlane user;
    private final Scene scene;
    private final ImageView background;
    private final Stage stage;
    private final Audio audio;

    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;

    private int currentNumberOfEnemies;
    private final LevelView levelView;
    private boolean isTransitioning = false;

    /**
     * Constructs a {@code LevelParent} instance with the specified parameters.
     *
     * @param backgroundImageName the file path to the background image
     * @param screenHeight the height of the game screen
     * @param screenWidth the width of the game screen
     * @param playerInitialHealth the initial health of the player's character
     * @param stage the JavaFX {@code Stage} for displaying the game level
     */
    public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, Stage stage) {
        this.root = new Group();
        this.scene = new Scene(root, screenWidth, screenHeight);
        this.timeline = new Timeline();
        this.user = new UserPlane(playerInitialHealth);
        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
        this.audio = new Audio();

        this.background = new ImageView(loadImage(backgroundImageName));
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
        this.levelView = instantiateLevelView();
        this.currentNumberOfEnemies = 0;
        this.stage = stage;

        initializeTimeline();
        friendlyUnits.add(user);
    }

    // Abstract methods to be implemented by subclasses
    /**
     * Initializes the friendly units, such as the player's character, for the level.
     */
    protected abstract void initializeFriendlyUnits();

    /**
     * Checks the game's state to determine if the game is over.
     * Implement win or lose conditions in this method.
     */
    protected abstract void checkIfGameOver();

    /**
     * Spawns enemy units in the level. The logic for enemy spawning is implemented in subclasses.
     */
    protected abstract void spawnEnemyUnits();

    /**
     * Instantiates the {@code LevelView}, which represents the level's visual components.
     *
     * @return the {@code LevelView} for the level
     */
    protected abstract LevelView instantiateLevelView();

    /**
     * Initializes the scene for the level.
     * 
     * @return the {@code Scene} for the level
     */
    public Scene initializeScene() {
        initializeBackground();
        initializeFriendlyUnits();
        levelView.showHeartDisplay();
        return scene;
    }

    /**
     * Starts the game by playing the timeline and background music.
     */
    public void startGame() {
        background.requestFocus();
        timeline.play();
        audio.playBackgroundMusic(BACKGROUND_MUSIC_PATH);
    }

    /**
     * Resets the current level by clearing all elements, reinitializing units, and restarting the game.
     */
    public void resetLevel() {
        friendlyUnits.clear();
        enemyUnits.clear();
        userProjectiles.clear();
        enemyProjectiles.clear();
        currentNumberOfEnemies = 0;
        timeline.stop();
        root.getChildren().clear();
        root.getChildren().add(background);
        initializeFriendlyUnits();
        levelView.resetLevelView();

        // Debugging - Check that resetLevel is being invoked
        System.out.println("resetLevel invoked!");

        // Create the restart button
        javafx.scene.control.Button restartButton = new javafx.scene.control.Button("TRY AGAIN");
        restartButton.setLayoutX(screenWidth / 2 - 50);
        restartButton.setLayoutY(screenHeight / 2 + 50);
        restartButton.setFocusTraversable(true);
        restartButton.setOnAction(e -> {
            System.out.println("Restart Button Clicked!");
            resetLevel();
            startGame();
        });

        Platform.runLater(() -> {
            root.getChildren().add(restartButton);
            root.requestLayout();
        });
    }

    /**
     * Transitions to the specified next level.
     *
     * @param nextLevel the next level to transition to
     */
    public void goToNextLevel(LevelParent nextLevel) {
        if (!isTransitioning) {
            isTransitioning = true;
            try {
                System.out.println("Transitioning to the next level...");

                timeline.stop();
                root.getChildren().clear();

                Scene nextScene = nextLevel.initializeScene();
                if (nextScene == null) {
                    throw new IllegalStateException("Next level scene is null!");
                }

                stage.setScene(nextScene);
                nextLevel.startGame();

                System.out.println("Transition to next level successful.");
            } catch (Exception e) {
                System.err.println("Error during level transition: " + e.getMessage());
                e.printStackTrace();
            } finally {
                isTransitioning = false;
            }
        } else {
            System.out.println("Transition already in progress.");
        }
    }

    /**
     * Initializes the game's timeline, which controls the game loop.
     */
    private void initializeTimeline() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
        timeline.getKeyFrames().add(gameLoop);
    }
/**
     * Initializes the background of the level, sets up key listeners for user input, 
     * and adds the background image to the scene graph.
     */
    private void initializeBackground() {
        background.setFocusTraversable(true);
        background.setFitHeight(screenHeight);
        background.setFitWidth(screenWidth);
        background.setOnKeyPressed(e -> {
            KeyCode kc = e.getCode();
            if (kc == KeyCode.UP) user.moveUp();
            if (kc == KeyCode.DOWN) user.moveDown();
            if (kc == KeyCode.SPACE) fireProjectile();
        });

        background.setOnKeyReleased(e -> {
            KeyCode kc = e.getCode();
            if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
        });

        root.getChildren().add(background);
    }

    /**
     * Fires a projectile from the user's plane and adds it to the scene and 
     * tracking list of user projectiles.
     */
    private void fireProjectile() {
        ActiveActorDestructible projectile = user.fireProjectile();
        root.getChildren().add(projectile);
        userProjectiles.add(projectile);
    }

    /**
     * Updates the game scene, including spawning enemy units, updating actors, 
     * handling collisions, and checking for game-over conditions.
     */
    private void updateScene() {
        spawnEnemyUnits();
        updateActors();
        handleCollisions(userProjectiles, enemyUnits);
        handleCollisions(enemyProjectiles, friendlyUnits);
        handleCollisions(friendlyUnits, enemyUnits);
        handleEnemyPenetration();
        removeAllDestroyedActors();
        updateKillCount();
        updateLevelView();
        checkIfGameOver();
    }

    /**
     * Updates the state of all actors in the level, including friendly units, 
     * enemy units, and projectiles.
     */
    private void updateActors() {
        friendlyUnits.forEach(plane -> plane.updateActor());
        enemyUnits.forEach(enemy -> enemy.updateActor());
        userProjectiles.forEach(projectile -> projectile.updateActor());
        enemyProjectiles.forEach(projectile -> projectile.updateActor());
    }

    /**
     * Handles collisions between two lists of actors, applying damage and 
     * removing destroyed actors from the scene.
     *
     * @param projectiles the list of projectiles to check for collisions
     * @param actors the list of actors to check for collisions
     */
    private void handleCollisions(List<ActiveActorDestructible> projectiles, List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> toRemove = new ArrayList<>();
        for (ActiveActorDestructible projectile : projectiles) {
            if (projectile.isDestroyed()) continue; // Skip if projectile is destroyed
            for (ActiveActorDestructible actor : actors) {
                if (actor.isDestroyed()) continue; // Skip if actor is destroyed
                if (projectile.getBoundsInParent().intersects(actor.getBoundsInParent())) {
                    projectile.takeDamage();
                    actor.takeDamage();
                    if (projectile.isDestroyed()) toRemove.add(projectile);
                    if (actor.isDestroyed()) toRemove.add(actor);
                }
            }
        }
        projectiles.removeAll(toRemove);
        actors.removeAll(toRemove);
        root.getChildren().removeAll(toRemove);
    }

    /**
     * Handles the case where enemy units penetrate the player's defenses, 
     * dealing damage to the user and removing the enemy from the scene.
     */
    private void handleEnemyPenetration() {
        List<ActiveActorDestructible> toDestroy = new ArrayList<>();
        for (ActiveActorDestructible enemy : enemyUnits) {
            if (enemyHasPenetratedDefenses(enemy)) {
                user.takeDamage();
                enemy.destroy();
                toDestroy.add(enemy);
            }
        }
        enemyUnits.removeAll(toDestroy); // Remove penetrated enemies
    }

    /**
     * Removes all actors from the scene and their respective tracking lists 
     * if they are marked as destroyed.
     */
    private void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(enemyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
    }

    /**
     * Removes destroyed actors from a specific list and the scene graph.
     *
     * @param actors the list of actors to remove
     */
    private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream()
            .filter(ActiveActorDestructible::isDestroyed)
            .collect(Collectors.toList());
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }

    /**
     * Updates the kill count for the user based on the number of destroyed enemies.
     */
    private void updateKillCount() {
        int killsToAdd = Math.max(0, currentNumberOfEnemies - enemyUnits.size());
        for (int i = 0; i < killsToAdd; i++) {
            user.incrementKillCount();
        }
        currentNumberOfEnemies = enemyUnits.size();
    }

    /**
     * Updates the level view to reflect the user's current health.
     */
    private void updateLevelView() {
        levelView.removeHearts(user.getHealth());
    }

    /**
     * Determines if an enemy has penetrated the player's defenses by checking 
     * if the enemy's position exceeds the screen boundaries.
     *
     * @param enemy the enemy actor to check
     * @return true if the enemy has penetrated defenses, false otherwise
     */
    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX()) > screenWidth;
    }

    /**
     * Handles the game-over condition when the player wins the level.
     * Displays a win image and provides buttons for restarting or transitioning 
     * to the next level.
     */
    protected void winGame() {
        timeline.stop();
        levelView.showWinImage();
        audio.stopBackgroundMusic();

        // Create "Next Level" button
        javafx.scene.control.Button nextLevelButton = new javafx.scene.control.Button("Next Level");
        nextLevelButton.setLayoutX(screenWidth / 2 - 100);
        nextLevelButton.setLayoutY(screenHeight / 2 + 50);
        nextLevelButton.setFocusTraversable(true); // Ensure button is focusable
        nextLevelButton.setOnAction(e -> {
            System.out.println("Next Level Button Clicked");
            LevelParent nextLevel = getNextLevel();
            if (nextLevel != null) {
                goToNextLevel(nextLevel);
            } else {
                System.out.println("No next level. Game completed!");
                askForRestart();
            }
        });

        // Create "Restart" button
        javafx.scene.control.Button restartButton = new javafx.scene.control.Button("TRY AGAIN");
        restartButton.setLayoutX(screenWidth / 2 + 100);
        restartButton.setLayoutY(screenHeight / 2 + 50);
        restartButton.setFocusTraversable(true); // Ensure button is focusable
        restartButton.setOnAction(e -> {
            System.out.println("Restart Button Clicked");
            resetLevel();
            startGame();
        });

        // Add buttons to the root separately
        javafx.application.Platform.runLater(() -> {
            root.getChildren().add(nextLevelButton);  // Add the next level button
            root.getChildren().add(restartButton);    // Add the restart button
            root.requestLayout();  // Refresh the layout to display the buttons
        });
    }

    /**
     * Determines the next level based on the current level. 
     * This method should be overridden by subclasses to define level transitions.
     *
     * @return the next {@code LevelParent}, or null if there are no more levels
     */
    protected LevelParent getNextLevel() {
        if (this instanceof LevelOne) {
            return new LevelTwo(
                "/com/example/demo/images/background2.jpg",
                getScreenHeight(),
                getScreenWidth(),
                5,
                getStage()
            );
        }
        return null;
    }


    /**
     * Handles the game-over condition when the player loses the level.
     * Displays a game-over image and provides a button to restart the level.
     */
    protected void loseGame() {
        timeline.stop();
        levelView.showGameOverImage();
        audio.stopBackgroundMusic();

        // Create a button to restart the game
        javafx.scene.control.Button restartButton = new javafx.scene.control.Button("TRY AGAIN");
        restartButton.setLayoutX(screenWidth / 2 - 50);
        restartButton.setLayoutY(screenHeight / 2 + 50);
        restartButton.setFocusTraversable(true); // Ensure the button is interactive
        restartButton.setOnAction(e -> {
            System.out.println("Restart Button Clicked");
            resetLevel();
            startGame();
        });

        javafx.application.Platform.runLater(() -> {
            root.getChildren().add(restartButton);
            root.requestLayout(); // Refresh the layout to display the button
        });
    }

    /**
     * Resets the level and starts the game. This method is called when restarting the level.
     */
    private void askForRestart() {
        resetLevel();
        startGame();
    }

    /**
     * Loads an image from the specified path. If loading fails, a default image is returned.
     *
     * @param path the relative path to the image file
     * @return the loaded {@code Image} object, or a default image if loading fails
     */
    private Image loadImage(String path) {
        try {
            return new Image(getClass().getResource(path).toExternalForm());
        } catch (Exception e) {
            System.err.println("Failed to load image: " + path + ". Using default image.");
            return new Image("/com/example/demo/images/default.png");
        }
    }

    /**
     * Retrieves the user's plane object.
     *
     * @return the {@code UserPlane} representing the user's plane
     */
    protected UserPlane getUser() {
        return user;
    }

    /**
     * Retrieves the root group of the scene graph.
     *
     * @return the {@code Group} representing the root node of the scene graph
     */
    protected Group getRoot() {
        return root;
    }

    /**
     * Retrieves the current number of enemies in the level.
     *
     * @return the number of enemy units
     */
    protected int getCurrentNumberOfEnemies() {
        return enemyUnits.size();
    }

    /**
     * Adds an enemy unit to the level and includes it in the scene graph.
     *
     * @param enemy the {@code ActiveActorDestructible} representing the enemy unit
     */
    protected void addEnemyUnit(ActiveActorDestructible enemy) {
        enemyUnits.add(enemy);
        root.getChildren().add(enemy);
    }

    /**
     * Generates enemy fire for all enemy units capable of firing.
     * Projectiles are added to the scene and tracked.
     */
    public void generateEnemyFire() {
        for (ActiveActorDestructible enemy : enemyUnits) {
            // Use pattern matching for instanceof
            if (enemy instanceof FighterPlane plane && plane.canFire()) {
                ActiveActorDestructible projectile = plane.fireProjectile();
                if (projectile != null) {
                    spawnEnemyProjectile(projectile);
                }
            }
        }
    }
    
    /**
     * Spawns an enemy projectile by adding it to the scene and tracking list.
     *
     * @param projectile the {@code ActiveActorDestructible} representing the enemy projectile
     */
    private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null && !projectile.isDestroyed()) {
            root.getChildren().add(projectile); // Add projectile to the scene
            enemyProjectiles.add(projectile);   // Track the projectile
        }
    }

    /**
     * Retrieves the maximum Y-position allowed for enemy units on the screen.
     *
     * @return the maximum Y-position for enemy units
     */
    protected double getEnemyMaximumYPosition() {
        return enemyMaximumYPosition;
    }

    /**
     * Retrieves the width of the game screen.
     *
     * @return the screen width
     */
    protected double getScreenWidth() {
        return screenWidth;
    }

    /**
     * Retrieves the height of the game screen.
     *
     * @return the screen height
     */
    protected double getScreenHeight() {
        return screenHeight;
    }

    /**
     * Checks whether the user's plane is destroyed.
     *
     * @return {@code true} if the user's plane is destroyed, otherwise {@code false}
     */
    protected boolean userIsDestroyed() {
        return user.isDestroyed();
    }

    /**
     * Retrieves the primary stage of the application.
     *
     * @return the {@code Stage} representing the main application window
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Retrieves the number of kills made by the user.
     *
     * @return the user's kill count
     */
    public int getUserKillCount() {
        return user.getKillCount();
    }

    /**
     * Displays an error message in the console and optionally logs the exception.
     *
     * @param message the error message to display
     * @param e the exception to log, or {@code null} if no exception is provided
     */
    protected void showError(String message, Exception e){
        System.err.println("ERROR: " + message );
        if (e!=null){
            e.printStackTrace();
        }
    }
}
