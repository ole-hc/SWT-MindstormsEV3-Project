package org.swtthm.bandit;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryGateway {
    private AtomicInteger spins;
    private List<Pictures> lastPicture;

    InMemoryGateway(int credit, List<Pictures> lastPicture){
        this.spins = new AtomicInteger(credit);
        this.lastPicture = lastPicture;
    }

    public int getSpins(){
        return spins.get();
    }

    public synchronized void setSpins(int credit){
        this.spins.set(credit);
    }

    public List<Pictures> getLastPicture(){
        return lastPicture;
    }

    public void setLastPicture(List<Pictures> lastPicture){
        this.lastPicture = lastPicture;
    }
}
