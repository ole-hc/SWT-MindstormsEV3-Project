package org.swtthm.bandit;

import ev3dev.actuators.LCD;
import ev3dev.actuators.LCDJessie;
import ev3dev.actuators.LCDStretch;
import ev3dev.actuators.Sound;
import ev3dev.utils.JarResource;
import lejos.hardware.lcd.GraphicsLCD;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class BrickOutput {
    private Sound sound;
    private GraphicsLCD lcd;

    public BrickOutput(){
        sound = Sound.getInstance();
        sound.setVolume(100);

        lcd = LCDJessie.getInstance();
    }

    public void outputWiningSound() {
    }

    public void outputStartScreen() {
        lcd.drawString("Zieh am Hebel", 10,10, 10);
    }

    public void outputWinningScreen() {
    }

    public void outputNoMoney() {
    }

    public void outputLosingSound() {
        String filePathWiningSound = "data/WinningSound.wav";

        try {
            sound.playSample(getFile(filePathWiningSound));

        }catch (Exception e){
            System.out.println("Couldn't export sound file");
        }
    }

    public void outputLosingScreen() {
    }

    public File getFile(String filePath) throws IOException {
        // Ressource als InputStream laden
        InputStream inputStream = getClass().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("[BRICKOUTPUT] Could not find sound file");
        }

        // Temporäre Datei erstellen
        File tempFile = Files.createTempFile("Sound", ".wav").toFile();
        tempFile.deleteOnExit(); // wird beim Beenden automatisch gelöscht

        // InputStream in die temporäre Datei kopieren
        Files.copy(inputStream, tempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        return tempFile;
    }
}
