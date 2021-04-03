package casino.cashier;


import casino.bet.BetID;
import casino.idfactory.IDFactory;

import java.util.Set;

public class GamblerCard implements IGamblerCard {

    private CardID cardID = null;
    private long moneyAmountInCents;

    /**
     * @should generate a cardID upon creation
     */
    public GamblerCard() {
        cardID = (CardID) IDFactory.generateID("CARDID");
    }

    @Override
    public CardID getCardID() {
        return cardID;
    }

    public Set<BetID> getBetIDs() {
        return null;
    }

    public long getMoneyAmountInCents() {
        return moneyAmountInCents;
    }

    public void setMoneyAmountInCents(long moneyAmountInCents) {
        this.moneyAmountInCents += moneyAmountInCents;
    }

    /**
     * The card generates a unique betID for every bet made by the gambler on the machine.
     * A list of all generated betID’s is also stored on the card. BetID’s also contain a timestamp.
     *
     * @return
     *
     * @should create and store a new betID
     */
    @Override
    public BetID generateNewBetID() {
        return null;
    }

    /**
     * return number of betID's generated on this card.
     *
     * @return
     *
     * @should return the count of betIDs stored
     */
    @Override
    public int getNumberOfBetIDs() {
        return 0;
    }

    /**
     * returns all generated betID's by this card
     *
     * @return a copied set of betID's generated by this card.
     *
     * @should return a copy of all betID
     */
    @Override
    public Set<BetID> returnBetIDs() {
        return null;
    }

    /**
     * returns all generated betID's by this card, and clears all betID's from the card.
     *
     * @return a copied set of betID's generated by this card.
     *
     * @should return a copy of all betID
     * @should clear all stored betIDs
     */

    @Override
    public Set<BetID> returnBetIDsAndClearCard() {
        return null;
    }
}
