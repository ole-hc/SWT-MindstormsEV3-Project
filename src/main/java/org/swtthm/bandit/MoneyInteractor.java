package org.swtthm.bandit;

public class MoneyInteractor {
    private final InMemoryGateway inMemoryGateway;

    public MoneyInteractor(InMemoryGateway inMemoryGateway){
        this.inMemoryGateway = inMemoryGateway;
    }

    void sendCreditSignal(){
        int creditToSpins = 5;
        inMemoryGateway.setSpins(inMemoryGateway.getSpins() + creditToSpins);
        System.out.println("[MONEYINTERACTOR] Added " + creditToSpins + " Spins");
    }
}
