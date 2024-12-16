package com.example.demo;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Audio {
    private MediaPlayer backgroundMusicPlayer;

    public void playBackgroundMusic(String musicFileName) {
        try {
            String filePath = getClass().getClassLoader().getResource(musicFileName).toExternalForm();

            if (filePath == null) {
                throw new Exception("Music file not found: " + musicFileName);
            }

            Media media = new Media(filePath);
            backgroundMusicPlayer = new MediaPlayer(media);
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundMusicPlayer.setVolume(1.0);
            backgroundMusicPlayer.play();
        } catch (Exception e) {
            System.err.println("Error loading or playing background music: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }

    public void setVolume(double volume) {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(volume);
        }
    }
}
