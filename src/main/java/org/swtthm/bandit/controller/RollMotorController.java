package org.swtthm.bandit.controller;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import org.swtthm.bandit.enums.Picture;

import java.util.List;

public class RollMotorController {
    private final EV3LargeRegulatedMotor leftMotor;
    private final EV3LargeRegulatedMotor middleMotor;
    private final EV3LargeRegulatedMotor rightMotor;
    private final EV3LargeRegulatedMotor[] motors;

    // Konstruktor
    public RollMotorController() {
        // definieren der drei Motoren an mit ihren Ports am Ev3 
        leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        middleMotor = new EV3LargeRegulatedMotor(MotorPort.C);
        rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
        // anlegen einer Liste mit allen Motoren für einfachere Adressierung
        motors = new EV3LargeRegulatedMotor[] {leftMotor, middleMotor, rightMotor};

        // initiales drehen der Motoren (schnellere Ansteuerung nach dem ersten mal code ausführen)
        moveMotors(List.of(Picture.RED, Picture.RED, Picture.RED), List.of(Picture.RED, Picture.RED, Picture.RED), false);
    }

    // rechnet abhängig von der letzten position der Motoren (lastPicture) die Drehwinkel aus und dreht die Motoren auf die Farben übergeben in picture
    // angleRotation -> Werden volle Umdrehungen gemacht bevor die richtige Farbe angesteuert wird
    public void moveMotors(List<Picture> picture, List<Picture> lastPicture, boolean angleRotationOn) {
        int angleOneRotation = 0;
        // Ist angleOneRotation aktiv wird immer 1x 360° gedreht bevor die berechnete Farbe angesteuert wird
        if (angleRotationOn) {
            angleOneRotation = 1 * 360;
        }

        // Öffnen eines Synchronisationsblocks der alle Motoren enthält
        // das ermöglicht die "gleichzeitige" Ansteuerung der Motoren vom Ev3
        leftMotor.synchronizeWith(motors);
        leftMotor.startSynchronization();

        System.out.println("[MOTORS] Start sync.");
        // allen Motoren gleich konfigurieren
        // brake damit sie direkt am angegebenen Winkel zum halt kommen
        for (int index = 0; index < motors.length; index++) {
            System.out.println("[MOTORS] Configure motors...");
            motors[index].setSpeed(250);
            motors[index].brake();
        }
        // Berechnung des Drehwinkels und drehen der Motoren
        for (int index = 0; index < motors.length; index++) {
            System.out.println("[MOTORS] Calculation offset...");
            // Offset zur Grundposition berechnen
            int offset = 360 - lastPicture.get(index).getAngle();
            // RotationAngle Berechnung mit std. Rotation + Offset der berechneten Farbe von Startfarbe + Offset der aktuellen Position zur Startfarbe
            int rotationAngle = angleOneRotation + picture.get(index).getAngle() + offset;
            System.out.println("[MOTORS] Turning motor: " + index + ", " + rotationAngle + " degrees");

            System.out.println("[MOTORS] Turning motors...");
            // tatsächliche drehen des jeweiligen Motors
            motors[index].rotate(rotationAngle, true);
        }
        // Warten bis jeder Motor fertig gedreht hat
        for (int index = 0; index < motors.length; index++) {
            System.out.println("[MOTORS] Waiting for motors...");
            motors[index].waitComplete();
        }
        // beenden des Synchronisationsblocks -> Ende der Funktion
        leftMotor.endSynchronization();
        System.out.println("[MOTORS] End sync.");
    }
}
