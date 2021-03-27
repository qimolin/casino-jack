package casino.cashier;

import casino.bet.Bet;
import casino.bet.MoneyAmount;
import gamblingauthoritiy.BetLoggingAuthority;
import gamblingauthoritiy.IBetLoggingAuthority;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CashierTest {
    /**
     * @verifies create a cashier and set logging authority
     * @see Cashier#Cashier(gamblingauthoritiy.IBetLoggingAuthority)
     */
    @Test
    public void Cashier_shouldCreateACashierAndSetLoggingAuthority() {
        // Arrange
        IBetLoggingAuthority betLogging = mock(BetLoggingAuthority.class);
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
        IBetLoggingAuthority betLogging = mock(BetLoggingAuthority.class);
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
        IBetLoggingAuthority betLogging = mock(BetLoggingAuthority.class);
        Cashier sut = new Cashier(betLogging);
        // Act
        IGamblerCard gamblerCard = sut.distributeGamblerCard();
        // Assert
        assertThat(gamblerCard).isNotNull();
        assertThat(gamblerCard).isInstanceOf(GamblerCard.class);
    }

    /**
     * @verifies log betIDs to logging authority
     * @see Cashier#returnGamblerCard(IGamblerCard)
     */
    @Test
    public void distributeGamblerCard_shouldPutCardsInASet() {
        // Arrange
        IBetLoggingAuthority betLogging = mock(BetLoggingAuthority.class);
        Cashier sut = new Cashier(betLogging);
        // Act
        sut.distributeGamblerCard();
        Set<IGamblerCard> gamblerCards = sut.getGamblerCards();
        // Assert
        assertThat(gamblerCards).isNotEmpty();
    }

    /**
     * @verifies call returnBetIDsAndClearCard and getCardID
     * @see Cashier#returnGamblerCard(IGamblerCard)
     */
    @Test
    public void returnGamblerCard_shouldCallReturnBetIDsAndClearCardAndGetCardID() {
        // Arrange
        IBetLoggingAuthority betLogging = mock(BetLoggingAuthority.class);
        Cashier sut = new Cashier(betLogging);
        GamblerCard card = mock(GamblerCard.class);
        // Act
        sut.returnGamblerCard(card);
        // Assert
        verify(card).returnBetIDsAndClearCard();
        verify(card).getCardID();
    }

    /**
     * @verifies call logHandInGamblingCard
     * @see Cashier#returnGamblerCard(IGamblerCard)
     */
    @Test
    public void returnGamblerCard_shouldCallLogHandInGamblingCard() {
        // Arrange
        IBetLoggingAuthority betLogging = mock(BetLoggingAuthority.class);
        Cashier sut = new Cashier(betLogging);
        GamblerCard card = mock(GamblerCard.class);
        // Act
        sut.returnGamblerCard(card);
        // Assert
        verify(sut.getLoggingAuthority()).logHandInGamblingCard(card.getCardID(), card.returnBetIDsAndClearCard());
    }

    /**
     * @verifies throw BetNotFoundException if bet amount is invalid
     * @see Cashier#checkIfBetIsValid(IGamblerCard, casino.bet.Bet)
     */
    @Test
    public void checkIfBetIsValid_shouldThrowBetNotFoundExceptionIfBetAmountIsInvalid() {
        // Arrange
        IBetLoggingAuthority betLogging = mock(BetLoggingAuthority.class);
        Cashier sut = new Cashier(betLogging);
        GamblerCard card = mock(GamblerCard.class);
        Bet bet = mock(Bet.class);
        MoneyAmount amount = mock(MoneyAmount.class);
        // Act
        when(card.getMoneyAmountInCents()).thenReturn(1L);
        when(amount.getAmountInCents()).thenReturn(5L);
        when(bet.getMoneyAmount()).thenReturn(amount);
        // Assert
        assertThatExceptionOfType(BetNotExceptedException.class)
                .isThrownBy(() -> {
                   sut.checkIfBetIsValid(card, bet);
                });
    }

    /**
     * @verifies subtract bet amount from the card and return true
     * @see Cashier#checkIfBetIsValid(IGamblerCard, casino.bet.Bet)
     */
    @Test
    public void checkIfBetIsValid_shouldSubtractBetAmountFromTheCardAndReturnTrue() throws Exception {
        // Arrange
        IBetLoggingAuthority betLogging = mock(BetLoggingAuthority.class);
        Cashier sut = new Cashier(betLogging);
        GamblerCard card = mock(GamblerCard.class);
        Bet bet = mock(Bet.class);
        MoneyAmount amount = mock(MoneyAmount.class);
        // Act
        when(card.getMoneyAmountInCents()).thenReturn(4L);
        when(amount.getAmountInCents()).thenReturn(2L);
        when(bet.getMoneyAmount()).thenReturn(amount);
        // Assert
        assertThat(card.getMoneyAmountInCents()).isEqualTo(2L);
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
