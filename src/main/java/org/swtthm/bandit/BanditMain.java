package org.swtthm.bandit;

import ev3dev.sensors.Button;

public class BanditMain {
    public static void main(String[] args) {
        System.out.println("[MAIN] system started!");

        GameInteractor interactor = new GameInteractor();
        StartButton startButton = new StartButton(interactor);
        startButton.waitForInput();
    }
}
