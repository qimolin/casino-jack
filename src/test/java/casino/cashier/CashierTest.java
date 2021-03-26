package casino.cashier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CashierTest {
    /**
     * @verifies create a cashier and set logging authority
     * @see Cashier#Cashier(gamblingauthoritiy.IBetLoggingAuthority)
     */
    @Test
    public void Cashier_shouldCreateACashierAndSetLoggingAuthority() throws Exception {
        //TODO auto-generated
        Assertions.fail("Not yet implemented");
    }

    /**
     * @verifies return a gambling card
     * @see Cashier#distributeGamblerCard()
     */
    @Test
    public void distributeGamblerCard_shouldReturnAGamblingCard() throws Exception {
        //TODO auto-generated
        Assertions.fail("Not yet implemented");
    }

    /**
     * @verifies log betIDs to logging authority
     * @see Cashier#returnGamblerCard(IGamblerCard)
     */
    @Test
    public void returnGamblerCard_shouldLogBetIDsToLoggingAuthority() throws Exception {
        //TODO auto-generated
        Assertions.fail("Not yet implemented");
    }

    /**
     * @verifies set amount on card to zero
     * @see Cashier#returnGamblerCard(IGamblerCard)
     */
    @Test
    public void returnGamblerCard_shouldSetAmountOnCardToZero() throws Exception {
        //TODO auto-generated
        Assertions.fail("Not yet implemented");
    }

    /**
     * @verifies clear stored betIDs
     * @see Cashier#returnGamblerCard(IGamblerCard)
     */
    @Test
    public void returnGamblerCard_shouldClearStoredBetIDs() throws Exception {
        //TODO auto-generated
        Assertions.fail("Not yet implemented");
    }

    /**
     * @verifies throw BetNotFoundException if bet amount is invalid
     * @see Cashier#checkIfBetIsValid(IGamblerCard, casino.bet.Bet)
     */
    @Test
    public void checkIfBetIsValid_shouldThrowBetNotFoundExceptionIfBetAmountIsInvalid() throws Exception {
        //TODO auto-generated
        Assertions.fail("Not yet implemented");
    }

    /**
     * @verifies subtract bet amount from the card
     * @see Cashier#checkIfBetIsValid(IGamblerCard, casino.bet.Bet)
     */
    @Test
    public void checkIfBetIsValid_shouldSubtractBetAmountFromTheCard() throws Exception {
        //TODO auto-generated
        Assertions.fail("Not yet implemented");
    }

    /**
     * @verifies add correct amount to players card
     * @see Cashier#addAmount(IGamblerCard, casino.bet.MoneyAmount)
     */
    @Test
    public void addAmount_shouldAddCorrectAmountToPlayersCard() throws Exception {
        //TODO auto-generated
        Assertions.fail("Not yet implemented");
    }

    /**
     * @verifies throw InvalidAmountException if amount is negative or null
     * @see Cashier#addAmount(IGamblerCard, casino.bet.MoneyAmount)
     */
    @Test
    public void addAmount_shouldThrowInvalidAmountExceptionIfAmountIsNegativeOrNull() throws Exception {
        //TODO auto-generated
        Assertions.fail("Not yet implemented");
    }
}
