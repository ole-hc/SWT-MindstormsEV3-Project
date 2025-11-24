package org.swtthm.bandit;

public enum Pictures {
    RED(0),
    YELLOW(90),
    BLUE(180),
    GREEN(270);

    private final int angle;

    Pictures(int angel){
        this.angle = angel;
    }

    public int getAngle(){
        return this.angle;
    }
}
