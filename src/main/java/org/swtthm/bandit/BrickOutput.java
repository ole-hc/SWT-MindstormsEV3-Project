package org.swtthm.bandit;

import ev3dev.actuators.LCD;
import ev3dev.actuators.LCDStretch;
import ev3dev.actuators.Sound;
import ev3dev.utils.JarResource;

import java.io.File;
import java.io.IOException;

public class BrickOutput {

    private Sound sound;

    public BrickOutput(){
        sound = Sound.getInstance();
        sound.setVolume(100);
    }

    public void outputWiningSound() {
    }

    public void outputWinningScreen() {
    }

    public void outputNoMoney() {
    }

    public void outputLosingSound() {
        String filePathWiningSound = "/data/WiningSound.wav";

        try {
            JarResource.export(filePathWiningSound);
            File file = new File(filePathWiningSound);
            sound.playSample(file);

        }catch (IOException e){
            System.out.println("Couldn't export sound file");
        }
    }

    public void outputLosingScreen() {
    }
}
