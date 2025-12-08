package org.swtthm.bandit.enums;

//Enum in welchem die Farben mit den zugehörigen Grad-Zahlen gespeichert sind. Dieses ist für ein besseres Verständnis und einen einfacheren Umgang in den MotorControllern
public enum Picture {
    RED(0),
    YELLOW(90),
    BLUE(180),
    GREEN(270);

    private final int angle;

    Picture(int angel){
        this.angle = angel;
    }

    //Die Funktion getAngle liefert den Winkel der jeweiligen Farbe zurück
    public int getAngle(){
        return this.angle;
    }
}
