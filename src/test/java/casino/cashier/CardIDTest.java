package casino.cashier;

import casino.idfactory.GeneralID;
import casino.idfactory.IDFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardIDTest {


    /**
     * @verifies Generate And Return An ID
     * @see CardID#compareTo(casino.idfactory.GeneralID)
     */
    @Test
    public void compareTo_shouldGenerateAndReturnAnID() throws Exception {
        CardID sut = new CardID();
        String id = sut.getID();
        assertThat(id).isNotNull();
    }

    /**
     * @verifies compare ids and return false
     * @see CardID#compareTo(casino.idfactory.GeneralID)
     */
    @Test
    public void compareTo_shouldCompareIdsAndReturnFalse() throws Exception {
        CardID sut = new CardID();
        CardID secondID = new CardID();
        // 0 represents false
        assertEquals(sut.compareTo(secondID),0);
    }

    /**
     * @verifies compare ids and return true
     * @see CardID#compareTo(casino.idfactory.GeneralID)
     */
    @Test
    public void compareTo_shouldCompareIdsAndReturnTrue() throws Exception {
        CardID cardID1 = spy(new CardID());
        CardID cardID2 = spy(new CardID());
        GeneralID cardID = IDFactory.generateID("CARDID");
        when(cardID1.getID()).thenReturn(cardID.getID());
        when(cardID2.getID()).thenReturn(cardID.getID());


        assertEquals( 1 ,cardID1.compareTo(cardID2) );


    }
}