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
import static org.mockito.Mockito.*;

public class BettingRoundTest {

    @Mock
    private final IBetLoggingAuthority iBetLoggingAuthority = mock(IBetLoggingAuthority.class);

    /**
     * @verifies create and return new BettingRoundID
     * @see BettingRound#getBettingRoundID()
     */
    @Test
    public void getBettingRoundID_shouldCreateAndReturnNewBettingRoundID() throws Exception {
        // Arrange
        BettingRound sut = new BettingRound(iBetLoggingAuthority);
        // Act
        BettingRoundID id = sut.getBettingRoundID();
        // Assert
        assertThat(id).isNotNull();
    }

    /**
     * @verifies set BetLoggingAuthority
     * @see BettingRound#BettingRound(gamblingauthoritiy.IBetLoggingAuthority)
     */
    @Test
    public void BettingRound_shouldSetBetLoggingAuthority() throws Exception {
        //TODO auto-generated
        Assertions.fail("Not yet implemented");
    }

    /**
     * @verifies create new Set of Bets
     * @see BettingRound#BettingRound(gamblingauthoritiy.IBetLoggingAuthority)
     */
    @Test
    public void BettingRound_shouldCreateNewSetOfBets() throws Exception {
        // Arrange
        BettingRound sut = new BettingRound(iBetLoggingAuthority);
        // Act
        // Assert
        assertThat(sut.getBetLoggingAuthority()).isEqualTo(iBetLoggingAuthority);
    }

    /**
     * @verifies add bet to set
     * @see BettingRound#placeBet(Bet)
     */
    @Test
    public void placeBet_shouldAddBetToSet() throws Exception {
        // Arrange
        BettingRound sut = new BettingRound(iBetLoggingAuthority);
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
    public void placeBet_shouldReturnFalseIfAmountIsNegative() throws Exception {
        // Arrange
        BettingRound sut = new BettingRound(iBetLoggingAuthority);
        Bet bet = mock(Bet.class);
        MoneyAmount moneyAmount = mock(MoneyAmount.class);
        // Act
        when(moneyAmount.getAmountInCents()).thenReturn(-2L);
        when(bet.getMoneyAmount()).thenReturn(moneyAmount);
        // Assert
        assertThat(sut.placeBet(bet)).isFalse();
    }

    /**
     * @verifies log bet to BetLoggingAuthority if successful
     * @see BettingRound#placeBet(Bet)
     */
    @Test
    public void placeBet_shouldLogBetToBetLoggingAuthorityIfSuccessful() throws Exception {
        // Arrange
        BettingRound sut = new BettingRound(iBetLoggingAuthority);
        Bet bet = mock(Bet.class);
        // Act
        sut.placeBet(bet);
        // Assert
    }

    /**
     * @verifies throw IllegalArgumentException if bet is null
     * @see BettingRound#placeBet(Bet)
     */
    @Test
    public void placeBet_shouldThrowIllegalArgumentExceptionIfBetIsNull() throws Exception {
        //TODO auto-generated
        Assertions.fail("Not yet implemented");
    }
}
