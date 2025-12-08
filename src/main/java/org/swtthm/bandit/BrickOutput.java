package org.swtthm.bandit;

import ev3dev.actuators.Sound;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class BrickOutput {

    private Sound sound;

    public BrickOutput(){
        sound = Sound.getInstance();
        sound.setVolume(100);
    }

    public void outputWiningSound() {
    }

    public void outputWinningScreen() {
    }

    public void outputNoMoney() {
    }

    public void outputLosingSound() {
        String filePathWiningSound = "data/WinningSound.wav";

        try {
            //JarResource.export(filePathWiningSound);
            //File file = new File(filePathWiningSound);
            sound.playSample(getSoundFile());

        }catch (IOException e){
            System.out.println("Couldn't export sound file");
        }
    }

    public void outputLosingScreen() {
    }

    public File getSoundFile() throws IOException {
        // Ressource als InputStream laden
        InputStream inputStream = getClass().getResourceAsStream("/data/WinningSound.wav");
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
