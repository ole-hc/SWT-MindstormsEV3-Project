package org.swtthm.bandit.interactors;

import org.swtthm.bandit.controller.BrickOutputController;
import org.swtthm.bandit.gateway.InMemoryGateway;

public class MoneyInteractor {
    private final InMemoryGateway inMemoryGateway;
    private final BrickOutputController brickOutput;

    public MoneyInteractor(InMemoryGateway inMemoryGateway, BrickOutputController brickOutput){
        this.inMemoryGateway = inMemoryGateway;
        this.brickOutput = brickOutput;
    }

    public void sendCreditSignal(){
        //Die aktuelle Anzahl an Umdrehungen aus dem Gateway holen, 5 addieren und wieder zurückschreiben
        int spins = inMemoryGateway.getSpins() + 5;
        inMemoryGateway.setSpins(spins);
        //Die aktualisierte Anzahl an Umdrehungen auf dem Display ausgeben
        brickOutput.outputGameCount(spins);
        System.out.println("[MONEYINTERACTOR] Added 5 Spins");
    }
}
