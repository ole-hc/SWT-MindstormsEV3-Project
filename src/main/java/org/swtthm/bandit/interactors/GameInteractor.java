package org.swtthm.bandit.interactors;

import org.swtthm.bandit.controller.BrickOutputController;
import org.swtthm.bandit.controller.CoinMotorController;
import org.swtthm.bandit.controller.RollMotorController;
import org.swtthm.bandit.enums.Picture;
import org.swtthm.bandit.gateway.InMemoryGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameInteractor {
    private final Random random;
    private final CoinMotorController coinMotor;
    private final BrickOutputController brickOutput;
    private final InMemoryGateway inMemoryGateway;
    private final RollMotorController motorController;
    private int loses;

    public GameInteractor(CoinMotorController coinMotor, BrickOutputController brickOutput, InMemoryGateway inMemoryGateway, RollMotorController motorController){
        random = new Random();
        this.coinMotor = coinMotor;
        this.brickOutput = brickOutput;
        this.inMemoryGateway = inMemoryGateway;
        this.motorController = motorController;

        //Counter für die Anzahl an verlorenen Spielen am Stück, um die Gewinnwahrscheinlichkeiten anzupassen
        loses = 0;
    }

    public void startGame() {
        System.out.println("[INTERACTOR] Game started...");

        //Anzahl an übrigen Umdrehungen aus dem Gateway holen
        int spins = inMemoryGateway.getSpins();
        System.out.println("[INTERACTOR] Current spins: " + spins);

        //Falls mehr als null Umdrehungen vorhanden sind, wird das Spiel fortgesetzt
        if(spins > 0) {
            inMemoryGateway.setSpins(spins - 1);

            List<Picture> picture = new ArrayList<>();
            //Spielbild generieren und auf dem Display ausgeben
            generatePicture(picture);
            System.out.println("[INTERACTOR] Generated picture: ");
            printArray(picture);

            //Motoren ansteuern und auf das vorab generierte Spielbild drehen
            System.out.println("[INTERACTOR] Moving motors...");
            motorController.moveMotors(picture, inMemoryGateway.getLastPicture(), true);

            //Nach dem Drehen der Motoren das Bild im Gateway updaten, um eine referenz für den nächsten Durchgang zu haben
            inMemoryGateway.setLastPicture(picture);

            //Anhand des Spielbildes ermitteln, ob das Spiel gewonnen oder verloren wurde
            if (determineWin(picture)) {
                //Bei einem Sieg wird die Gewinn-Sequence gestartet
                System.out.println("[INTERACTOR] Trigger winning sequence");
                triggerWinningSequence();
            } else{
                //Bei einer Niederlage wird die Verlier-Sequence gestartet
                System.out.println("[INTERACTOR] Trigger losing sequence");
                triggerLosingSequence();
            }
        }else {
            //Falls keine Umdrehungen mehr übrig sind, wird auf dem Display ausgegeben: "Geld einwerfen"
            System.out.println("[INTERACTOR] Insufficient funds");
            brickOutput.outputNoMoney();
        }
    }

    private void triggerLosingSequence() {
        //Bei einem verlorenem Spiel wird dies auf dem Bildschirm ausgegeben und die Anzahl der verlorenen Spiele am Stück wird hochgezählt
        brickOutput.outputLosingScreen();
        loses += 1;
    }

    private void triggerWinningSequence(){
        //Bei einem Sieg wird dies auf dem Bildschirm angezeigt und das Geld im Münzschlitz wird ausgeworfen
        brickOutput.outputWinningScreen();
        coinMotor.outputMoney();
        //Die Anzahl der verlorenen Spiele am Stück wird hochgezählt
        loses = 0;
    }

    private boolean determineWin(List<Picture> picture){
        //Ermittlung, ob das Spiel gewonnen wurde. Wenn alle drei Bilder gleich sind, handelt es sich um einen Gewinn
        return picture.get(0) == picture.get(1) && picture.get(1) == picture.get(2);
    }

    private void generatePicture(List<Picture> picture) {
        //Generierung des Spielbildes abhängig von der Anzahl der verlorenen Spiele
        if(loses > 15){
            //Ab 15 verlorener Spiele am Stück wird mit einer wahrscheinlichkeit von 50% ein Gewinn-Bild generiert, ansonsten ein zufälliges
            if(random.nextInt(2) == 0){
                generateWiningPicture(picture);
            }else {
                generateRandomPicture(picture);
            }
        } else if (loses > 10) {
            //Ab 10 verlorener Spiele am Stück wird mit einer wahrscheinlichkeit von 25% ein Gewinn-Bild generiert, ansonsten ein zufälliges
            if(random.nextInt(4) == 3){
                generateWiningPicture(picture);
            }else {
                generateRandomPicture(picture);
            }
        }else {
            //Hier wird ein rein zufälliges Bild generiert
            generateRandomPicture(picture);
        }
    }

    private void generateWiningPicture(List<Picture> picture){
        //Hier wird ein Bild generiert, was zufällig dreimal die gleichen Farben hat
        //Mit der Funktion random.nextInt(4) wird eine zufällige Zahl zwischen 0 und 3 generiert und abhängig von dieser Zahl wird die entsprechende Farbe erzeugt
        switch (random.nextInt(4)) {
            case 0:
                picture.add(Picture.RED);
                picture.add(Picture.RED);
                picture.add(Picture.RED);
                break;
            case 1:
                picture.add(Picture.GREEN);
                picture.add(Picture.GREEN);
                picture.add(Picture.GREEN);
                break;
            case 2:
                picture.add(Picture.YELLOW);
                picture.add(Picture.YELLOW);
                picture.add(Picture.YELLOW);
                break;
            case 3:
                picture.add(Picture.BLUE);
                picture.add(Picture.BLUE);
                picture.add(Picture.BLUE);
                break;
        }
    }

    private void generateRandomPicture(List<Picture> picture){
        //Es wird mit einer For-Schleife dreimal hintereinander eine Zahl zwischen 0 und 4 generiert und die entsprechende Farbe wird dem Spielbild hinzugefügt
        for(int i = 0; i < 3; i++) {
            switch (random.nextInt(4)) {
                case 0:
                    picture.add(Picture.RED);
                    break;
                case 1:
                    picture.add(Picture.GREEN);
                    break;
                case 2:
                    picture.add(Picture.YELLOW);
                    break;
                case 3:
                    picture.add(Picture.BLUE);
                    break;
            }
        }
    }

    private void printArray(List<Picture> picture){
        //Eine Hilfsfunktion zum Ausgeben des Spielbildes, da Java ein Listen-Objekt nicht direkt ausgibt
        System.out.print("[INTERACTOR] Picture: ");
        //Es wird über das Array rüber iteriert und jedes einzelne Objekt wird dabei ausgegeben
        for (Picture selectedPicture : picture) {
            System.out.print(selectedPicture + ";");
        }
        System.out.println();
    }
}
