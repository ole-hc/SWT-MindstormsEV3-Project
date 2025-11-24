package org.swtthm.bandit;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

import java.util.List;

public class MotorController {
    private final EV3LargeRegulatedMotor leftMotor;
    private final EV3LargeRegulatedMotor middleMotor;
    private final EV3LargeRegulatedMotor rightMotor;
    private final EV3LargeRegulatedMotor[] motors;

    public MotorController() {
        leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        middleMotor = new EV3LargeRegulatedMotor(MotorPort.C);
        rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
        motors = new EV3LargeRegulatedMotor[] {leftMotor, middleMotor, rightMotor};
    }

    public void moveMotors(List<Pictures> picture, List<Pictures> lastPicture) {
        int angleFiveRotations = 1 * 360;

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
            int rotationAngle = angleFiveRotations + picture.get(index).getAngle() + offset;
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
