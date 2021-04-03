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
        if (bets.size() != 0) {
            Bet winner = null;
            int index = 0;
            long totalBet = 0;
            for (Bet bet : bets) {
                if (index == randomWinValue) {
                    winner = bet;
                }
                totalBet += bet.getMoneyAmount().getAmountInCents();
                index++;
            }
            if (winner == null) {
                return null;
            } else {
                return new BetResult(winner, new MoneyAmount(totalBet));
            }
        }else {
            throw new NoBetsMadeException("No bets made");
        }

    }

    @Override
    public int getMaxBetsPerRound() {
        return maxBetsPerRound;
    }

    public void setMaxBetsPerRound(int maxBetsPerRound) {
        this.maxBetsPerRound = maxBetsPerRound;
    }
}
