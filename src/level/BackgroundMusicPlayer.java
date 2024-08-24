package level;


import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BackgroundMusicPlayer {

    private Clip clip;

    // Method to play background music from a resource path
    public void playBackgroundMusic(String resourcePath) {
        try {
            // Get the audio input stream from the resource path
            InputStream audioSrc = getClass().getResourceAsStream(resourcePath);
            if (audioSrc == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            // Add a buffer around the InputStream
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);

            // Get a clip resource
            clip = AudioSystem.getClip();

            // Open the audio input stream
            clip.open(audioInputStream);

            // Loop the clip continuously
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            // Start playing the sound
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to stop the background music
    public void stopBackgroundMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Method to set volume
    public void setVolume(float volume) {
        if (clip != null) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume); // Set volume level (decibels)
        }
    }
}
