package casino.cashier;

import casino.bet.Bet;
import casino.bet.MoneyAmount;
import gamblingauthoritiy.BetLoggingAuthority;
import gamblingauthoritiy.BettingAuthority;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CashierTest {

    @Mock
    private final BettingAuthority bettingAuthority = mock(BettingAuthority.class);
    @Mock
    private final BetLoggingAuthority betLoggingAuthority = mock(BetLoggingAuthority.class);

    /**
     * @verifies setBettingAuthority
     * @see Cashier#Cashier(BettingAuthority)
     */
    @Test
    public void Cashier_shouldSetBettingAuthority() throws Exception {
        // Arrange
        Cashier sut = new Cashier(bettingAuthority);
        // Act
        // Assert
        assertThat(sut.getBettingAuthority()).isEqualTo(bettingAuthority);
    }

    /**
     * @verifies cashier object is logically equal
     */
    @Test
    public void Cashier_withSameLoggingAuthorityAreLogicallyEqual() {
        // Arrange
        Cashier A = new Cashier(bettingAuthority);
        Cashier B = new Cashier(bettingAuthority);
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
        Cashier sut = new Cashier(bettingAuthority);
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
        Cashier sut = new Cashier(bettingAuthority);
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
        Cashier sut = new Cashier(bettingAuthority);
        IGamblerCard card = mock(GamblerCard.class);
        // Act
        when(bettingAuthority.getLoggingAuthority()).thenReturn(betLoggingAuthority);
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
        Cashier sut = new Cashier(bettingAuthority);
        IGamblerCard card = mock(GamblerCard.class);
        // Act
        when(bettingAuthority.getLoggingAuthority()).thenReturn(betLoggingAuthority);
        sut.returnGamblerCard(card);
        // Assert
        verify(sut.getBettingAuthority().getLoggingAuthority()).logHandInGamblingCard(card.getCardID(), card.returnBetIDsAndClearCard());
    }

    /**
     * @verifies throw BetNotFoundException if bet amount is invalid
     * @see Cashier#checkIfBetIsValid(IGamblerCard, casino.bet.Bet)
     */
    @Test
    public void checkIfBetIsValid_shouldThrowBetNotFoundExceptionIfBetAmountIsInvalid() {
        // Arrange
        Cashier sut = new Cashier(bettingAuthority);
        IGamblerCard card = mock(GamblerCard.class);
        Bet bet = mock(Bet.class);
        MoneyAmount amount = mock(MoneyAmount.class);
        // Act
        when(card.getMoneyAmountInCents()).thenReturn(1l);
        when(amount.getAmountInCents()).thenReturn(5l);
        when(bet.getMoneyAmount()).thenReturn(amount);
        // Assert
        assertThatExceptionOfType(BetNotAcceptedException.class)
                .isThrownBy(() -> {
                   sut.checkIfBetIsValid(card, bet);
                });
    }

    /**
     * @verifies call setMoneyAmountInCents and return true
     * @see Cashier#checkIfBetIsValid(IGamblerCard, Bet)
     */
    @Test
    public void checkIfBetIsValid_shouldCallSetMoneyAmountInCentsAndReturnTrue() throws BetNotAcceptedException {
        // Arrange
        Cashier sut = new Cashier(bettingAuthority);
        IGamblerCard card = mock(GamblerCard.class);
        Bet bet = mock(Bet.class);
        MoneyAmount amount = mock(MoneyAmount.class);
        // Act
        when(card.getMoneyAmountInCents()).thenReturn(4L);
        when(amount.getAmountInCents()).thenReturn(2L);
        when(bet.getMoneyAmount()).thenReturn(amount);
        sut.checkIfBetIsValid(card, bet);
        // Assert
        verify(card).setMoneyAmountInCents(-2L);
        assertThat(sut.checkIfBetIsValid(card, bet)).isTrue();
    }
    /**
     * @verifies call setMoneyAmountInCents on card
     * @see Cashier#addAmount(IGamblerCard, MoneyAmount)
     */
    @Test
    public void addAmount_shouldCallSetMoneyAmountInCentsOnCard() throws Exception {
        // Arrange
        Cashier sut = new Cashier(bettingAuthority);
        GamblerCard card = mock(GamblerCard.class);
        MoneyAmount amount = mock(MoneyAmount.class);
        // Act
        when(amount.getAmountInCents()).thenReturn(2L);
        sut.addAmount(card, amount);
        // Assert
        verify(card).setMoneyAmountInCents(2L);
    }
    /**
     * @verifies throw InvalidAmountException if amount is negative or null
     * @see Cashier#addAmount(IGamblerCard, casino.bet.MoneyAmount)
     */
    @Test
    public void addAmount_shouldThrowInvalidAmountExceptionIfAmountIsNegativeOrNull() throws Exception {
        // Arrange
        Cashier sut = new Cashier(bettingAuthority);
        GamblerCard card = mock(GamblerCard.class);
        MoneyAmount amount = mock(MoneyAmount.class);
        // Act
        when(amount.getAmountInCents()).thenReturn(-2L);
        // Assert
        assertThatExceptionOfType(InvalidAmountException.class)
                .isThrownBy(() -> {
                    sut.addAmount(card, amount);
                });

        assertThatExceptionOfType(InvalidAmountException.class)
                .isThrownBy(() -> {
                    sut.addAmount(card, null);
                });
    }


}
