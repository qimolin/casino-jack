package casino.idfactory;

import casino.cashier.CardID;
import casino.cashier.GamblerCard;
import casino.game.BettingRoundID;
import casino.gamingmachine.GamingMachineID;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class IDFactoryTest {

    @Test
    public void IDFactory_shouldReturnCardIDObject() {
        GeneralID cardID = IDFactory.generateID("CARDID");

        assertThat(cardID).isNotNull();
        assertThat(cardID).isInstanceOf(CardID.class);
    }

    @Test
    public void IDFactory_shouldReturnBettingIDObject() {
        GeneralID bettingRoundID = IDFactory.generateID("BETTINGROUNDID");

        assertThat(bettingRoundID).isNotNull();
        assertThat(bettingRoundID).isInstanceOf(BettingRoundID.class);
    }

    @Test
    public void IDFactory_shouldReturnGamingMachineID() {
        GeneralID gamingMachineID = IDFactory.generateID("GAMINGMACHINEID");

        assertThat(gamingMachineID).isNotNull();
        assertThat(gamingMachineID).isInstanceOf(GamingMachineID.class);
    }
}