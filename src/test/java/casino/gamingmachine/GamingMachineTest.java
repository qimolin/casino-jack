package casino.gamingmachine;

import casino.bet.Bet;
import casino.cashier.Cashier;
import casino.cashier.GamblerCard;
import casino.cashier.IGamblerCard;
import gamblingauthoritiy.BetLoggingAuthority;
import gamblingauthoritiy.IBetLoggingAuthority;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class GamingMachineTest {

    @Mock
    private final IBetLoggingAuthority iBetLoggingAuthority = mock(IBetLoggingAuthority.class);

    /**
     * @verifies throws an exception
     * @see GamingMachine#placeBet(long)
     */
    @Test
    public void Machine_placeBetShouldThrowNoPlayerCardException() {
        GamingMachine sut = new GamingMachine(new Cashier(iBetLoggingAuthority));
        NoPlayerCardException exception = assertThrows(NoPlayerCardException.class, () ->
                sut.placeBet(2L));
        assertEquals("No card exception", exception.getMessage());

    }
    /**
     * @verifies if moneyInCard > moneyToBet should return true and pass
     * @see GamingMachine#placeBet(long)
     */
    @Test
    public void Machine_shouldPlaceBet() throws NoPlayerCardException {
        long moneyInCard = 5L;
        long moneyToBet = 2L;
        GamingMachine sut = new GamingMachine(new Cashier(iBetLoggingAuthority));
        GamblerCard gamblerCard = mock(GamblerCard.class);
        when(gamblerCard.getMoneyAmountInCents()).thenReturn(moneyInCard);
        sut.connectCard(gamblerCard);

        assertTrue(sut.placeBet(moneyToBet));

    }

    /**
     * @verifies if moneyInCard < moneyToBet should return true and pass
     * @see GamingMachine#placeBet(long)
     */
    @Test
    public void Machine_shouldNotPlaceBet() throws NoPlayerCardException {
        long moneyInCard = 5L;
        long moneyToBet = 6L;
        GamingMachine sut = new GamingMachine(new Cashier(iBetLoggingAuthority));
        GamblerCard gamblerCard = mock(GamblerCard.class);
        when(gamblerCard.getMoneyAmountInCents()).thenReturn(moneyInCard);
        sut.connectCard(gamblerCard);

        assertFalse(sut.placeBet(moneyToBet));

    }

    @Test
    public void Machine_shouldAcceptWinner() {


    }

    @Test
    public void Machine_shouldReturnGamingMachineId() {

        GamingMachine sut = new GamingMachine(new Cashier(iBetLoggingAuthority));

        assertThat(sut.getGamingMachineID()).isInstanceOf(GamingMachineID.class);

    }

}