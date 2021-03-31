package casino.game;

import casino.bet.BetResult;
import gamblingauthoritiy.BetLoggingAuthority;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DefaultGameTest {

    @Mock
    private BetLoggingAuthority betLoggingAuthority;
    @Mock
    private IGameRule gameRule;

    private AutoCloseable closeable;
    private DefaultGame game;

    @BeforeEach
    void initAuthorities() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeAuthorities() throws Exception {
        closeable.close();
    }

    /**
     * @verifies log to BettingAuthority
     * @see DefaultGame#startBettingRound()
     */
    @Test
    public void startBettingRound_shouldLogToBettingAuthority() throws NoBetsMadeException {
        BettingRound currentRound = mock(BettingRound.class);
        BetResult betResult = mock(BetResult.class);
        game = new DefaultGame(gameRule, currentRound, betLoggingAuthority);
        when(gameRule.determineWinner(anyInt(), any(Set.class))).thenReturn(betResult);

        game.startBettingRound();

        verify(betLoggingAuthority).logEndBettingRound(currentRound, betResult);
        verify(betLoggingAuthority).logStartBettingRound(any(IBettingRound.class));
    }

    /**
     * @verifies return true if the number of bets equal the max number of bets per round defined by the game rule
     * @see DefaultGame#isBettingRoundFinished()
     */
    @Test
    public void isBettingRoundFinished_shouldReturnTrueIfTheNumberOfBetsEqualTheMaxNumberOfBetsPerRoundDefinedByTheGameRule() {
        BettingRound currentRound = mock(BettingRound.class);
        game = new DefaultGame(gameRule, currentRound, betLoggingAuthority);
        when(gameRule.getMaxBetsPerRound()).thenReturn(5);
        when(currentRound.numberOFBetsMade()).thenReturn(5);

        assertThat(game.isBettingRoundFinished()).isTrue();
    }

    /**
     * @verifies return false if the number of bets are less the max number of bets per round defined by the game rule
     * @see DefaultGame#isBettingRoundFinished()
     */
    @Test
    public void isBettingRoundFinished_shouldReturnFalseIfTheNumberOfBetsAreLessTheMaxNumberOfBetsPerRoundDefinedByTheGameRule() {
        BettingRound currentRound = mock(BettingRound.class);
        game = new DefaultGame(gameRule, currentRound, betLoggingAuthority);
        when(gameRule.getMaxBetsPerRound()).thenReturn(5);
        when(currentRound.numberOFBetsMade()).thenReturn(4);

        assertThat(game.isBettingRoundFinished()).isFalse();
    }
}
