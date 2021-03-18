package casino.idfactory;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * CREATE TESTS FOR THIS CLASS, implement necessary code when needed (after creating tests first)
 *
 */
public abstract class GeneralID {
    private final UUID uniqueID;
    private final Instant timeStamp;

    public GeneralID() {
        this.uniqueID = UUID.randomUUID();
        this.timeStamp = Instant.now();
    }

    // TODO: implement necessary code. Add WHY you need it.

}
