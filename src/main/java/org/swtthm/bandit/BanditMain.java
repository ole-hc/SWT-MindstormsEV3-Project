package org.swtthm.bandit;

import ev3dev.actuators.lego.motors.Motor;
import ev3dev.sensors.Button;
import lejos.utility.Delay;

public class BanditMain {
    public static void main(String[] args) {
        System.out.println("[MAIN] system started!");

        GameInteractor interactor = new GameInteractor();
        StartButton startButton = new StartButton(interactor);
        MotorController motorController = new MotorController(interactor);
        motorController.moveMotors(Pictures.RED.getAngel(), Pictures.RED.getAngel(), Pictures.RED.getAngel());
        Delay.msDelay(2000);
        motorController.moveMotors(Pictures.RED.getAngel(), Pictures.RED.getAngel(), Pictures.RED.getAngel());
        startButton.waitForInput();
    }
}
