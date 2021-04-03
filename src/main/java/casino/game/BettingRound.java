package casino.game;

import casino.idfactory.GeneralID;
import casino.idfactory.IDFactory;
import gamblingauthoritiy.BetToken;
import gamblingauthoritiy.BetTokenAuthority;
import gamblingauthoritiy.IBetLoggingAuthority;
import casino.bet.Bet;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 */
public class BettingRound implements IBettingRound {

    private final Set<Bet> bets;
    private final IBetLoggingAuthority betLoggingAuthority;
    /**
     * @should create new Set of Bets
     * @should set BetLoggingAuthority
     */
    public BettingRound(IBetLoggingAuthority betLoggingAuthority) {
        bets = new HashSet<>();
        this.betLoggingAuthority = betLoggingAuthority;
    }
    /**
     * @should create and return new BettingRoundID
     * @return BettingRoundID
     */
    @Override
    public BettingRoundID getBettingRoundID() {
        return (BettingRoundID) IDFactory.generateID("BETTINGROUNDID");
    }

    /**
     * add a bet to the current bettinground.
     * <p>
     * <p>
     * Note: also use the appropiate required methods from the gambling authority API
     * @should add bet to set
     * @should return false if amount is negative
     * @should log bet to BetLoggingAuthority if successful
     * @should throw IllegalArgumentException if bet is null
     * @param bet
     * @return true if bet is made, otherwise folse
     * @throws IllegalArgumentException when Bet is null
     */
    @Override
    public boolean placeBet(Bet bet) throws IllegalArgumentException {
        long amount = bet.getMoneyAmount().getAmountInCents();
        if (amount < 0) {
            return false;
        }
        this.bets.add(bet);
        return true;
    }

    /**
     * @return set of all bets made in this betting round.
     */
    @Override
    public Set<Bet> getAllBetsMade() {
        return bets;
    }

    /**
     * @return betToken from this betting round.
     */
    @Override
    public BetToken getBetToken() {
        return null;
    }

    /**
     * @return number of bets made in the betting round
     */
    @Override
    public int numberOFBetsMade() {
        return 0;
    }

    public IBetLoggingAuthority getBetLoggingAuthority() {
        return betLoggingAuthority;
    }
}
