package casino.game;

import casino.bet.Bet;
import casino.bet.MoneyAmount;
import casino.cashier.Cashier;
import casino.idfactory.GeneralID;
import casino.idfactory.IDFactory;
import gamblingauthoritiy.BetToken;
import gamblingauthoritiy.BetTokenAuthority;
import gamblingauthoritiy.IBetLoggingAuthority;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

public class BettingRoundTest {


    /**
     * @verifies create new Set of Bets and bettingRoundID and BetToken
     * @see BettingRound#BettingRound()
     */
    @Test
    public void BettingRound_shouldCreateNewSetOfBetsAndBettingRoundIDAndBetToken() throws Exception {
        // Arrange
        BettingRound sut = new BettingRound();
        // Act
        // Assert
        assertThat(sut.getAllBetsMade()).isNotNull();
        assertThat(sut.getBettingRoundID()).isNotNull();
        assertThat(sut.getBetToken()).isNotNull();
    }

    /**
     * @verifies create new Set of Bets
     * @see BettingRound#BettingRound()
     */
    @Test
    public void BettingRound_shouldCreateNewSetOfBets() {
        // Arrange
        BettingRound sut = new BettingRound();
        // Act
        // Assert
        assertThat(sut.getAllBetsMade()).isNotNull();
    }

    /**
     * @verifies add bet to set
     * @see BettingRound#placeBet(Bet)
     */
    @Test
    public void placeBet_shouldAddBetToSet() {
        // Arrange
        BettingRound sut = new BettingRound();
        Bet bet = mock(Bet.class);
        // Act
        sut.placeBet(bet);
        // Assert
        assertThat(sut.getAllBetsMade()).isNotEmpty();
    }

    /**
     * @verifies return false if amount is negative
     * @see BettingRound#placeBet(Bet)
     */
    @Test
    public void placeBet_shouldReturnFalseIfAmountIsNegative() {
        // Arrange
        BettingRound sut = new BettingRound();
        Bet bet = mock(Bet.class);
        MoneyAmount moneyAmount = mock(MoneyAmount.class);
        // Act
        when(moneyAmount.getAmountInCents()).thenReturn(-2L);
        when(bet.getMoneyAmount()).thenReturn(moneyAmount);
        // Assert
        assertThat(sut.placeBet(bet)).isFalse();
    }

    /**
     * @verifies throw IllegalArgumentException if bet is null
     * @see BettingRound#placeBet(Bet)
     */
    @Test
    public void placeBet_shouldThrowIllegalArgumentExceptionIfBetIsNull() {
        // Arrange
        BettingRound sut = new BettingRound();
        Bet bet = null;
        // Act
        // Assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    sut.placeBet(bet);
                });
    }


    /**
     * @verifies return size of bets set
     * @see BettingRound#numberOFBetsMade()
     */
    @Test
    public void numberOFBetsMade_shouldReturnSizeOfBetsSet() throws Exception {
        // Arrange
        BettingRound sut = new BettingRound();
        Bet bet = mock(Bet.class);
        MoneyAmount moneyAmount = mock(MoneyAmount.class);
        // Act
        when(moneyAmount.getAmountInCents()).thenReturn(2L);
        when(bet.getMoneyAmount()).thenReturn(moneyAmount);

        sut.placeBet(bet);
        // Assert
        assertThat(sut.numberOFBetsMade()).isEqualTo(1);
    }
}
