package org.swtthm.bandit.controller;

import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class CoinMotorController {
    private final EV3MediumRegulatedMotor motor;

    // Konstruktor legt den Motor an und konfiguriert ihn entsprechend
    public CoinMotorController() {
        motor = new EV3MediumRegulatedMotor(MotorPort.A);
        motor.setSpeed(250);
        motor.brake();
    }

    // Funktion zum öffnen der Bank
    public void outputMoney() {
        // Richtung setzen und öffnen
        motor.forward();
        motor.rotateTo(180);
        // warten und in Ausgangsposition fahren
        Delay.msDelay(2500);
        motor.backward();
        motor.rotateTo(0);
    }
}
