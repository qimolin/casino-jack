package casino.cashier;

import casino.idfactory.GeneralID;

public class CardID extends GeneralID implements Comparable<GeneralID> {


    /***
     * @should Generate And Return An ID
     * @should compare ids and return false
     * @should compare ids and return true
     * @param o
     * @return
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
