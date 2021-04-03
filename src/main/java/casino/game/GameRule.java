package casino.game;

import casino.bet.Bet;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;

import java.util.Set;

public class GameRule implements IGameRule{

    private int maxBetsPerRound;

    /**
     * @should throw no bets exception
     * @should return a random winner
     * @param randomWinValue
     * @param bets
     * @return
     * @throws NoBetsMadeException
     */
    @Override
    public BetResult determineWinner(Integer randomWinValue, Set<Bet> bets) throws NoBetsMadeException {


    }

    @Override
    public int getMaxBetsPerRound() {
        return maxBetsPerRound;
    }

    public void setMaxBetsPerRound(int maxBetsPerRound) {
        this.maxBetsPerRound = maxBetsPerRound;
    }
}
