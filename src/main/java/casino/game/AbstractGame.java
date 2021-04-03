package casino.game;


import casino.cashier.Cashier;
import gamblingauthoritiy.IBetLoggingAuthority;
import gamblingauthoritiy.IBetTokenAuthority;

abstract class AbstractGame implements IGame{

    protected IBetLoggingAuthority betLoggingAuthority;
    protected IBetTokenAuthority betTokenAuthority;
    protected IGameRule gameRule;

    /**
     * @param cashier
     * @param gameRule
     */
    public AbstractGame(Cashier cashier, IGameRule gameRule) {
        this.betLoggingAuthority = cashier.getBettingAuthority().getLoggingAuthority();
        this.betTokenAuthority = cashier.getBettingAuthority().getTokenAuthority();
        this.gameRule = gameRule;
    }

    //public Cashier getCashier() {
    //    return cashier;
    //}

    //public IGameRule getGameRule() {
    //    return gameRule;
    //}

    protected void setGameRule(IGameRule gameRule) {
        this.gameRule = gameRule;
    }
}
