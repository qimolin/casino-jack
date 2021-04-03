package casino.game;


import casino.cashier.ICashier;

abstract class AbstractGame implements IGame{

    private final ICashier cashier;
    private IGameRule gameRule;

    /**
     * @should set cashier and gameRule
     * @param cashier
     * @param gameRule
     */
    public AbstractGame(ICashier cashier, IGameRule gameRule) {

        this.cashier = cashier;
        this.gameRule = gameRule;
    }

    public ICashier getCashier() {
        return cashier;
    }

    public IGameRule getGameRule() {
        return gameRule;
    }

    public void setGameRule(IGameRule gameRule) {
        this.gameRule = gameRule;
    }
}
