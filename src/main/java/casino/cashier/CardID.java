package casino.cashier;

import casino.idfactory.GeneralID;

public class CardID extends GeneralID implements Comparable<GeneralID> {


    @Override
    public int compareTo(GeneralID o) {
        if (this.getID().equals(o.getID())){
            return 1; //true
        }else {
            return 0;//false
        }
    }
}
