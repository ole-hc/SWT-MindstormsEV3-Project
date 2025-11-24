package org.swtthm.bandit;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryGateway {
    private AtomicInteger credit;
    private List<Pictures> lastPicture;

    InMemoryGateway(int credit, List<Pictures> lastPicture){
        this.credit = new AtomicInteger(credit);
        this.lastPicture = lastPicture;
    }

    public int getCredit(){
        return credit.get();
    }

    public synchronized void setCredit(int credit){
        this.credit.set(credit);
    }

    public List<Pictures> getLastPicture(){
        return lastPicture;
    }

    public void setLastPicture(List<Pictures> lastPicture){
        this.lastPicture = lastPicture;
    }
}
