package casino.game;

import casino.idfactory.GeneralID;
import casino.idfactory.IDFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class BettingRoundIDTest {
    /**
     * @verifies return true if ids are same
     * @see BettingRoundID#compareTo(casino.idfactory.GeneralID)
     */
    @Test
    public void compareTo_shouldReturnTrueIfIdsAreSame() throws Exception {
        BettingRoundID idA = spy(new BettingRoundID());
        BettingRoundID idB = spy(new BettingRoundID());
        GeneralID bettingRoundID = IDFactory.generateID("BETTINGROUNDID");

        when(idA.getID()).thenReturn(bettingRoundID.getID());
        when(idB.getID()).thenReturn(bettingRoundID.getID());

        assertEquals(1, idA.compareTo(idB));
    }

    /**
     * @verifies return false if ids are different
     * @see BettingRoundID#compareTo(GeneralID)
     */
    @Test
    public void compareTo_shouldReturnFalseIfIdsAreDifferent() throws Exception {
        BettingRoundID idA = new BettingRoundID();
        BettingRoundID idB = new BettingRoundID();

        assertEquals(0, idA.compareTo(idB));
    }
}
