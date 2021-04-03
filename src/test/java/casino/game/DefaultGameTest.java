package casino.game;

import casino.bet.Bet;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import casino.gamingmachine.GamingMachine;
import casino.gamingmachine.GamingMachineID;
import casino.idfactory.GeneralID;
import casino.idfactory.IDFactory;
import gamblingauthoritiy.BetLoggingAuthority;
import gamblingauthoritiy.BetToken;
import gamblingauthoritiy.BetTokenAuthority;
import org.junit.jupiter.api.*;
import org.mockito.*;

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
    @Mock
    private BettingRound currentRound;

    private AutoCloseable closeable;

    @InjectMocks
    private DefaultGame game = new DefaultGame();

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
    public void startBettingRound_shouldLogToBettingAuthority() {
        game.startBettingRound();

        verify(betLoggingAuthority).logStartBettingRound(any(BettingRound.class));
    }

    /**
     * @verifies end active betting round
     * @see DefaultGame#startBettingRound()
     */
    @Test
    public void startBettingRound_shouldEndActiveBettingRound() throws Exception {
        when(currentRound.getBettingRoundID()).thenReturn(mock(BettingRoundID.class));
        when(gameRule.determineWinner(anyInt(), any(Set.class))).thenReturn(null);

        game.startBettingRound();

        assertThat(game.getBettingRound()).isNotEqualTo(currentRound);
        verify(betLoggingAuthority).logEndBettingRound(currentRound, null);
    }

    /**
     * @verifies create a new betting round using the API
     * @see DefaultGame#startBettingRound()
     */
    @Test
    @Disabled
    public void startBettingRound_shouldCreateANewBettingRoundUsingTheAPI() throws Exception {
        GeneralID bettingRoundID = mock(BettingRoundID.class);
        MockedStatic<IDFactory> mockedIDFactory = Mockito.mockStatic(IDFactory.class);
        mockedIDFactory.when(() -> { IDFactory.generateID(anyString()); }).thenReturn(bettingRoundID);
        when(currentRound.getBettingRoundID()).thenReturn((BettingRoundID) bettingRoundID);

        game.startBettingRound();

        assertThat(game.getBettingRound().getBettingRoundID()).isNotEqualTo(currentRound.getBettingRoundID());
        verify(betTokenAuthority).getBetToken((BettingRoundID) bettingRoundID);
    }

    /**
     * @verifies return true if the number of bets equal the max number of bets per round defined by the game rule
     * @see DefaultGame#isBettingRoundFinished()
     */
    @Test
    public void isBettingRoundFinished_shouldReturnTrueIfTheNumberOfBetsEqualTheMaxNumberOfBetsPerRoundDefinedByTheGameRule() {
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
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        DefaultGame gameSpy = spy(game);
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
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        DefaultGame gameSpy = spy(game);
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
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        DefaultGame gameSpy = spy(game);
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
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        game = new DefaultGame();
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
        BettingRoundID bettingRoundID = mock(BettingRoundID.class);
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        GamingMachineID gamingMachineID = mock(GamingMachineID.class);
        when(currentRound.getBettingRoundID()).thenReturn(bettingRoundID);
        when(gamingMachine.getGamingMachineID()).thenReturn(gamingMachineID);

        game.acceptBet(bet, gamingMachine);

        verify(betLoggingAuthority).logAddAcceptedBet(bet, bettingRoundID, gamingMachineID);
    }

    /**
     * @verifies store accepted bet
     * @see DefaultGame#acceptBet(Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    @Disabled
    public void acceptBet_shouldStoreAcceptedBet() throws Exception {
        GamingMachine gamingMachine = mock(GamingMachine.class);
        Bet bet = mock(Bet.class);

        game.acceptBet(bet, gamingMachine);

        assertThat(currentRound.getAllBetsMade()).contains(bet);
    }

    /**
     * @verifies start a new round if the round is finished
     * @see DefaultGame#acceptBet(Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    public void acceptBet_shouldStartANewRoundIfTheRoundIsFinished() throws Exception {
        GamingMachine gamingMachine = mock(GamingMachine.class);
        Bet bet = mock(Bet.class);

        DefaultGame gameSpy = spy(game);

        gameSpy.acceptBet(bet, gamingMachine);

        verify(gameSpy).startBettingRound();
    }

    /**
     * @verifies end the current round
     * @see DefaultGame#determineWinner()
     */
    @Test
    public void determineWinner_shouldEndTheCurrentRound() {
        DefaultGame gameSpy = spy(game);

        gameSpy.determineWinner();

        assertThat(gameSpy.getBettingRound()).isNull();
    }

    /**
     * @verifies log to betting authority
     * @see DefaultGame#determineWinner()
     */
    @Test
    public void determineWinner_shouldLogToBettingAuthority() throws Exception {
        when(currentRound.numberOFBetsMade()).thenReturn(2);
        BetResult betResult = mock(BetResult.class);
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
        when(currentRound.numberOFBetsMade()).thenReturn(2);
        BetResult betResult = mock(BetResult.class);
        when(gameRule.determineWinner(anyInt(), any(Set.class))).thenReturn(betResult);
        GamingMachine machineA = mock(GamingMachine.class);
        GamingMachine machineB = mock(GamingMachine.class);
        GamingMachine machineC = mock(GamingMachine.class);
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
        when(currentRound.numberOFBetsMade()).thenReturn(0);
        IGameRule gameRuleSpy = spy(IGameRule.class);

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
        when(currentRound.numberOFBetsMade()).thenReturn(2);
        when(currentRound.getAllBetsMade()).thenReturn(mock(Set.class));
        when(currentRound.getBetToken()).thenReturn(betToken);
        when(betTokenAuthority.getRandomInteger(betToken)).thenReturn(42);

        game.determineWinner();

        verify(betTokenAuthority).getRandomInteger(betToken);
    }
}
