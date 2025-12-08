package org.swtthm.bandit.gateway;

import org.swtthm.bandit.enums.Picture;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryGateway {
    //Eine Atomic Variable ist in Java Threadsafe. Da auf die Variable Spins von zwei Threads zugegriffen wird, wird diese als AtomicInteger deklariert
    private AtomicInteger spins;
    //Das letzte Bild wird gespeichert, weil sich dort drin auch die Informationen befinden, an welcher Grad-Zahl sich die Motoren befinden und dies zum erneuten Ansteuern der Motoren benötigt wird
    private List<Picture> lastPicture;

    public InMemoryGateway(int credit, List<Picture> lastPicture){
        this.spins = new AtomicInteger(credit);
        this.lastPicture = lastPicture;
    }

    //Get-Funktion für die Anzahl an Spins
    public int getSpins(){
        return spins.get();
    }

    //Set-Funktion für die Anzahl der Spins. Diese Funktion ist "synchronized", damit nicht beide Threads zeitgleich versuchen sie zu lesen
    public synchronized void setSpins(int credit){
        this.spins.set(credit);
    }

    //Getter für Last Picture
    public List<Picture> getLastPicture(){
        return lastPicture;
    }

    //Setter für Last Picture. Diese Funktion muss nicht Threadsafe sein, da auf diese Variable nur aus einem Thread zugegriffen wird
    public void setLastPicture(List<Picture> lastPicture){
        this.lastPicture = lastPicture;
    }
}
