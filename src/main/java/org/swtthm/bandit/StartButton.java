package org.swtthm.bandit;

import ev3dev.sensors.ev3.EV3TouchSensor;
import lejos.hardware.port.SensorPort;


public class StartButton {
    private final EV3TouchSensor startButton;
    private final GameInteractor interactor;

    public StartButton(GameInteractor interactor){
        startButton = new EV3TouchSensor(SensorPort.S1);
        this.interactor = interactor;
    }

    public void waitForInput() {
        while(true) {
            if(startButton.isPressed()) {
                interactor.startGame();
            }
        }
    }
}

