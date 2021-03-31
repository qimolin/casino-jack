package casino.cashier;


import casino.bet.Bet;
import casino.bet.BetID;
import casino.bet.MoneyAmount;
import gamblingauthoritiy.IBetLoggingAuthority;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Cashier implements ICashier {

    private final IBetLoggingAuthority loggingAuthority;
    private final Set<IGamblerCard> gamblerCards;
    /**
     * @should create a cashier and set logging authority
     * @param loggingAuthority
     */
    public Cashier(IBetLoggingAuthority loggingAuthority) {
        this.loggingAuthority = loggingAuthority;
        this.gamblerCards = new HashSet<>();
    }
    /**
     * Cards are distributed by a Cashier to a gambler
     * bankteller keeps track of which cards are handed out.
     * Note: also use the appropiate required methods from the gambling authority API
     * @should return a gambling card
     * @should put cards in a set
     * @return null
     */
    @Override
    public IGamblerCard distributeGamblerCard() {
        IGamblerCard gamblerCard = new GamblerCard();
        gamblerCards.add(gamblerCard);
        return gamblerCard;
    }

    /**
     * When handing in the card at a Bank teller, all betID’s on it are logged.
     * The total amount of money credit is physically handed to the gambler,
     * and the amount stored on the card is changed to zero.
     * The stored betID’s on the card are also removed.
     * Note: also use the appropiate required methods from the gambling authority API
     * @should call returnBetIDsAndClearCard and getCardID
     * @should call logHandInGamblingCard
     * @param card
     */
    @Override
    public void returnGamblerCard(IGamblerCard card) {
        this.loggingAuthority.logHandInGamblingCard(card.getCardID(), card.returnBetIDsAndClearCard());
    }

    /**
     * check if Bet made with the playercard is possible. this is based on the amount related
     * to the card, and the amount made in the bet.
     * <p>
     * if the bet is valid, the amount of the bet is subtracted from the amount belonging to
     * the card.
     * @should throw BetNotFoundException if bet amount is invalid
     * @should call setMoneyAmountInCents and return true
     * @param card
     * @param betToCheck
     * @return
     * @throws BetNotExceptedException if bet amount is invalid
     */
    @Override
    public boolean checkIfBetIsValid(IGamblerCard card, Bet betToCheck) throws BetNotExceptedException {
        long cardAmount = card.getMoneyAmountInCents();
        long betAmount = betToCheck.getMoneyAmount().getAmountInCents();
        if (cardAmount < betAmount) {
            throw new BetNotExceptedException("Bet amount is bigger than amount on card");
        } else {
            card.setMoneyAmountInCents(-betAmount);
            return true;
        }
    }

    /**
     * add amount to the players card.
     * @should call setMoneyAmountInCents on card
     * @should throw InvalidAmountException if amount is negative or null
     * @param card   card to add amount to
     * @param amount amount to add to card
     * @throws InvalidAmountException when MoneyAmount contains a negative value or is null
     */
    @Override
    public void addAmount(IGamblerCard card, MoneyAmount amount) throws InvalidAmountException {
        if (amount.getAmountInCents() <= 0) throw new InvalidAmountException("Value has to be greater than 0");
        card.setMoneyAmountInCents(amount.getAmountInCents());
    }

    public IBetLoggingAuthority getLoggingAuthority() {
        return loggingAuthority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cashier)) return false;
        Cashier cashier = (Cashier) o;
        return loggingAuthority.equals(cashier.loggingAuthority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loggingAuthority);
    }

    public Set<IGamblerCard> getGamblerCards() {
        return gamblerCards;
    }
}
