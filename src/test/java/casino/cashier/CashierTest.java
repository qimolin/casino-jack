package casino.cashier;

import gamblingauthoritiy.BetLoggingAuthority;
import gamblingauthoritiy.IBetLoggingAuthority;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CashierTest {
    /**
     * @verifies create a cashier and set logging authority
     * @see Cashier#Cashier(gamblingauthoritiy.IBetLoggingAuthority)
     */
    @Test
    public void Cashier_shouldCreateACashierAndSetLoggingAuthority() {
        // Arrange
        IBetLoggingAuthority betLogging = new BetLoggingAuthority();
        Cashier sut = new Cashier(betLogging);
        // Act
        // Assert
        assertThat(sut.getLoggingAuthority()).isEqualTo(betLogging);
    }

    /**
     * @verifies cashier object is logically equal
     */
    @Test
    public void Cashier_withSameLoggingAuthorityAreLogicallyEqual() {
        // Arrange
        IBetLoggingAuthority betLogging = new BetLoggingAuthority();
        Cashier A = new Cashier(betLogging);
        Cashier B = new Cashier(betLogging);
        // Act
        // Assert
        assertThat(A).isEqualTo(B);
        assertThat(A).hasSameHashCodeAs(B);
    }

    /**
     * @verifies return a gambling card
     * @see Cashier#distributeGamblerCard()
     */
    @Test
    public void distributeGamblerCard_shouldReturnAGamblingCard() throws Exception {
        // Arrange
        IBetLoggingAuthority betLogging = new BetLoggingAuthority();
        Cashier sut = new Cashier(betLogging);
        // Act
        IGamblerCard gamblerCard = sut.distributeGamblerCard();
        // Assert
        assertThat(gamblerCard).isNotNull();
    }

    /**
     * @verifies log betIDs to logging authority
     * @see Cashier#returnGamblerCard(IGamblerCard)
     */
    @Test
    public void distributeGamblerCard_shouldPutCardsInASet() throws Exception {
        // Arrange
        IBetLoggingAuthority betLogging = new BetLoggingAuthority();
        Cashier sut = new Cashier(betLogging);
        // Act
        Set<GamblerCard> cards = sut.getGamblerCards();
        // Assert
        assertThat(cards).isNotEmpty();
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
