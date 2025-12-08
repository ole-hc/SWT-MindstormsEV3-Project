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

    public RollMotorController() {
        leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        middleMotor = new EV3LargeRegulatedMotor(MotorPort.C);
        rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
        motors = new EV3LargeRegulatedMotor[] {leftMotor, middleMotor, rightMotor};

        moveMotors(List.of(Picture.RED, Picture.RED, Picture.RED), List.of(Picture.RED, Picture.RED, Picture.RED), false);
    }

    public void moveMotors(List<Picture> picture, List<Picture> lastPicture, boolean angleRotationOn) {
        int angleOneRotation = 0;
        if (angleRotationOn) {
            angleOneRotation = 1 * 360;
        }

        leftMotor.synchronizeWith(motors);
        leftMotor.startSynchronization();

        System.out.println("[MOTORS] Start sync.");
        for (int index = 0; index < motors.length; index++) {
            System.out.println("[MOTORS] Configure motors...");
            motors[index].setSpeed(250);
            motors[index].brake();
        }
        for (int index = 0; index < motors.length; index++) {
            System.out.println("[MOTORS] Calculation offset...");
            int offset = 360 - lastPicture.get(index).getAngle();
            int rotationAngle = angleOneRotation + picture.get(index).getAngle() + offset;
            System.out.println("[MOTORS] Turning motor: " + index + ", " + rotationAngle + " degrees");

            System.out.println("[MOTORS] Turning motors...");
            motors[index].rotate(rotationAngle, true);
        }
        for (int index = 0; index < motors.length; index++) {
            System.out.println("[MOTORS] Waiting for motors...");
            motors[index].waitComplete();
        }
        leftMotor.endSynchronization();
        System.out.println("[MOTORS] End sync.");
    }
}
