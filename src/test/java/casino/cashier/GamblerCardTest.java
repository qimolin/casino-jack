package casino.cashier;

import gamblingauthoritiy.BetLoggingAuthority;
import gamblingauthoritiy.BetTokenAuthority;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GamblerCardTest {
    @Mock
    private BetLoggingAuthority betLoggingAuthority;
    @Mock
    private BetTokenAuthority betTokenAuthority;

    private AutoCloseable closeable;
    private GamblerCard card;

    @BeforeEach
    void initMocks() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void clearMocks() throws Exception { closeable.close(); }

    /**
     * @verifies generate a cardID upon creation
     * @see GamblerCard#GamblerCard()
     */
    @Test
    public void GamblerCard_shouldGenerateACardIDUponCreation() throws Exception {
        card = new GamblerCard();

        assertThat(card.getCardID()).isNotNull();
        assertThat(card.getCardID()).isInstanceOf(CardID.class);
    }

    /**
     * @verifies return a copy of all betID
     * @see GamblerCard#returnBetIDs()
     */
    @Test
    public void returnBetIDs_shouldReturnACopyOfAllBetID() throws Exception {

    }
}
