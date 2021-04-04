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
    private final GeneralID bettingRoundID;
    private final BetToken token;
    /**
     * @should create new Set of Bets and bettingRoundID and BetToken
     */
    public BettingRound(GeneralID id, BetToken token) {
        bets = new HashSet<>();
        bettingRoundID = id;
        this.token = token;
    }
    /**
     * @return BettingRoundID
     */
    @Override
    public BettingRoundID getBettingRoundID() {
        return (BettingRoundID) bettingRoundID;
    }

    /**
     * add a bet to the current bettinground.
     * <p>
     * <p>
     * Note: also use the appropiate required methods from the gambling authority API
     * @should add bet to set
     * @should return false if amount is negative
     * @should throw IllegalArgumentException if bet is null
     * @param bet
     * @return true if bet is made, otherwise folse
     * @throws IllegalArgumentException when Bet is null
     */
    @Override
    public boolean placeBet(Bet bet) throws IllegalArgumentException {
        if (bet == null) throw new IllegalArgumentException("Bet is null");
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
        return token;
    }

    /**
     * @should return size of bets set
     * @return number of bets made in the betting round
     */
    @Override
    public int numberOFBetsMade() {
        return bets.size();
    }
}
