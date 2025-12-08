package org.swtthm.bandit;

import ev3dev.actuators.LCD;
import ev3dev.actuators.lego.motors.Motor;
import ev3dev.sensors.Button;
import lejos.utility.Delay;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.List;

public class BanditMain {
    public static void main(String[] args) {
        System.out.println("[MAIN] setting up system..");
        CoinMotor coinMotor = new CoinMotor();
        BrickOutput brickOutput = new BrickOutput();
        InMemoryGateway inMemoryGateway = new InMemoryGateway(0, List.of(Pictures.RED, Pictures.RED, Pictures.RED));
        MotorController motorController = new MotorController();
        MoneyInteractor moneyInteractor = new MoneyInteractor(inMemoryGateway);
        
        CoinButton coinButton = new CoinButton(moneyInteractor);
        final ExecutorService executorService;
        executorService = Executors.newFixedThreadPool(1);
        executorService.execute(coinButton);

        GameInteractor interactor = new GameInteractor(coinMotor, brickOutput, inMemoryGateway, motorController);
        StartButton startButton = new StartButton(interactor);
        System.out.println("[MAIN] Setup complete");

        startButton.waitForInput();
    }
}
