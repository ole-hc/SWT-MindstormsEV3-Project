package org.swtthm.bandit;

import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class CoinMotor {
    final EV3MediumRegulatedMotor motor;

    public CoinMotor() {
        motor = new EV3MediumRegulatedMotor(MotorPort.A);
        motor.setSpeed(250);
        motor.brake();
    }

    public void outputMoney() {
        motor.forward();
        motor.rotateTo(180);
        Delay.msDelay(2500);
        motor.backward();
        motor.rotateTo(0);
    }
}
