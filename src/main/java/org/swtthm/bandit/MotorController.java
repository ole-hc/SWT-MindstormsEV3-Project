package org.swtthm.bandit;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class MotorController {
    private final EV3LargeRegulatedMotor leftMotor;
    private final EV3LargeRegulatedMotor middleMotor;
    private final EV3LargeRegulatedMotor rightMotor;
    private final EV3LargeRegulatedMotor[] motors;
    private final GameInteractor interactor;

    public MotorController(GameInteractor interactor) {
        leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        middleMotor = new EV3LargeRegulatedMotor(MotorPort.C);
        rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
        motors = new EV3LargeRegulatedMotor[] {leftMotor, middleMotor, rightMotor};
        this.interactor = interactor;
    }

    public void moveMotors(int colorLeftMotor, int colorMiddleMotor, int colorRightMotor) {
        int angleFiveRotations = 1 * 360;

        leftMotor.synchronizeWith(motors);
        leftMotor.startSynchronization();

        System.out.println("[MOTORS] Start sync.");
        for (int index = 0; index < motors.length; index++) {
            motors[index].setSpeed(250);
            motors[index].brake();
        }
        for (int index = 0; index < motors.length; index++) {
            motors[index].rotate(angleFiveRotations + colorLeftMotor, true);
        }
        for (int index = 0; index < motors.length; index++) {
            motors[index].waitComplete();
        }
        leftMotor.endSynchronization();
        System.out.println("[MOTORS] End sync.");
    }
}
