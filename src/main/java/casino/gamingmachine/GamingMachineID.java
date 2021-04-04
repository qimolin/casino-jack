package casino.gamingmachine;

import casino.idfactory.GeneralID;

public class GamingMachineID  extends GeneralID implements Comparable<GeneralID> {
    /**
     *
     * @param o
     * @return 1 if both objects have the same id, 0 otherwise
     *
     * @should return true if ids are same
     * @should return false if ids are different
     */
    @Override
    public int compareTo(GeneralID o) {
        if (this.getID().equals(o.getID())) {
            return 1;
        }

        return 0;
    }
}
