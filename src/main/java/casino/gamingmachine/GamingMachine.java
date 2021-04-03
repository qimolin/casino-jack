package casino.gamingmachine;


import casino.bet.Bet;
import casino.bet.BetID;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import casino.cashier.*;
import casino.idfactory.GeneralID;
import casino.idfactory.IDFactory;

public class GamingMachine implements IGamingMachine {

    private GamblerCard gamblerCard = null;
    private Cashier cashier;
    private GeneralID generalID;
    private Bet bet;

    public GamingMachine(Cashier cashier) {
        this.cashier = cashier;
        this.generalID = IDFactory.generateID("GAMINGMACHINEID");
    }

    /**
     * try to place bet with given amount and connected card.
     * amount needs to be checked with the cashier
     * if accepted: generate a bet using the card and add it to the game.
     *
     * @param amountInCents amount in cents to gamble
     * @return true if bet is valid, excepted and added to betting round.
     * @throws NoPlayerCardException when no card is connected to this machine.
     * @should throw bet not accepted
     */
    @Override
    public boolean placeBet(long amountInCents) throws NoPlayerCardException {
        if (gamblerCard == null){
            throw new NoPlayerCardException("No card exception");
        }else {
            try {
                bet = new Bet(new BetID(), new MoneyAmount(amountInCents));
                boolean isValid = cashier.checkIfBetIsValid(gamblerCard, bet);
                System.out.println("isvalid: " + isValid);
                if (isValid) {

                    return isValid;
                }else {
                    bet = null;
                }
            } catch (BetNotExceptedException ex) {
                return false;
            }
        }
        return false;
    }

    /**
     * Accept the BetResult from the winner. Clear all open bets on this machine.
     * When the winner has made his bet on this machine: let the Cashier update
     * the amount of the winner.
     *
     * @param winResult result of a betting round. can be null when there is no winner.
     */

    @Override
    public void acceptWinner(BetResult winResult) {
        System.out.println("winResult" + winResult.getWinningBet().getMoneyAmount().getAmountInCents());
        if (winResult != null) {
            System.out.println("betID: " + bet.getBetID().getID());
            System.out.println("betID: " + winResult.getWinningBet().getBetID().getID());
            if (winResult.getWinningBet().getBetID().compareTo(
                    bet.getBetID()) == 1) {
                try {
                    System.out.println("MONEY AMOUNT: " + bet.getMoneyAmount());
                    cashier.addAmount(gamblerCard, bet.getMoneyAmount());
                } catch (InvalidAmountException e) {
                    e.printStackTrace();
                }


            }
        }
        bet = null;
    }

    /**
     * getter
     *
     * @return gamingmachineID
     */
    @Override
    public GamingMachineID getGamingMachineID() {
        return (GamingMachineID) generalID;
    }

    /**
     * connect card to this gaming machine
     *
     * @param card card to connect
     */
    @Override
    public void connectCard(IGamblerCard card) {
        gamblerCard = (GamblerCard) card;
    }

    /**
     * disconnects/removes card from this gaming machine.
     *
     * @throws CurrentBetMadeException when open bets made with this card
     *                                 are still present in the current betting round and
     */
    @Override
    public void disconnectCard() throws CurrentBetMadeException {

    }

    // to avoid installing PowerMockito that will allow me to mock the construction
    // of new objects  i decided to add an objectSetter in the class
    // ONLY FOR TESTING
    public void setBet(Bet bet){
        this.bet = bet;
    }
}
