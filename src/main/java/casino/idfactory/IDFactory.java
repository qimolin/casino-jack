package casino.idfactory;

import casino.bet.BetID;
import casino.cashier.CardID;
import casino.game.BettingRoundID;
import casino.gamingmachine.GamingMachineID;

/**
 * Factory for creation of GeneralID objects.
 * creation of the right object is done by specifying the type to create as a string
 * the specified type is case insensitive
 *
 * possible types:
 * betID
 * bettingroundID
 * cardID
 * gamingMachineID
 *
 * when the type is not present, null is returned.
 */
public class IDFactory {
  //

    /**
     * generate the right generalID instance by specifying the type as a string
     * @param idType is name of the type in capitals.
     * @return an instance of the correct GeneralID object type, or null otherwise.
     */
    public static GeneralID generateID(String idType){
        if(idType == null){
            return null;
        }
        if(idType.equalsIgnoreCase("CARDID")){
            return new CardID();
        } else if(idType.equalsIgnoreCase("BETID")){
            return new BetID();
        } else if(idType.equalsIgnoreCase("BETTINGROUNDID")){
            return new BettingRoundID();
        } else if(idType.equalsIgnoreCase("GAMINGMACHINEID")){
            return new GamingMachineID();
        }

        return null;
    };





}
