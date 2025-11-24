package org.swtthm.bandit;

import ev3dev.sensors.Button;

import java.util.List;

public class BanditMain {
    public static void main(String[] args) {
        System.out.println("[MAIN] system started!");

        CoinMotor coinMotor = new CoinMotor();
        BrickOutput brickOutput = new BrickOutput();
        InMemoryGateway inMemoryGateway = new InMemoryGateway(0, List.of(Pictures.ROT, Pictures.ROT, Pictures.ROT));

        GameInteractor interactor = new GameInteractor(coinMotor, brickOutput, inMemoryGateway);
        StartButton startButton = new StartButton(interactor);
        startButton.waitForInput();
    }
}
