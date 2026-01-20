package org.swtthm.bandit.controller;

import ev3dev.actuators.LCD;
import ev3dev.actuators.Sound;
import ev3dev.hardware.EV3DevDistro;
import ev3dev.hardware.EV3DevDistros;
import ev3dev.utils.Brickman;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;
import org.swtthm.bandit.gateway.InMemoryGateway;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class BrickOutputController {
    private final Sound sound;
    private final GraphicsLCD lcd;
    private final InMemoryGateway gateway;

    //Die einzelnen Pfade zu den Bild- und Sounddateien werden anfänglich deklariert um die Les- und Wartbarkeit zu erhöhen
    private final String WINNING_SCREEN_PATH = "/data/WinScreen.png";
    private final String LOSING_SCREEN_PATH = "/data/LoseScreen.png";
    private final String WINNING_SOUND_PATH = "/data/WinningSound.wav";
    private final String BASIC_SCREEN_PATH = "/data/BasicScreen.png";
    private final String NO_MONEY_PATH = "/data/ThrowInMoney.png";
    private final String BASIC_PATH = "/data/";
    private final String EMPTY_PATH = "/data/empty.png";

    public BrickOutputController(InMemoryGateway gateway){
        //Mit der Funktion getInstance werden die verwendeten Systemressourcen geladen
        sound = Sound.getInstance();
        lcd = LCD.getInstance();
        //Lautstärke des Bausteins auf 100%
        sound.setVolume(100);

        this.gateway = gateway;

        //Veraltete Ansteuermöglichkeit für das Display ausschalten (Wurde in allen Beispielen der Bibliothek so empfohlen)
        if(EV3DevDistros.getInstance().getDistro().equals(EV3DevDistro.JESSIE)) {
            Brickman.disable();
        }

        //Nach dem Initialisieren alle möglichen Bilder und Sounds ausgeben, da wir die Erfahrung gemacht haben, dass diese beim ersten Mal laden viel länger dauern
        outputLosingScreen();
        outputWinningScreen();
        outputWiningSound();
    }

    public void outputWiningSound() {
        //Ausgabe von einem Sound, beim Gewinnen des Spiels
        try {
            sound.playSample(getFile(WINNING_SOUND_PATH));

        }catch (Exception e){
            System.out.println("Couldn't export sound file");
        }
    }

    public void outputBasicScreen() {
        //Ausgabe des Standardbildschirms, auf welchem der Name des Roboters, sowie die Anzahl an verbleibenden Spielen angezeigt wird
        //Mit der funktion clear löscht man alles auf dem Bildschirm und nach dem Laden eines Bildes muss die Funktion refresch aufgerufen werden
        lcd.clear();
        lcd.drawImage(loadImage(BASIC_SCREEN_PATH), 0,0,0);
        lcd.refresh();
        outputGameCount(gateway.getSpins());
    }

    public void outputGameCount(int count){
        //Ausgabe der Anzahl an übrigen Spielen
        if (count != 0){
            //Wenn die Anzahl an Spielen über null ist, wird die Ausgabe "Geld einwerfen" wieder entfernt
            lcd.drawImage(loadImage(EMPTY_PATH), 25, 100, 0);
        }else {
            //Falls die Anzahl an Spielen null ist, wird "Geld einwerfen" ausgegeben
            outputNoMoney();
        }

        lcd.setColor(0,0,0);

        //Der Bereich in welchen die Zahlen geschrieben werden sollen wird gesäubert, da sonst Zahlen übereinander geschrieben würden
        lcd.drawImage(loadImage(EMPTY_PATH ), 135,62,0);

        int x = 135;
        //Aus der Anzahl der Umdrehungen werden Chars erstellt, da die Darstellung über selbst erstellte Bilder erfolgt
        char[] charArray = String.valueOf(count).toCharArray();

        for(char c : charArray){
            //Zu den entsprechenden Chars werden die passenden Bilder der Zahlen geladen und angezeigt. Sobald die Zahl zwei oder dreistellig wird, wird die nächste Zahl 14 Pixel weiter rechts angezeigt, um nicht direkt über der anderen Zahl angezeigt zu werden.
            lcd.drawImage(loadImage(BASIC_PATH + c + ".png"), x,62,0);
            System.out.println("Should print char");
            x += 14;
        }
        lcd.refresh();
    }

    public void outputWinningScreen() {
        //Ausgabe des Sieg-Bildschirms. Im Anschluss wird der Siegsound abgespielt und danach wird der Standartbildschirm wieder geladen
        lcd.clear();
        lcd.drawImage(loadImage(WINNING_SCREEN_PATH), 25,50,0);
        lcd.refresh();
        outputWiningSound();
        outputBasicScreen();
    }

    public void outputNoMoney() {
        //Ausgabe des Schriftzugs: "Geld einwerfen"
        lcd.drawImage(loadImage(NO_MONEY_PATH), 25, 100, 0);
        lcd.refresh();
    }

    public void outputLosingScreen() {
        //Ausgabe bei einem verlorenem Spiel. Nach einer halben Sekunde wird wieder der Startbildschirm angezeigt
        lcd.drawImage(loadImage(LOSING_SCREEN_PATH), 20,20,0);
        lcd.refresh();
        Delay.msDelay(500);
        outputBasicScreen();
    }

    //Hilfsfunktion zum Laden einer Sounddatei. Wird benötigt, da man eine Datei in einer Jar-Datei nicht direkt laden kann. (Mit hilfe von KI geschrieben)
    private File getFile(String filePath) throws IOException {
        // Ressource als InputStream laden
        InputStream inputStream = getClass().getResourceAsStream(filePath);
        //Falls Datei nicht existiert wird ein Fehler geworfen
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

    //Hilfsfunktion zum Laden einer Bilddatei. Die Funktion zum Ansteuern des Displays benötigt eine Variable vom Typ BufferedImage. Wird benötigt, da man eine Datei in einer Jar-Datei nicht direkt laden kann. (Mit hilfe von KI geschrieben)
    private BufferedImage loadImage(String imagePath) {
        try {
            // Ressource als InputStream laden
            InputStream inputStream = getClass().getResourceAsStream(imagePath);
            //Falls Datei nicht existiert wird ein Fehler geworfen
            if (inputStream == null) {
                throw new IllegalArgumentException("Bild nicht gefunden!");
            }

            // BufferedImage aus InputStream erstellen
            BufferedImage image = ImageIO.read(inputStream);
            return image;

            //Falls fehler beim Schreiben oder Laden der Datei entstehen wird eine Ausgabe getätigt
        } catch (IOException e) {
            System.out.print("FailedToLoadImage");
            return null;
        }
    }
}
