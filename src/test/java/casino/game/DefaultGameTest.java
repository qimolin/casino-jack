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

import static org.mockito.Mockito.verify;

public class DefaultGameTest {

    @Mock
    private BetLoggingAuthority betLoggingAuthority;
    @Mock
    private BettingRound bettingRound;

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
    public void startBettingRound_shouldLogToBettingAuthority() {
        game = new DefaultGame();
        BettingRound currentRound = game.getBettingRound();
        BettingRound newBettingRound = new BettingRound();
        Bet bet = new Bet(new BetID(), new MoneyAmount(2000));
        BetResult betResult = new BetResult(bet, new MoneyAmount(4000));

        game.startBettingRound();

        verify(betLoggingAuthority).logEndBettingRound(currentRound, betResult);
        verify(betLoggingAuthority).logStartBettingRound(newBettingRound);
    }
}
