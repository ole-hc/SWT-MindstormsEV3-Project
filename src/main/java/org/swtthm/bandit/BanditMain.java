package org.swtthm.bandit;

import org.swtthm.bandit.inputs.CoinButtonInput;
import org.swtthm.bandit.gateway.InMemoryGateway;
import org.swtthm.bandit.inputs.StartButtonInput;
import org.swtthm.bandit.controller.BrickOutputController;
import org.swtthm.bandit.controller.CoinMotorController;
import org.swtthm.bandit.controller.RollMotorController;
import org.swtthm.bandit.enums.Picture;
import org.swtthm.bandit.interactors.GameInteractor;
import org.swtthm.bandit.interactors.MoneyInteractor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BanditMain {
    public static void main(String[] args) {
        System.out.println("[MAIN] setting up system..");
        //Initalisierung aller Komponenten

        CoinMotorController coinMotor = new CoinMotorController();
        InMemoryGateway inMemoryGateway = new InMemoryGateway(0, List.of(Picture.RED, Picture.RED, Picture.RED));
        BrickOutputController brickOutput = new BrickOutputController(inMemoryGateway);
        RollMotorController motorController = new RollMotorController();
        MoneyInteractor moneyInteractor = new MoneyInteractor(inMemoryGateway, brickOutput);
        CoinButtonInput coinButton = new CoinButtonInput(moneyInteractor);
        GameInteractor interactor = new GameInteractor(coinMotor, brickOutput, inMemoryGateway, motorController);
        StartButtonInput startButton = new StartButtonInput(interactor);


        //Erzeugen eines seperaten Threads, um beide Button-Inputs parallel bearbeiten zu können
        final ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(coinButton);

        System.out.println("[MAIN] Setup complete");

        //Starten der Haupt-Routine
        startButton.waitForInput();
    }
}
