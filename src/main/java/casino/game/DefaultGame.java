package casino.game;


import casino.bet.Bet;
import casino.bet.BetID;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import casino.gamingmachine.GamingMachine;
import casino.gamingmachine.IGamingMachine;
import casino.gamingmachine.NoPlayerCardException;
import gamblingauthoritiy.BetLoggingAuthority;
import gamblingauthoritiy.IBetLoggingAuthority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultGame extends AbstractGame {
    private BettingRound bettingRound;
    private IGameRule gameRule;
    private IBetLoggingAuthority betLoggingAuthority;
    private List<GamingMachine> gamingMachines;

    public DefaultGame(IGameRule gameRule, BettingRound bettingRound, IBetLoggingAuthority betLoggingAuthority) {
        this.gameRule = gameRule;
        this.bettingRound = bettingRound;
        this.betLoggingAuthority = betLoggingAuthority;
        this.gamingMachines = new ArrayList<>();
    }

    public BettingRound getBettingRound() {
        return bettingRound;
    }

    public List<GamingMachine> getGamingMachines() { return gamingMachines; }

    public void connectGamingMachine(GamingMachine gamingMachine) {
        gamingMachines.add(gamingMachine);
    }

    /**
     * create and start a new BettingRound.
     * when called when a current bettinground is active: the current bettinground ends and a new
     * bettinground is created, which becomes the current bettinground.
     * <p>
     * Note: also use the appropiate required methods from the gambling authority API
     *
     * @should
     * @should log to BettingAuthority
     */
    @Override
    public void startBettingRound() {
        determineWinner();

        bettingRound = new BettingRound();

        betLoggingAuthority.logStartBettingRound(bettingRound);
    }

    /**
     * Accept a bet on the current betting round.
     * determine if the betting round is finished, if so: determine the winner,
     * notify the connected gaming machines and start a new betting round.
     * <p>
     * Note: also use the appropiate required methods from the gambling authority API
     *
     * @param bet           the bet to be made on the betting round
     * @param gamingMachine gamingmachine which places bet on this game.
     * @return true when bet is accepted by the game, otherwise false.
     * @throws NoCurrentRoundException when no BettingRound is currently active.
     *
     * @should check that the current betting round is finished
     * @should determine the winner if the current betting round is finished
     * @should continue if the current betting round is not finished
     * @should return false if the bet is invalid
     * @should return true if the bet is valid
     * @should store accepted bet
     * @should not store accepted bet
     * @should call the required methods in the correct order
     * @should throw NoCurrentRoundException when no betting round is active
     */
    @Override
    public boolean acceptBet(Bet bet, IGamingMachine gamingMachine) throws NoCurrentRoundException, NoPlayerCardException {
        if (bettingRound == null) throw new NoCurrentRoundException();

        MoneyAmount betAmount = bet.getMoneyAmount();
        if (!gamingMachine.placeBet(betAmount.getAmountInCents())) {
            return false;
        }

        if (isBettingRoundFinished()) {
            determineWinner();
        }

        return true;
    }

    /**
     * End the current bettinground & calculate the winner using the gamerules.
     * notifiy all connected game machines of the BetResult.
     * When no bets have been made yet, no winner can be determined. In this case, only log to the betlogging authority,
     * and end the current bettinground.
     * <p>
     * Note: also use the appropiate required methods from the gambling authority API
     *
     * @should end the current round
     * @should log to betting authority
     * @should notify all connected game machines
     */
    @Override
    public void determineWinner() {
        BetResult winResult = null;
        try {
            winResult = gameRule.determineWinner(2, new HashSet<>());
            BetResult finalWinResult = winResult;
            gamingMachines.forEach(gamingMachine -> {
                gamingMachine.acceptWinner(finalWinResult);
            });
        } catch (NoBetsMadeException e) {
            e.printStackTrace();
        }

        betLoggingAuthority.logEndBettingRound(bettingRound, winResult);
        bettingRound = null;
    }

    /**
     * determine if the right number of bets are done (determined by gamerules) to be able to
     * calculate a winner.
     * for calculation a winner, a true random value needs to be received from the gambling authority API.
     * Note: also use the appropiate required methods from the gambling authority API
     *
     * @return true if all necessary bets are made in the betting round, otherwise false
     *
     * @should return true if the number of bets equal the max number of bets per round defined by the game rule
     * @should return false if the number of bets are less the max number of bets per round defined by the game rule
     */
    @Override
    public boolean isBettingRoundFinished() {
        return bettingRound.numberOFBetsMade() == gameRule.getMaxBetsPerRound();
    }
}
