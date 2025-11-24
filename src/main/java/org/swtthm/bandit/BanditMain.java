package org.swtthm.bandit;

import ev3dev.actuators.lego.motors.Motor;
import ev3dev.sensors.Button;
import lejos.utility.Delay;

import java.util.List;

public class BanditMain {
    public static void main(String[] args) {
        System.out.println("[MAIN] system started!");

        CoinMotor coinMotor = new CoinMotor();
        BrickOutput brickOutput = new BrickOutput();
        InMemoryGateway inMemoryGateway = new InMemoryGateway(500, List.of(Pictures.RED, Pictures.RED, Pictures.RED));
        MotorController motorController = new MotorController();

        GameInteractor interactor = new GameInteractor(coinMotor, brickOutput, inMemoryGateway, motorController);
        StartButton startButton = new StartButton(interactor);
        System.out.println("[MAIN] Setup complete");

        startButton.waitForInput();
    }
}
