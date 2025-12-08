package org.swtthm.bandit;

import ev3dev.sensors.ev3.EV3TouchSensor;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;

public class CoinButton implements Runnable{
    private final EV3TouchSensor coinButton;
    private final MoneyInteractor moneyInteractor;

    public CoinButton(MoneyInteractor moneyInteractor) {
        this.coinButton = new EV3TouchSensor(SensorPort.S2);
        this.moneyInteractor = moneyInteractor;
    }

    @Override
    public void run() {
        while (true) {
            if (coinButton.isPressed()) {
                System.out.println("[COINBUTTON] isPressed, sending Signal");
                moneyInteractor.sendCreditSignal();
                Delay.msDelay(1000); // Timeout until next input can be registered
            }
        }
    }
}
