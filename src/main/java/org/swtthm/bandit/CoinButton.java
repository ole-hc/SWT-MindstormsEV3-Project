package org.swtthm.bandit;

import ev3dev.sensors.ev3.EV3TouchSensor;
import lejos.hardware.port.SensorPort;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CoinButton{
    private final EV3TouchSensor coinButton;
    private final MoneyInteractor moneyInteractor;
    private final ExecutorService executorService;

    public CoinButton(MoneyInteractor moneyInteractor) {
        this.coinButton = new EV3TouchSensor(SensorPort.S2);
        this.executorService = Executors.newFixedThreadPool(1);
        this.moneyInteractor = moneyInteractor;
        executorService.execute(this::checkButton);
    }

    public void checkButton() {
        while (true) {
            if (coinButton.isPressed()) {
                System.out.println("[COINBUTTON] isPressed, sending Signal");
                moneyInteractor.sendCreditSignal();
            }
        }
    }
}
