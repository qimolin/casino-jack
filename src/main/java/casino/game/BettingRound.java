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

    private final GeneralID bettingRoundID;
    private final BetToken betToken;
    /**
     * @should create a BettingRound and set bettingRoundID and betToken
     */
    public BettingRound(BettingRoundID id, BetToken token) {
        this.bettingRoundID = id;
        this.betToken = token;
    }
    /**
     * @should create and return new BettingRoundID
     * @return BettingRoundID
     */
    @Override
    public BettingRoundID getBettingRoundID() {
        return null;
    }

    /**
     * add a bet to the current bettinground.
     * <p>
     * <p>
     * Note: also use the appropiate required methods from the gambling authority API
     *
     * @param bet
     * @return true if bet is made, otherwise folse
     * @throws IllegalArgumentException when Bet is null
     */
    @Override
    public boolean placeBet(Bet bet) throws IllegalArgumentException {
        return false;
    }

    /**
     * @return set of all bets made in this betting round.
     */
    @Override
    public Set<Bet> getAllBetsMade() {
        return null;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BettingRound)) return false;
        BettingRound that = (BettingRound) o;
        return bettingRoundID.equals(that.bettingRoundID) && betToken.equals(that.betToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bettingRoundID, betToken);
    }
}
