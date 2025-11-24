package org.swtthm.bandit;

import ev3dev.actuators.lego.motors.Motor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameInteractor {
    private final Random random;
    private final CoinMotor coinMotor;
    private final BrickOutput brickOutput;
    private final InMemoryGateway inMemoryGateway;
    private final MotorController motorController;

    public GameInteractor(CoinMotor coinMotor, BrickOutput brickOutput, InMemoryGateway inMemoryGateway, MotorController motorController){
        random = new Random();
        this.coinMotor = coinMotor;
        this.brickOutput = brickOutput;
        this.inMemoryGateway = inMemoryGateway;
        this.motorController = motorController;
    }

    public void startGame() {
        System.out.println("[INTERACTOR] Game started...");

        int credit = inMemoryGateway.getCredit();
        System.out.println("[INTERACTOR] Current credits: " + credit);

        if(credit > 0) {
            inMemoryGateway.setCredit(credit - 1);

            List<Pictures> picture = new ArrayList<>();
            generatePicture(picture);
            System.out.println("[INTERACTOR] Generated picture: ");
            printArray(picture);

            System.out.println("[INTERACTOR] Moving motors...");
            motorController.moveMotors(picture, inMemoryGateway.getLastPicture());

            inMemoryGateway.setLastPicture(picture);

            if (determineWin(picture)) {
                System.out.println("[INTERACTOR] Trigger winning sequence");
                triggerWinningSequence();
            } else{
                System.out.println("[INTERACTOR] Trigger losing sequence");
                triggerLosingSequence();
            }
        }else {
            System.out.println("[INTERACTOR] Insufficient funds");
            brickOutput.outputNoMoney();
        }
    }

    private void triggerLosingSequence() {
        brickOutput.outputLosingSound();
        brickOutput.outputLosingScreen();
    }

    private void triggerWinningSequence(){
        coinMotor.outputMoney();
        brickOutput.outputWiningSound();
        brickOutput.outputWinningScreen();
    }

    private boolean determineWin(List<Pictures> picture){
        return picture.get(0) == picture.get(1) && picture.get(1) == picture.get(2);
    }

    private void generatePicture(List<Pictures> picture) {
        for(int i = 0; i < 3; i++) {
            switch (random.nextInt(4)) {
                case 0:
                    picture.add(Pictures.RED);
                    break;
                case 1:
                    picture.add(Pictures.GREEN);
                    break;
                case 2:
                    picture.add(Pictures.YELLOW);
                    break;
                case 3:
                    picture.add(Pictures.BLUE);
                    break;
            }
        }
    }

    private void printArray(List<Pictures> picture){
        System.out.print("[INTERACTOR] Picture: ");
        for (Pictures selectedPicture : picture) {
            System.out.print(selectedPicture + ";");
        }
        System.out.println();
    }
}
