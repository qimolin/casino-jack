package casino.game;

import casino.cashier.Cashier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BettingRoundTest {

    /**
     * @verifies BettingRound object is logically equal
     */
    @Test
    public void BettingRound_withSameLoggingAuthorityAreLogicallyEqual() {
        // Arrange
        IBettingRound A = new BettingRound();
        IBettingRound B = new BettingRound();
        // Act
        // Assert
        assertThat(A).isEqualTo(B);
        assertThat(A).hasSameHashCodeAs(B);
    }
    /**
     * @verifies create a BettingRound and set bettingRoundID and betToken
     * @see BettingRound#BettingRound()
     */
    @Test
    public void BettingRound_shouldCreateABettingRoundAndSetBettingRoundIDAndBetToken() throws Exception {

    }
}
