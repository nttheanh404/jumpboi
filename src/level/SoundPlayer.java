package level;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundPlayer {

    private Clip clip;
    private FloatControl volumeControl;

    // Method to play sound from a resource path
    public void playSound(String resourcePath) {
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

            // Get the control for volume adjustment
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // Start playing the sound
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to set volume
    public void setVolume(float volume) {
        if (volumeControl != null) {
            volumeControl.setValue(volume); // Reduce volume by a number of decibels
        }
    }

    // Method to stop the sound
    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}



