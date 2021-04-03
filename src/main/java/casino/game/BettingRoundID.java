package casino.game;

import casino.idfactory.GeneralID;

public class BettingRoundID extends GeneralID implements Comparable<GeneralID> {
    @Override
    public int compareTo(GeneralID o) {
        if (this.getID().equals(o.getID())) {
            return 1;
        }

        return 0;
    }
}
