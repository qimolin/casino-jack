package casino.game;

import casino.bet.Bet;
import casino.cashier.Cashier;
import casino.idfactory.GeneralID;
import casino.idfactory.IDFactory;
import gamblingauthoritiy.BetToken;
import gamblingauthoritiy.BetTokenAuthority;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class BettingRoundTest {

    /**
     * @verifies create and return new BettingRoundID
     * @see BettingRound#getBettingRoundID()
     */
    @Test
    public void getBettingRoundID_shouldCreateAndReturnNewBettingRoundID() throws Exception {
        // Arrange
        BettingRound sut = new BettingRound();
        // Act
        BettingRoundID id = sut.getBettingRoundID();
        // Assert
        assertThat(id).isNotNull();
    }

    /**
     * @verifies create new Set of Bets
     * @see BettingRound#BettingRound()
     */
    @Test
    public void BettingRound_shouldCreateNewSetOfBets() throws Exception {
        // Arrange
        BettingRound sut = new BettingRound();
        // Act
        Set<Bet> sets = sut.getAllBetsMade();
        // Assert
        assertThat(sets).isNotNull();
    }

    /**
     * @verifies add bet to set
     * @see BettingRound#placeBet(Bet)
     */
    @Test
    public void placeBet_shouldAddBetToSet() throws Exception {
        // Arrange
        BettingRound sut = new BettingRound();
        Bet bet = mock(Bet.class);
        // Act
        sut.placeBet(bet);
        // Assert
        assertThat(sut.getAllBetsMade()).isNotEmpty();
    }

    /**
     * @verifies return true if successful otherwise false
     * @see BettingRound#placeBet(Bet)
     */
    @Test
    public void placeBet_shouldReturnTrueIfSuccessfulOtherwiseFalse() throws Exception {
        //TODO auto-generated
        Assertions.fail("Not yet implemented");
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
