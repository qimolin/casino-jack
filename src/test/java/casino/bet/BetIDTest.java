package casino.bet;

import casino.idfactory.GeneralID;
import casino.idfactory.IDFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class BetIDTest {
    /**
     * @verifies return 1 if equal else 0
     * @see BetID#compareTo(casino.idfactory.GeneralID)
     */
    @Test
    public void compareTo_shouldReturn1IfEqualElse0() throws Exception {
        // Arrange
        BetID bet1 = spy(new BetID());
        BetID bet2 = spy(new BetID());
        GeneralID cardID = IDFactory.generateID("BETID");
        // Act
        when(bet1.getID()).thenReturn(cardID.getID());
        when(bet2.getID()).thenReturn(cardID.getID());
        // Assert
        assertThat(bet1.compareTo(bet2)).isEqualTo(1);
    }
}
