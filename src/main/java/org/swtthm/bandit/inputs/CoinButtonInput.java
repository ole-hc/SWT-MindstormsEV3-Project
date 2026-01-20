package org.swtthm.bandit.inputs;

import ev3dev.sensors.ev3.EV3TouchSensor;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import org.swtthm.bandit.interactors.MoneyInteractor;

public class CoinButtonInput implements Runnable{
    private final EV3TouchSensor coinButton;
    private final MoneyInteractor moneyInteractor;

    public CoinButtonInput(MoneyInteractor moneyInteractor) {
        //Implementiert den Touch-Sensor am Port 2
        this.coinButton = new EV3TouchSensor(SensorPort.S2);
        this.moneyInteractor = moneyInteractor;
    }

    @Override
    public void run() {
        //Die Abfrage des Tasters läuft in einer Dauerschleife, da sie über den Thread beendet werden kann
        while (true) {
            //Bei einem Tastendruck erfolgt eine Ausgabe und die Funktion sendCreditSignal im Interactor wird aufgerufen
            if (coinButton.isPressed()) {
                System.out.println("[COINBUTTON] isPressed, sending Signal");
                moneyInteractor.sendCreditSignal();
                //Kurzes Timeout, um das System nicht zu überlasten
                Delay.msDelay(500);
            }
        }
    }
}
