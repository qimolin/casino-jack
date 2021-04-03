package casino.gamingmachine;

import casino.bet.Bet;
import casino.bet.BetID;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import casino.cashier.*;
import gamblingauthoritiy.BetLoggingAuthority;
import gamblingauthoritiy.IBetLoggingAuthority;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
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

        //moneyInCard = 5L
        Cashier cashier = mock(Cashier.class);
        GamingMachine sut = new GamingMachine(cashier);
        BetResult betResult = mock(BetResult.class);
        BetID betID = new BetID();
        MoneyAmount moneyAmount = mock(MoneyAmount.class);
        Bet bet = mock(Bet.class);
        GamblerCard gamblerCard = new GamblerCard();
        gamblerCard.setMoneyAmountInCents(3L);;
        MoneyAmount moneyAmountActual = new MoneyAmount(3L);

        when(betResult.getWinningBet()).thenReturn(bet);
        when(betResult.getWinningBet().getMoneyAmount()).thenReturn(moneyAmount);
        when(betResult.getWinningBet().getBetID()).thenReturn(betID);
        when(betResult.getWinningBet().getMoneyAmount().getAmountInCents()).thenReturn(5L);

        sut.connectCard(gamblerCard);

        sut.setBet(new Bet(betID,moneyAmountActual));

        sut.acceptWinner(betResult);


        try {
            verify(cashier).addAmount(gamblerCard, moneyAmountActual);
            assertEquals(8L,gamblerCard.getMoneyAmountInCents());
        } catch (InvalidAmountException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void Machine_shouldReturnGamingMachineId() {

        GamingMachine sut = new GamingMachine(new Cashier(iBetLoggingAuthority));

        assertThat(sut.getGamingMachineID()).isInstanceOf(GamingMachineID.class);

    }

}