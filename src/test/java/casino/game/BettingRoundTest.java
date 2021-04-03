package casino.game;

import casino.cashier.Cashier;
import casino.idfactory.GeneralID;
import casino.idfactory.IDFactory;
import gamblingauthoritiy.BetToken;
import gamblingauthoritiy.BetTokenAuthority;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}
