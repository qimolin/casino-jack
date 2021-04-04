package casino.cashier;

import casino.bet.BetID;
import gamblingauthoritiy.BetLoggingAuthority;
import gamblingauthoritiy.BetTokenAuthority;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Array;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
     * @verifies create and store a new betID
     * @see GamblerCard#generateNewBetID()
     */
    @Test
    public void generateNewBetID_shouldCreateAndStoreANewBetID() throws Exception {
        card = new GamblerCard();

        card.generateNewBetID();

        assertThat(card.getBetIDs()).hasSize(1);
    }

    /**
     * @verifies return the count of betIDs stored
     * @see GamblerCard#getNumberOfBetIDs()
     */
    @Test
    public void getNumberOfBetIDs_shouldReturnTheCountOfBetIDsStored() throws Exception {
        card = new GamblerCard();

        card.generateNewBetID();
        card.generateNewBetID();
        card.generateNewBetID();

        assertThat(card.getNumberOfBetIDs()).isEqualTo(3);
    }

    /**
     * @verifies return a copy of all betID
     * @see GamblerCard#returnBetIDs()
     */
    @Test
    public void returnBetIDs_shouldReturnACopyOfAllBetID() throws Exception {
        Set<BetID> betIDs = new HashSet<>();

        card = new GamblerCard();

        BetID betA = card.generateNewBetID();
        BetID betB = card.generateNewBetID();
        betIDs.add(betA);
        betIDs.add(betB);

        assertThat(card.returnBetIDs()).satisfiesAnyOf(
                b -> assertThat(betA).isNotSameAs(b),
                b -> assertThat(betB).isNotSameAs(b));
    }

    /**
     * @verifies clear all stored betIDs
     * @see GamblerCard#returnBetIDsAndClearCard()
     */
    @Test
    public void returnBetIDsAndClearCard_shouldClearAllStoredBetIDs() throws Exception {
        card = spy(new GamblerCard());

        card.returnBetIDsAndClearCard();

        verify(card).returnBetIDs();
        assertThat(card.getBetIDs()).isNull();
    }
}
