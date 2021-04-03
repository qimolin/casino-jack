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
     * @verifies should return false Money in card less than bet
     * @see GamingMachine#placeBet(long)
     */
    @Test
    public void placeBet_shouldShouldReturnFalseMoneyInCardLessThanBet() throws Exception {
        long moneyInCard = 5L;
        long moneyToBet = 6L;
        GamingMachine sut = new GamingMachine(new Cashier(iBetLoggingAuthority));
        GamblerCard gamblerCard = mock(GamblerCard.class);
        when(gamblerCard.getMoneyAmountInCents()).thenReturn(moneyInCard);
        sut.connectCard(gamblerCard);

        assertFalse(sut.placeBet(moneyToBet));
    }

    /**
     * @verifies throw NoPlayerCardException
     * @see GamingMachine#placeBet(long)
     */
    @Test
    public void placeBet_shouldThrowNoPlayerCardException() throws Exception {
        GamingMachine sut = new GamingMachine(new Cashier(iBetLoggingAuthority));
        NoPlayerCardException exception = assertThrows(NoPlayerCardException.class, () ->
                sut.placeBet(2L));
        assertEquals("No card exception", exception.getMessage());
    }

    /**
     * @verifies place bet
     * @see GamingMachine#placeBet(long)
     */
    @Test
    public void placeBet_shouldPlaceBet() throws Exception {
        long moneyInCard = 5L;
        long moneyToBet = 2L;
        GamingMachine sut = new GamingMachine(new Cashier(iBetLoggingAuthority));
        GamblerCard gamblerCard = mock(GamblerCard.class);
        when(gamblerCard.getMoneyAmountInCents()).thenReturn(moneyInCard);
        sut.connectCard(gamblerCard);

        assertTrue(sut.placeBet(moneyToBet));
    }

    /**
     * @verifies throw a CurrentBetMadeException
     * @see GamingMachine#disconnectCard()
     */
    @Test
    public void disconnectCard_shouldThrowACurrentBetMadeException() throws Exception {
        //TODO auto-generated
        Assertions.fail("Not yet implemented");
    }

    /**
     * @verifies generateId
     * @see GamingMachine#GamingMachine(Cashier)
     */
    @Test
    public void GamingMachine_shouldGenerateId() throws Exception {
        GamingMachine sut = new GamingMachine(new Cashier(iBetLoggingAuthority));

        assertThat(sut.getGamingMachineID()).isInstanceOf(GamingMachineID.class);
    }

    /**
     * @verifies accept winner and update card amount
     * @see GamingMachine#acceptWinner(BetResult)
     */
    @Test
    public void acceptWinner_shouldAcceptWinnerAndUpdateCardAmount() throws Exception {
        long BET_AMOUNT = 3L;
        long CARD_BALANCE = 5L;

        Cashier cashier = mock(Cashier.class);
        GamingMachine sut = new GamingMachine(cashier);
        BetResult betResult = mock(BetResult.class);
        BetID betID = new BetID();
        MoneyAmount moneyAmount = mock(MoneyAmount.class);
        Bet bet = mock(Bet.class);
        GamblerCard gamblerCard = new GamblerCard();
        gamblerCard.setMoneyAmountInCents(CARD_BALANCE);;
        MoneyAmount moneyAmountActual = new MoneyAmount(BET_AMOUNT);

        when(betResult.getWinningBet()).thenReturn(bet);
        when(betResult.getWinningBet().getMoneyAmount()).thenReturn(moneyAmount);
        when(betResult.getWinningBet().getBetID()).thenReturn(betID);
        when(betResult.getWinningBet().getMoneyAmount().getAmountInCents()).thenReturn(BET_AMOUNT);

        sut.connectCard(gamblerCard);

        sut.setBet(new Bet(betID,moneyAmountActual));

        sut.acceptWinner(betResult);


        try {
            verify(cashier).addAmount(gamblerCard, moneyAmountActual);
            //should pass After Mikels implementation
            assertEquals(8L,gamblerCard.getMoneyAmountInCents());
        } catch (InvalidAmountException e) {
            e.printStackTrace();
        }
    }


}