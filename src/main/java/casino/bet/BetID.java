package casino.bet;

import casino.idfactory.GeneralID;

public class BetID extends GeneralID implements Comparable<GeneralID>{

    @Override
    public int compareTo(GeneralID o) {
        if (this.getID().equals(o.getID())){
            return 1; //true
        }else {
            return 0;//false
        }
    }
}
