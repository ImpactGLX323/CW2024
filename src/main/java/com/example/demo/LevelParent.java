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

public abstract class LevelParent {

    private static final String BACKGROUND_MUSIC_PATH = "/com/example/demo/audio/BackgroundMusic.mp3";
    private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
    private static final int MILLISECOND_DELAY = 50;
    private final double screenHeight;
    private final double screenWidth;
    private final double enemyMaximumYPosition;
    protected static final int KILLS_TO_ADVANCE = 100;

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

    public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, Stage stage) {
        this.root = new Group();
        this.scene = new Scene(root, screenWidth, screenHeight);
        this.timeline = new Timeline();
        this.user = new UserPlane(playerInitialHealth);
        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
        this.audio = new Audio(); //audio

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
    protected abstract void initializeFriendlyUnits();
    protected abstract void checkIfGameOver();
    protected abstract void spawnEnemyUnits();
    protected abstract LevelView instantiateLevelView();

    public Scene initializeScene() {
        initializeBackground();
        initializeFriendlyUnits();
        levelView.showHeartDisplay();
        return scene;
    }

    public void startGame() {
        background.requestFocus();
        timeline.play();
        audio.playBackgroundMusic(BACKGROUND_MUSIC_PATH);
    }

    public void resetLevel() {
        friendlyUnits.clear();
        enemyUnits.clear();
        userProjectiles.clear();
        enemyProjectiles.clear();
        currentNumberOfEnemies = 0;
        timeline.stop();  // Stop the timeline
        root.getChildren().clear(); // Clears all existing UI elements
        root.getChildren().add(background); // Add background again to reset the scene
        initializeFriendlyUnits(); // Initialize units
        levelView.resetLevelView(); // Reset the UI components related to the level view
    
        // Debugging - Check that resetLevel is being invoked
        System.out.println("resetLevel invoked!");
    
        // Create the restart button
        javafx.scene.control.Button restartButton = new javafx.scene.control.Button("TRY AGAIN");
        restartButton.setLayoutX(screenWidth / 2 - 50);
        restartButton.setLayoutY(screenHeight / 2 + 50);
        restartButton.setFocusTraversable(true); // Ensure button is focusable
        restartButton.setOnAction(e -> {
            System.out.println("Restart Button Clicked!");
            resetLevel();  // Reset the level and restart the game
            startGame();   // Start the game after reset
        });
    
        // Add the restart button to the root
        Platform.runLater(() -> {
            root.getChildren().add(restartButton);  // Add the restart button
            root.requestLayout();  // Refresh the layout to display the button
        });
    }
    

    public void goToNextLevel(LevelParent nextLevel) {
        if (!isTransitioning) {
            isTransitioning = true;
            try {
                System.out.println("Transitioning to the next level...");
    
                // Stop the current timeline and clear the root
                timeline.stop();
                root.getChildren().clear();
    
                // Ensure the next level initializes properly
                Scene nextScene = nextLevel.initializeScene();
                if (nextScene == null) {
                    throw new IllegalStateException("Next level scene is null!");
                }
    
                // Set the next scene on the stage
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

    private void initializeTimeline() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
        timeline.getKeyFrames().add(gameLoop);
    }

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
        
    private void fireProjectile() {
        ActiveActorDestructible projectile = user.fireProjectile();
        root.getChildren().add(projectile);
        userProjectiles.add(projectile);
    }

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

    private void updateActors() {
        friendlyUnits.forEach(plane -> plane.updateActor());
        enemyUnits.forEach(enemy -> enemy.updateActor());
        userProjectiles.forEach(projectile -> projectile.updateActor());
        enemyProjectiles.forEach(projectile -> projectile.updateActor());
    }

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

    private void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(enemyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
    }

    private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream()
            .filter(ActiveActorDestructible::isDestroyed)
            .collect(Collectors.toList());
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }

    private void updateKillCount() {
        int killsToAdd = Math.max(0, currentNumberOfEnemies - enemyUnits.size());
            for (int i = 0; i <killsToAdd; i++) {
                user.incrementKillCount();
            }
        currentNumberOfEnemies = enemyUnits.size();
    }

    private void updateLevelView() {
        levelView.removeHearts(user.getHealth());
    }

    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX()) > screenWidth;
    }

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

    private void askForRestart() {
        resetLevel();
        startGame();
    }

    private Image loadImage(String path) {
        try {
            return new Image(getClass().getResource(path).toExternalForm());
        } catch (Exception e) {
            System.err.println("Failed to load image: " + path + ". Using default image.");
            return new Image("/com/example/demo/images/default.png");
        }
    }

    protected UserPlane getUser() {
        return user;
    }

    protected Group getRoot() {
        return root;
    }

    protected int getCurrentNumberOfEnemies() {
        return enemyUnits.size();
    }

    protected void addEnemyUnit(ActiveActorDestructible enemy) {
        enemyUnits.add(enemy);
        root.getChildren().add(enemy);
    }

    public void generateEnemyFire() {
        long currentTime = System.currentTimeMillis();
        for (ActiveActorDestructible enemy : enemyUnits) {
            if (enemy instanceof FighterPlane) {
                FighterPlane plane = (FighterPlane) enemy;
                // Check if the plane can fire
                if (plane.canFire()) {
                    ActiveActorDestructible projectile = plane.fireProjectile();
                    if (projectile != null) {
                        spawnEnemyProjectile(projectile);
                    }
                }
            }
        }
    }
    
    private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null && !projectile.isDestroyed()) {
            root.getChildren().add(projectile); // Add projectile to the scene
            enemyProjectiles.add(projectile);   // Track the projectile
        }
    }

    protected double getEnemyMaximumYPosition() {
        return enemyMaximumYPosition;
    }

    protected double getScreenWidth() {
        return screenWidth;
    }

    protected double getScreenHeight() {
        return screenHeight;
    }

    protected boolean userIsDestroyed() {
        return user.isDestroyed();
    }

    public Stage getStage() {
        return stage;
    }

    public int getUserKillCount() {
        return user.getKillCount();
    }

    protected void showError(String message, Exception e){
        System.err.println("ERROR: " + message );
        if (e!=null){
            e.printStackTrace();
        }
    }
}