package org.swtthm.bandit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameInteractor {
    private final Random random;
    private final CoinMotor coinMotor;
    private final BrickOutput brickOutput;
    private final InMemoryGateway inMemoryGateway;

    public GameInteractor(CoinMotor coinMotor, BrickOutput brickOutput, InMemoryGateway inMemoryGateway){
        random = new Random();
        this.coinMotor = coinMotor;
        this.brickOutput = brickOutput;
        this.inMemoryGateway = inMemoryGateway;
    }

    public void startGame() {
        System.out.println("Los");

        int credit = inMemoryGateway.getCredit();

        if(credit > 0) {
            inMemoryGateway.setCredit(credit - 1);

            List<Pictures> picture = new ArrayList<>();

            generatePicture(picture);

            printArray(picture);

            //Add MotorController.start

            inMemoryGateway.setLastPicture(picture);

            if (determineWin(picture)) {
                triggerWiningSequence();
            } else{
                triggerLosingSequence();
            }
        }else {
            brickOutput.outputNoMoney();
        }
    }

    private void triggerLosingSequence() {
        brickOutput.outputLosingSound();
        brickOutput.outputLosingScreen();
    }

    private void triggerWiningSequence(){
        coinMotor.outputMoney();
        brickOutput.outputWiningSound();
        brickOutput.outputWinningScreen();
    }

    private boolean determineWin(List<Pictures> picture){
        return picture.get(0) == picture.get(1) || picture.get(1) == picture.get(2);
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
        for (Pictures selectedPicture : picture) {
            System.out.print(selectedPicture + ";");
        }
        System.out.println();
    }
}
