package casino.cashier;


import casino.bet.BetID;

import java.util.Set;

public class GamblerCard implements IGamblerCard {

    @Override
    public Set<BetID> returnBetIDs() {
        return null;
    }

    @Override
    public Set<BetID> returnBetIDsAndClearCard() {
        return null;
    }

    @Override
    public BetID generateNewBetID() {
        return null;
    }

    @Override
    public int getNumberOfBetIDs() {
        return 0;
    }

    @Override
    public CardID getCardID() {
        return null;
    }
}
