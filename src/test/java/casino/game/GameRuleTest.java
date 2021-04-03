package casino.game;

import casino.bet.Bet;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import casino.gamingmachine.CurrentBetMadeException;
import casino.gamingmachine.GamingMachineID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameRuleTest {
    /**
     * @verifies return a random winner
     * @see GameRule#determineWinner(Integer, java.util.Set)
     */
    @Test
    public void determineWinner_shouldReturnARandomWinner() throws Exception {
        int MAX_BETS_PER_ROUND = 3;
        long USERS_BET = 5L;
        Integer winningNum = 2;
        GameRule sut = new GameRule();

        sut.setMaxBetsPerRound(MAX_BETS_PER_ROUND);
        Set<Bet> bets = new HashSet<>();
        for (int i = 0; i < MAX_BETS_PER_ROUND; i++) {
            bets.add(mock(Bet.class));
        }
        for (Bet bet : bets){
            when(bet.getMoneyAmount()).thenReturn(mock(MoneyAmount.class));
            when(bet.getMoneyAmount().getAmountInCents()).thenReturn(USERS_BET);
        }


        assertThat(sut.determineWinner(2, bets)).isInstanceOf(BetResult.class);
        assertEquals(USERS_BET * MAX_BETS_PER_ROUND , sut.determineWinner(2, bets).getAmountWon().getAmountInCents());

    }

    /**
     * @verifies throw no bets exception
     * @see GameRule#determineWinner(Integer, Set)
     */
    @Test
    public void determineWinner_shouldThrowNoBetsException() throws NoBetsMadeException {
        GameRule sut = new GameRule();
        //empty set of bets

        Set<Bet> bets = new HashSet<>();

        NoBetsMadeException exception = assertThrows(NoBetsMadeException.class,() -> sut.determineWinner(5,bets));
        assertEquals("No bets made", exception.getMessage());
    }
}
