package org.swtthm.bandit;

public enum Pictures {
    ROT(0),
    GRUEN(90),
    GELB(180),
    BLAU(270);

    private final int angel;

    Pictures(int angel){
        this.angel = angel;
    }

    public int getAngel(){
        return this.angel;
    }
}
