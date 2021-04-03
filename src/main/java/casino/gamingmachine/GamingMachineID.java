package casino.gamingmachine;

import casino.idfactory.GeneralID;

public class GamingMachineID  extends GeneralID implements Comparable<GeneralID> {

    @Override
    public int compareTo(GeneralID o) {
        if (this.getID().equals(o.getID())) {
            return 1;
        }

        return 0;
    }
}
