package casino.game;

import casino.bet.Bet;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import casino.gamingmachine.GamingMachine;
import casino.gamingmachine.GamingMachineID;
import gamblingauthoritiy.BetLoggingAuthority;
import gamblingauthoritiy.BetToken;
import gamblingauthoritiy.BetTokenAuthority;
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
    private BetTokenAuthority betTokenAuthority;
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
        game = new DefaultGame(gameRule, currentRound, betLoggingAuthority, betTokenAuthority);

        game.startBettingRound();

        verify(betLoggingAuthority).logStartBettingRound(any(IBettingRound.class));
    }

    /**
     * @verifies return true if the number of bets equal the max number of bets per round defined by the game rule
     * @see DefaultGame#isBettingRoundFinished()
     */
    @Test
    public void isBettingRoundFinished_shouldReturnTrueIfTheNumberOfBetsEqualTheMaxNumberOfBetsPerRoundDefinedByTheGameRule() {
        BettingRound currentRound = mock(BettingRound.class);
        game = new DefaultGame(gameRule, currentRound, betLoggingAuthority, betTokenAuthority);
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
        game = new DefaultGame(gameRule, currentRound, betLoggingAuthority, betTokenAuthority);
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
        DefaultGame gameSpy = spy(new DefaultGame(gameRule, currentRound, betLoggingAuthority, betTokenAuthority));
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
        DefaultGame gameSpy = spy(new DefaultGame(gameRule, currentRound, betLoggingAuthority, betTokenAuthority));
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
        DefaultGame gameSpy = spy(new DefaultGame(gameRule, currentRound, betLoggingAuthority, betTokenAuthority));
        doReturn(false).when(gameSpy).isBettingRoundFinished();
        when(bet.getMoneyAmount()).thenReturn(mock(MoneyAmount.class));
        when(gamingMachine.placeBet(anyLong())).thenReturn(true);

        gameSpy.acceptBet(bet, gamingMachine);

        verify(gameSpy, times(0)).determineWinner();
    }

    /**
     * @verifies throw NoCurrentRoundException when no betting round is active
     * @see DefaultGame#acceptBet(Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    public void acceptBet_shouldThrowNoCurrentRoundExceptionWhenNoBettingRoundIsActive() throws Exception {
        BettingRound currentRound = null;
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        DefaultGame game = new DefaultGame(gameRule, currentRound, betLoggingAuthority, betTokenAuthority);
        when(bet.getMoneyAmount()).thenReturn(mock(MoneyAmount.class));
        when(gamingMachine.placeBet(anyLong())).thenReturn(true);

        assertThatExceptionOfType(NoCurrentRoundException.class)
                .isThrownBy(() -> {
                    game.acceptBet(bet, gamingMachine);
                });
    }

    /**
     * @verifies log accepted bet to betlogging authority
     * @see DefaultGame#acceptBet(Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    public void acceptBet_shouldLogAcceptedBetToBetloggingAuthority() throws Exception {
        BettingRound currentRound = mock(BettingRound.class);
        BettingRoundID bettingRoundID = mock(BettingRoundID.class);
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        GamingMachineID gamingMachineID = mock(GamingMachineID.class);
        when(currentRound.getBettingRoundID()).thenReturn(bettingRoundID);
        game = new DefaultGame(gameRule, currentRound, betLoggingAuthority, betTokenAuthority);

        game.acceptBet(bet, gamingMachine);

        verify(betLoggingAuthority).logAddAcceptedBet(bet, bettingRoundID, gamingMachineID);
    }

    /**
     * @verifies end the current round
     * @see DefaultGame#determineWinner()
     */
    @Test
    public void determineWinner_shouldEndTheCurrentRound() {
        BettingRound currentRound = mock(BettingRound.class);
        DefaultGame game = spy(new DefaultGame(gameRule, currentRound, betLoggingAuthority, betTokenAuthority));

        game.determineWinner();

        assertThat(game.getBettingRound()).isNull();
    }

    /**
     * @verifies log to betting authority
     * @see DefaultGame#determineWinner()
     */
    @Test
    public void determineWinner_shouldLogToBettingAuthority() throws Exception {
        BettingRound currentRound = mock(BettingRound.class);
        when(currentRound.numberOFBetsMade()).thenReturn(2);
        BetResult betResult = mock(BetResult.class);
        game = new DefaultGame(gameRule, currentRound, betLoggingAuthority, betTokenAuthority);
        when(gameRule.determineWinner(anyInt(), any(Set.class))).thenReturn(betResult);

        game.determineWinner();

        verify(betLoggingAuthority).logEndBettingRound(currentRound, betResult);
    }

    /**
     * @verifies notify all connected game machines
     * @see DefaultGame#determineWinner()
     */
    @Test
    public void determineWinner_shouldNotifyAllConnectedGameMachines() throws Exception {
        BettingRound currentRound = mock(BettingRound.class);
        when(currentRound.numberOFBetsMade()).thenReturn(2);
        BetResult betResult = mock(BetResult.class);
        when(gameRule.determineWinner(anyInt(), any(Set.class))).thenReturn(betResult);
        GamingMachine machineA = spy(new GamingMachine());
        GamingMachine machineB = spy(new GamingMachine());
        GamingMachine machineC = spy(new GamingMachine());
        game = new DefaultGame(gameRule, currentRound, betLoggingAuthority, betTokenAuthority);
        game.connectGamingMachine(machineA);
        game.connectGamingMachine(machineB);
        game.connectGamingMachine(machineC);

        game.determineWinner();

        assertThat(game.getGamingMachines().size()).isEqualTo(3);
        verify(machineA).acceptWinner(betResult);
        verify(machineB).acceptWinner(betResult);
        verify(machineC).acceptWinner(betResult);
    }

    /**
     * @verifies only log to betlogging authority if no bets have been made
     * @see DefaultGame#determineWinner()
     */
    @Test
    public void determineWinner_shouldOnlyLogToBetloggingAuthorityIfNoBetsHaveBeenMade() throws Exception {
        BettingRound currentRound = mock(BettingRound.class);
        when(currentRound.numberOFBetsMade()).thenReturn(0);
        IGameRule gameRuleSpy = spy(IGameRule.class);
        game = new DefaultGame(gameRuleSpy, currentRound, betLoggingAuthority, betTokenAuthority);

        game.determineWinner();

        verify(gameRuleSpy, times(0)).determineWinner(anyInt(), any(Set.class));
        verify(betLoggingAuthority).logEndBettingRound(currentRound, null);
    }

    /**
     * @verifies get randomwinvalue from bettoken authority using token from betting round
     * @see DefaultGame#determineWinner()
     */
    @Test
    public void determineWinner_shouldGetRandomwinvalueFromBettokenAuthorityUsingTokenFromBettingRound() throws Exception {
        BetToken betToken = mock(BetToken.class);
        BettingRound currentRound = mock(BettingRound.class);
        when(currentRound.numberOFBetsMade()).thenReturn(2);
        when(currentRound.getAllBetsMade()).thenReturn(mock(Set.class));
        when(currentRound.getBetToken()).thenReturn(betToken);
        game = new DefaultGame(gameRule, currentRound, betLoggingAuthority, betTokenAuthority);
        when(betTokenAuthority.getRandomInteger(betToken)).thenReturn(42);

        game.determineWinner();

        verify(betTokenAuthority).getRandomInteger(betToken);
    }
}
