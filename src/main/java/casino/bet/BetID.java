package casino.bet;

import casino.idfactory.GeneralID;

public class BetID extends GeneralID implements Comparable<GeneralID>{

    /**
     * @should return 1 if equal else 0
     * @param o
     * @return true or false
     */
    @Override
    public int compareTo(GeneralID o) {
        if (this.getID().equals(o.getID())){
            return 1; //true
        }else {
            return 0;//false
        }
    }
}
