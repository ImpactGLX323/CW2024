package com.example.demo;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * This class handles the background audio playback for the game.
 * It provides methods to play, stop, and adjust the volume of the background music.
 */
public class Audio {

    // MediaPlayer instance to play background music
    private MediaPlayer backgroundMusicPlayer;

    /**
     * Plays background music from the specified file.
     *
     * @param musicFileName the name of the music file located in the classpath
     */
    public void playBackgroundMusic(String musicFileName) {
        try {
            // Get the file path of the music file from the classpath
            String filePath = getClass().getClassLoader().getResource(musicFileName).toExternalForm();

            // If the file is not found, throw an exception
            if (filePath == null) {
                throw new Exception("Music file not found: " + musicFileName);
            }

            // Create a new Media object with the file path
            Media media = new Media(filePath);

            // Create a new MediaPlayer for the media file
            backgroundMusicPlayer = new MediaPlayer(media);

            // Set the music to loop indefinitely
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);

            // Set the volume of the music to full (1.0)
            backgroundMusicPlayer.setVolume(1.0);

            // Start playing the background music
            backgroundMusicPlayer.play();
        } catch (Exception e) {
            // If an error occurs while loading or playing the music, print the error message
            System.err.println("Error loading or playing background music: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Stops the background music if it is currently playing.
     */
    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            // Stop the music if the MediaPlayer is initialized
            backgroundMusicPlayer.stop();
        }
    }

    /**
     * Sets the volume level of the background music.
     *
     * @param volume the desired volume level (value between 0.0 and 1.0)
     */
    public void setVolume(double volume) {
        if (backgroundMusicPlayer != null) {
            // Set the volume if the MediaPlayer is initialized
            backgroundMusicPlayer.setVolume(volume);
        }
    }
}

