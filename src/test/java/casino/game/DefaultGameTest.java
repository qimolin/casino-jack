package casino.game;

import gamblingauthoritiy.BetLoggingAuthority;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
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
        bettingRound = new BettingRound();
        game.startBettingRound();

        verify(betLoggingAuthority, times(1))
                .logStartBettingRound(bettingRound);
    }
}
