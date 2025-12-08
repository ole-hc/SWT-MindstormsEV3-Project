package org.swtthm.bandit;

import ev3dev.actuators.LCD;
import ev3dev.actuators.LCDJessie;
import ev3dev.actuators.LCDStretch;
import ev3dev.actuators.Sound;
import ev3dev.utils.JarResource;
import lejos.hardware.lcd.GraphicsLCD;

import java.io.File;
import java.io.IOException;

public class BrickOutput {
    private Sound sound;
    private GraphicsLCD lcd;

    public BrickOutput(){
        sound = Sound.getInstance();
        sound.setVolume(100);

        lcd = LCDJessie.getInstance();
    }

    public void outputWiningSound() {
    }

    public void outputStartScreen() {
        lcd.drawString("Zieh am Hebel", 10,10, 10);
    }

    public void outputWinningScreen() {
    }

    public void outputNoMoney() {
    }

    public void outputLosingSound() {
        String filePathWiningSound = "/data/WiningSound.wav";
        sound.beep();
        try {
            //JarResource.export(filePathWiningSound);
            System.out.println("Jar File exported");
            File file = new File(filePathWiningSound);
            System.out.println("Trying to play Sound");
            sound.playSample(file);

        }catch (Exception e){
            System.out.println("Couldn't export sound file");
        }
    }

    public void outputLosingScreen() {
    }
}
