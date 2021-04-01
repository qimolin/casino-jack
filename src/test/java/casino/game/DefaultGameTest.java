package casino.game;

import casino.bet.Bet;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import casino.gamingmachine.GamingMachine;
import gamblingauthoritiy.BetLoggingAuthority;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DefaultGameTest {

    @Mock
    private BetLoggingAuthority betLoggingAuthority;
    @Mock
    private IGameRule gameRule;

    private AutoCloseable closeable;
    private DefaultGame game;

    @BeforeEach
    void initAuthorities() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeAuthorities() throws Exception {
        closeable.close();
    }

    /**
     * @verifies log to BettingAuthority
     * @see DefaultGame#startBettingRound()
     */
    @Test
    public void startBettingRound_shouldLogToBettingAuthority() throws NoBetsMadeException {
        BettingRound currentRound = mock(BettingRound.class);
        BetResult betResult = mock(BetResult.class);
        game = new DefaultGame(gameRule, currentRound, betLoggingAuthority);
        when(gameRule.determineWinner(anyInt(), any(Set.class))).thenReturn(betResult);

        game.startBettingRound();

        verify(betLoggingAuthority).logEndBettingRound(currentRound, betResult);
        verify(betLoggingAuthority).logStartBettingRound(any(IBettingRound.class));
    }

    /**
     * @verifies return true if the number of bets equal the max number of bets per round defined by the game rule
     * @see DefaultGame#isBettingRoundFinished()
     */
    @Test
    public void isBettingRoundFinished_shouldReturnTrueIfTheNumberOfBetsEqualTheMaxNumberOfBetsPerRoundDefinedByTheGameRule() {
        BettingRound currentRound = mock(BettingRound.class);
        game = new DefaultGame(gameRule, currentRound, betLoggingAuthority);
        when(gameRule.getMaxBetsPerRound()).thenReturn(5);
        when(currentRound.numberOFBetsMade()).thenReturn(5);

        assertThat(game.isBettingRoundFinished()).isTrue();
    }

    /**
     * @verifies return false if the number of bets are less the max number of bets per round defined by the game rule
     * @see DefaultGame#isBettingRoundFinished()
     */
    @Test
    public void isBettingRoundFinished_shouldReturnFalseIfTheNumberOfBetsAreLessTheMaxNumberOfBetsPerRoundDefinedByTheGameRule() {
        BettingRound currentRound = mock(BettingRound.class);
        game = new DefaultGame(gameRule, currentRound, betLoggingAuthority);
        when(gameRule.getMaxBetsPerRound()).thenReturn(5);
        when(currentRound.numberOFBetsMade()).thenReturn(4);

        assertThat(game.isBettingRoundFinished()).isFalse();
    }

    /**
     * @verifies check that the current betting round is finished
     * @see DefaultGame#acceptBet(casino.bet.Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    public void acceptBet_shouldCheckThatTheCurrentBettingRoundIsFinished() throws Exception {
        BettingRound currentRound = mock(BettingRound.class);
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        DefaultGame gameSpy = spy(new DefaultGame(gameRule, currentRound, betLoggingAuthority));
        when(bet.getMoneyAmount()).thenReturn(mock(MoneyAmount.class));
        when(gamingMachine.placeBet(anyLong())).thenReturn(true);

        gameSpy.acceptBet(bet, gamingMachine);

        verify(gameSpy).isBettingRoundFinished();
    }

    /**
     * @verifies determine the winner if the current betting round is finished
     * @see DefaultGame#acceptBet(Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    public void acceptBet_shouldDetermineTheWinnerIfTheCurrentBettingRoundIsFinished() throws Exception {
        BettingRound currentRound = mock(BettingRound.class);
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        DefaultGame gameSpy = spy(new DefaultGame(gameRule, currentRound, betLoggingAuthority));
        doReturn(true).when(gameSpy).isBettingRoundFinished();
        when(bet.getMoneyAmount()).thenReturn(mock(MoneyAmount.class));
        when(gamingMachine.placeBet(anyLong())).thenReturn(true);

        gameSpy.acceptBet(bet, gamingMachine);

        verify(gameSpy).determineWinner();
    }

    /**
     * @verifies continue if the current betting round is not finished
     * @see DefaultGame#acceptBet(Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    public void acceptBet_shouldContinueIfTheCurrentBettingRoundIsNotFinished() throws Exception {
        BettingRound currentRound = mock(BettingRound.class);
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        DefaultGame gameSpy = spy(new DefaultGame(gameRule, currentRound, betLoggingAuthority));
        doReturn(false).when(gameSpy).isBettingRoundFinished();
        when(bet.getMoneyAmount()).thenReturn(mock(MoneyAmount.class));
        when(gamingMachine.placeBet(anyLong())).thenReturn(true);

        gameSpy.acceptBet(bet, gamingMachine);

        verify(gameSpy, times(0)).determineWinner();
    }

    /**
     * @verifies return false if the bet is invalid
     * @see DefaultGame#acceptBet(Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    public void acceptBet_shouldReturnFalseIfTheBetIsInvalid() throws Exception {
        BettingRound currentRound = mock(BettingRound.class);
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        DefaultGame game = spy(new DefaultGame(gameRule, currentRound, betLoggingAuthority));
        when(bet.getMoneyAmount()).thenReturn(mock(MoneyAmount.class));
        when(gamingMachine.placeBet(anyLong())).thenReturn(false);

        assertThat(game.acceptBet(bet, gamingMachine)).isFalse();
    }

    /**
     * @verifies return true if the bet is valid
     * @see DefaultGame#acceptBet(Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    public void acceptBet_shouldReturnTrueIfTheBetIsValid() throws Exception {
        BettingRound currentRound = mock(BettingRound.class);
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        DefaultGame game = spy(new DefaultGame(gameRule, currentRound, betLoggingAuthority));
        when(bet.getMoneyAmount()).thenReturn(mock(MoneyAmount.class));
        when(gamingMachine.placeBet(anyLong())).thenReturn(true);

        assertThat(game.acceptBet(bet, gamingMachine)).isTrue();
    }
}
