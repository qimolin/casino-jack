package casino.bet;

/**
 * immutable class.
 * keeps unique betID and moneyamount in the bet.
 */
public class Bet {
    private BetID betID;
    private MoneyAmount moneyAmount;

    public Bet(BetID betID, MoneyAmount moneyAmount) {
        this.betID = betID;
        this.moneyAmount = moneyAmount;
    }

    public BetID getBetID() {
        return betID;
    }

    public MoneyAmount getMoneyAmount() {
        return moneyAmount;
    }
}
