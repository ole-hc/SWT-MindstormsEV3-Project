package org.swtthm.bandit;

import ev3dev.actuators.LCD;
import ev3dev.actuators.LCDJessie;
import ev3dev.actuators.LCDStretch;
import ev3dev.actuators.Sound;
import ev3dev.hardware.EV3DevDistro;
import ev3dev.hardware.EV3DevDistros;
import ev3dev.utils.Brickman;
import ev3dev.utils.JarResource;
import lejos.hardware.lcd.GraphicsLCD;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class BrickOutput {
    private final Sound sound;
    private final GraphicsLCD lcd;
    private String WINNING_SCREEN_PATH = "/data/WinScreen.png";
    private String LOSING_SCREEN_PATH = "/data/LoseScreen.png";
    private String WINNING_SOUND_PATH = "/data/WinningSound.wav";

    public BrickOutput(){
        sound = Sound.getInstance();
        sound.setVolume(100);

        lcd = LCD.getInstance();

        if(EV3DevDistros.getInstance().getDistro().equals(EV3DevDistro.JESSIE)) {
            Brickman.disable();
        }
    }

    public void outputWiningSound() {
        try {
            sound.playSample(getFile(WINNING_SOUND_PATH));

        }catch (Exception e){
            System.out.println("Couldn't export sound file");
        }
    }

    public void outputStartScreen() {
        // lcd.drawString("Zieh am Hebel", 10,10, 10);

        lcd.setColor(255,255,255);
        lcd.fillRect(0,0, lcd.getWidth(), lcd.getHeight());
        lcd.refresh();
    }

    public void outputWinningScreen() {
        lcd.setColor(0,0,0);
        lcd.drawImage(loadImage(WINNING_SCREEN_PATH), 0,0,0);
        lcd.refresh();
    }

    public void outputNoMoney() {
    }

    public void outputLosingSound() {

    }

    public void outputLosingScreen() {
        lcd.setColor(0,0,0);
        lcd.drawImage(loadImage(LOSING_SCREEN_PATH), 0,0,0);
        lcd.refresh();
    }

    public void updateCount(){

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

    public BufferedImage loadImage(String imagePath) {
        try {
            // Ressource als InputStream laden
            InputStream inputStream = getClass().getResourceAsStream(imagePath);
            if (inputStream == null) {
                throw new IllegalArgumentException("Bild nicht gefunden!");
            }

            // BufferedImage aus InputStream erstellen
            BufferedImage image = ImageIO.read(inputStream);
            return image;

        } catch (IOException e) {
            System.out.print("FailedToLoadImage");
            return null;
        }
    }
}
