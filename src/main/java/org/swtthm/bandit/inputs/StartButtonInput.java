package org.swtthm.bandit.inputs;

import ev3dev.sensors.ev3.EV3TouchSensor;
import lejos.hardware.port.SensorPort;
import org.swtthm.bandit.interactors.GameInteractor;


public class StartButtonInput {
    private final EV3TouchSensor startButton;
    private final GameInteractor interactor;

    public StartButtonInput(GameInteractor interactor){
        //Implementiert den Touch-Sensor am Port 1
        startButton = new EV3TouchSensor(SensorPort.S1);
        this.interactor = interactor;
    }

    public void waitForInput() {
        while(true) {
            //Bei einem Tastendruck erfolgt eine Ausgabe und die Funktion startGame im Interactor wird aufgerufen
            if(startButton.isPressed()) {
                System.out.println("[STARTBUTTON] isPressed, starting game");
                interactor.startGame();
            }
        }
    }
}

