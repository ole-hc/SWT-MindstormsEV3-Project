package org.swtthm.bandit;

public enum Pictures {
    RED(0),
    YELLOW(90),
    BLUE(180),
    GREEN(270);

    private final int angel;

    Pictures(int angel){
        this.angel = angel;
    }

    public int getAngel(){
        return this.angel;
    }
}
