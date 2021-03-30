package casino.game;

import casino.bet.Bet;
import casino.bet.BetID;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import gamblingauthoritiy.BetLoggingAuthority;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
}
