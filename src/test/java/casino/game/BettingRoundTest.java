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
     * @verifies BettingRound object is logically equal
     */
    @Test
    public void BettingRound_WithSameBettingRoundIdAndBetTokenAreLogicallyEqual() {
        // Arrange
        GeneralID bettingRoundID = IDFactory.generateID("BETTINGROUNDID");
        BetToken betToken = mock(BetToken.class);
        IBettingRound A = new BettingRound((BettingRoundID) bettingRoundID, betToken);
        IBettingRound B = new BettingRound((BettingRoundID) bettingRoundID, betToken);
        // Act
        // Assert
        assertThat(A).isEqualTo(B);
        assertThat(A).hasSameHashCodeAs(B);
    }
}
