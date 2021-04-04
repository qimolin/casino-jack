package casino.game;

import casino.bet.Bet;
import casino.bet.MoneyAmount;
import casino.idfactory.GeneralID;
import gamblingauthoritiy.BetToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BettingRoundTest {

    @Mock
    private BettingRoundID bettingRoundID;
    @Mock
    private BetToken betToken;
    @Mock
    private Bet bet;

    AutoCloseable autoCloseable;

    @BeforeEach
    void initAuthorities() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeAuthorities() throws Exception {
        autoCloseable.close();
    }
    /**
     * @verifies create new Set of Bets and bettingRoundID and BetToken
     * @see BettingRound#BettingRound(GeneralID, BetToken)
     */
    @Test
    public void BettingRound_shouldCreateNewSetOfBetsAndBettingRoundIDAndBetToken() throws Exception {
        // Arrange
        BettingRound sut = new BettingRound(bettingRoundID, betToken);
        // Act
        // Assert
        assertThat(sut.getAllBetsMade()).isNotNull();
        assertThat(sut.getBettingRoundID()).isNotNull();
        assertThat(sut.getBetToken()).isNotNull();
    }

    /**
     * @verifies create new Set of Bets
     * @see BettingRound#BettingRound(GeneralID, BetToken)
     */
    @Test
    public void BettingRound_shouldCreateNewSetOfBets() {
        // Arrange
        BettingRound sut = new BettingRound(bettingRoundID, betToken);
        // Act
        // Assert
        assertThat(sut.getAllBetsMade()).isNotNull();
    }

    /**
     * @verifies add bet to set
     * @see BettingRound#placeBet(Bet)
     */
    @Test
    public void placeBet_shouldAddBetToSet() {
        // Arrange
        BettingRound sut = new BettingRound(bettingRoundID, betToken);
        MoneyAmount moneyAmount = mock(MoneyAmount.class);
        // Act
        when(moneyAmount.getAmountInCents()).thenReturn(2L);
        when(bet.getMoneyAmount()).thenReturn(moneyAmount);
        sut.placeBet(bet);
        // Assert
        assertThat(sut.getAllBetsMade()).isNotEmpty();
    }

    /**
     * @verifies return false if amount is negative
     * @see BettingRound#placeBet(Bet)
     */
    @Test
    public void placeBet_shouldReturnFalseIfAmountIsNegative() {
        // Arrange
        BettingRound sut = new BettingRound(bettingRoundID, betToken);
        MoneyAmount moneyAmount = mock(MoneyAmount.class);
        // Act
        when(moneyAmount.getAmountInCents()).thenReturn(-2L);
        when(bet.getMoneyAmount()).thenReturn(moneyAmount);
        // Assert
        assertThat(sut.placeBet(bet)).isFalse();
    }

    /**
     * @verifies throw IllegalArgumentException if bet is null
     * @see BettingRound#placeBet(Bet)
     */
    @Test
    public void placeBet_shouldThrowIllegalArgumentExceptionIfBetIsNull() {
        // Arrange
        BettingRound sut = new BettingRound(bettingRoundID, betToken);
        Bet bet = null;
        // Act
        // Assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    sut.placeBet(bet);
                });
    }


    /**
     * @verifies return size of bets set
     * @see BettingRound#numberOFBetsMade()
     */
    @Test
    public void numberOFBetsMade_shouldReturnSizeOfBetsSet() throws Exception {
        // Arrange
        BettingRound sut = new BettingRound(bettingRoundID, betToken);
        MoneyAmount moneyAmount = mock(MoneyAmount.class);
        // Act
        when(moneyAmount.getAmountInCents()).thenReturn(2L);
        when(bet.getMoneyAmount()).thenReturn(moneyAmount);

        sut.placeBet(bet);
        // Assert
        assertThat(sut.numberOFBetsMade()).isEqualTo(1);
    }
}
