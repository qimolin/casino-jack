package casino.game;


import casino.bet.Bet;
import casino.bet.BetID;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import casino.gamingmachine.GamingMachine;
import casino.gamingmachine.IGamingMachine;
import casino.gamingmachine.NoPlayerCardException;
import casino.idfactory.GeneralID;
import casino.idfactory.IDFactory;
import gamblingauthoritiy.BetLoggingAuthority;
import gamblingauthoritiy.BetToken;
import gamblingauthoritiy.IBetLoggingAuthority;
import gamblingauthoritiy.IBetTokenAuthority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultGame extends AbstractGame {
    final private IBetLoggingAuthority betLoggingAuthority;
    final private IBetTokenAuthority betTokenAuthority;

    private BettingRound bettingRound;
    final private IGameRule gameRule;

    private List<GamingMachine> gamingMachines;

    public DefaultGame(IGameRule gameRule, BettingRound bettingRound, IBetLoggingAuthority betLoggingAuthority,
                       IBetTokenAuthority betTokenAuthority) {
        this.gameRule = gameRule;
        this.bettingRound = bettingRound;
        this.betLoggingAuthority = betLoggingAuthority;
        this.betTokenAuthority = betTokenAuthority;
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
     * @should end active betting round
     * @should create a new betting round using the API
     * @should log to BettingAuthority
     */
    @Override
    public void startBettingRound() {
        if (bettingRound != null) {
            determineWinner();
        }

        GeneralID bettingRoundID = IDFactory.generateID("BETTINGROUND");
        BetToken betToken = betTokenAuthority.getBetToken((BettingRoundID) bettingRoundID);

        //bettingRound = new BettingRound(bettingRoundID, betToken);
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
     * @should log accepted bet to betlogging authority
     * @should store accepted bet
     * @should start a new round if the round is finished
     * @should throw NoCurrentRoundException when no betting round is active
     */
    @Override
    public boolean acceptBet(Bet bet, IGamingMachine gamingMachine) throws NoCurrentRoundException {
        if (bettingRound == null) throw new NoCurrentRoundException();

        betLoggingAuthority.logAddAcceptedBet(
                bet, bettingRound.getBettingRoundID(), gamingMachine.getGamingMachineID());

        if (isBettingRoundFinished()) {
            determineWinner();
            startBettingRound();
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
     * @should only log to betlogging authority if no bets have been made
     * @should get randomwinvalue from bettoken authority using token from betting round
     */
    @Override
    public void determineWinner() {
        BetResult winResult = null;
        if (bettingRound.numberOFBetsMade() > 0) {
            try {
                int randomInt = betTokenAuthority.getRandomInteger(bettingRound.getBetToken());
                winResult = gameRule.determineWinner(randomInt, bettingRound.getAllBetsMade());
                BetResult finalWinResult = winResult;
                gamingMachines.forEach(gamingMachine -> {
                    gamingMachine.acceptWinner(finalWinResult);
                });
            } catch (NoBetsMadeException e) {
                System.out.println(e.getMessage());
            }
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
