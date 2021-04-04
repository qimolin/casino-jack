package casino.gamingmachine;

import casino.game.BettingRoundID;
import casino.idfactory.GeneralID;
import casino.idfactory.IDFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class GamingMachineIDTest {
    /**
     * @verifies return true if ids are same
     * @see GamingMachineID#compareTo(casino.idfactory.GeneralID)
     */
    @Test
    public void compareTo_shouldReturnTrueIfIdsAreSame() throws Exception {
        GamingMachineID idA = spy(new GamingMachineID());
        GamingMachineID idB = spy(new GamingMachineID());
        GeneralID gamingMachineID = IDFactory.generateID("GAMINGMACHINEID");

        when(idA.getID()).thenReturn(gamingMachineID.getID());
        when(idB.getID()).thenReturn(gamingMachineID.getID());

        assertEquals(1, idA.compareTo(idB));
    }

    /**
     * @verifies return false if ids are different
     * @see GamingMachineID#compareTo(casino.idfactory.GeneralID)
     */
    @Test
    public void compareTo_shouldReturnFalseIfIdsAreDifferent() throws Exception {
        GamingMachineID idA = new GamingMachineID();
        GamingMachineID idB = new GamingMachineID();

        assertEquals(0, idA.compareTo(idB));
    }
}
