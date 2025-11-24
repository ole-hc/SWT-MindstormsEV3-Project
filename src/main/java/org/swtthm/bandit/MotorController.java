package org.swtthm.bandit;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class MotorController {
    private final EV3LargeRegulatedMotor leftMotor;
    private final EV3LargeRegulatedMotor middleMotor;
    private final EV3LargeRegulatedMotor rightMotor;
    private final GameInteractor interactor;

    public MotorController(GameInteractor interactor) {
        leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        middleMotor = new EV3LargeRegulatedMotor(MotorPort.C);
        rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
        this.interactor = interactor;
    }

    public void moveLeftMotor() {
        leftMotor.setSpeed(500);
        leftMotor.forward();
        Delay.msDelay(2500);
        leftMotor.stop();
    }
}
